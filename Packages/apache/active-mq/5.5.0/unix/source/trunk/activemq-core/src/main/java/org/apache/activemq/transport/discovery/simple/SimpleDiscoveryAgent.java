/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.transport.discovery.simple;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.thread.DefaultThreadPools;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryListener;
import org.apache.activemq.util.MDCHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple DiscoveryAgent that allows static configuration of the discovered
 * services.
 * 
 * 
 */
public class SimpleDiscoveryAgent implements DiscoveryAgent {

    private final static Logger LOG = LoggerFactory.getLogger(SimpleDiscoveryAgent.class);
    private long initialReconnectDelay = 1000;
    private long maxReconnectDelay = 1000 * 30;
    private long backOffMultiplier = 2;
    private boolean useExponentialBackOff=true;
    private int maxReconnectAttempts;
    private final Object sleepMutex = new Object();
    private long minConnectTime = 5000;
    private DiscoveryListener listener;
    private String services[] = new String[] {};
    private final AtomicBoolean running = new AtomicBoolean(false);

    class SimpleDiscoveryEvent extends DiscoveryEvent {

        private int connectFailures;
        private long reconnectDelay = initialReconnectDelay;
        private long connectTime = System.currentTimeMillis();
        private AtomicBoolean failed = new AtomicBoolean(false);

        public SimpleDiscoveryEvent(String service) {
            super(service);
        }

    }

    public void setDiscoveryListener(DiscoveryListener listener) {
        this.listener = listener;
    }

    public void registerService(String name) throws IOException {
    }

    public void start() throws Exception {
        running.set(true);
        for (int i = 0; i < services.length; i++) {
            listener.onServiceAdd(new SimpleDiscoveryEvent(services[i]));
        }
    }

    public void stop() throws Exception {
        running.set(false);
        synchronized (sleepMutex) {
            sleepMutex.notifyAll();
        }
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services.split(",");
    }

    public void setServices(String services[]) {
        this.services = services;
    }

    public void setServices(URI services[]) {
        this.services = new String[services.length];
        for (int i = 0; i < services.length; i++) {
            this.services[i] = services[i].toString();
        }
    }

    public void serviceFailed(DiscoveryEvent devent) throws IOException {

        final SimpleDiscoveryEvent event = (SimpleDiscoveryEvent)devent;
        if (event.failed.compareAndSet(false, true)) {

            listener.onServiceRemove(event);
            final Map context = MDCHelper.getCopyOfContextMap();
            DefaultThreadPools.getDefaultTaskRunnerFactory().execute(new Runnable() {
                public void run() {

                    MDCHelper.setContextMap(context);

                    // We detect a failed connection attempt because the service
                    // fails right
                    // away.
                    if (event.connectTime + minConnectTime > System.currentTimeMillis()) {
                        LOG.debug("Failure occurred soon after the discovery event was generated.  It will be classified as a connection failure: "+event);

                        event.connectFailures++;

                        if (maxReconnectAttempts > 0 && event.connectFailures >= maxReconnectAttempts) {
                            LOG.debug("Reconnect attempts exceeded "+maxReconnectAttempts+" tries.  Reconnecting has been disabled.");
                            return;
                        }

                        synchronized (sleepMutex) {
                            try {
                                if (!running.get()) {
                                    return;
                                }

                                LOG.debug("Waiting "+event.reconnectDelay+" ms before attempting to reconnect.");
                                sleepMutex.wait(event.reconnectDelay);
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }

                        if (!useExponentialBackOff) {
                            event.reconnectDelay = initialReconnectDelay;
                        } else {
                            // Exponential increment of reconnect delay.
                            event.reconnectDelay *= backOffMultiplier;
                            if (event.reconnectDelay > maxReconnectDelay) {
                                event.reconnectDelay = maxReconnectDelay;
                            }
                        }

                    } else {
                        event.connectFailures = 0;
                        event.reconnectDelay = initialReconnectDelay;
                    }

                    if (!running.get()) {
                        return;
                    }

                    event.connectTime = System.currentTimeMillis();
                    event.failed.set(false);
                    listener.onServiceAdd(event);
                }
            }, "Simple Discovery Agent");
        }
    }

    public long getBackOffMultiplier() {
        return backOffMultiplier;
    }

    public void setBackOffMultiplier(long backOffMultiplier) {
        this.backOffMultiplier = backOffMultiplier;
    }

    public long getInitialReconnectDelay() {
        return initialReconnectDelay;
    }

    public void setInitialReconnectDelay(long initialReconnectDelay) {
        this.initialReconnectDelay = initialReconnectDelay;
    }

    public int getMaxReconnectAttempts() {
        return maxReconnectAttempts;
    }

    public void setMaxReconnectAttempts(int maxReconnectAttempts) {
        this.maxReconnectAttempts = maxReconnectAttempts;
    }

    public long getMaxReconnectDelay() {
        return maxReconnectDelay;
    }

    public void setMaxReconnectDelay(long maxReconnectDelay) {
        this.maxReconnectDelay = maxReconnectDelay;
    }

    public long getMinConnectTime() {
        return minConnectTime;
    }

    public void setMinConnectTime(long minConnectTime) {
        this.minConnectTime = minConnectTime;
    }

    public boolean isUseExponentialBackOff() {
        return useExponentialBackOff;
    }

    public void setUseExponentialBackOff(boolean useExponentialBackOff) {
        this.useExponentialBackOff = useExponentialBackOff;
    }
}
