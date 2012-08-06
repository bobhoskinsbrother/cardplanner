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
package org.apache.activemq.bugs.amq1974;

import java.io.File;
import java.net.URISyntaxException;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.network.DiscoveryNetworkConnector;
import org.apache.activemq.store.kahadaptor.KahaPersistenceAdapter;
import org.apache.activemq.transport.discovery.simple.SimpleDiscoveryAgent;

public class TryJmsClient
{
    private final BrokerService broker = new BrokerService();

    public static void main(String[] args) throws Exception {
        new TryJmsClient().start();
    }

    private void start() throws Exception {

        broker.setUseJmx(false);
        broker.setPersistent(true);
        broker.setBrokerName("TestBroker");
        broker.getSystemUsage().setSendFailIfNoSpace(true);

        broker.getSystemUsage().getMemoryUsage().setLimit(10 * 1024 * 1024);

        KahaPersistenceAdapter persist = new KahaPersistenceAdapter();
        persist.setDirectory(new File("/tmp/broker2"));
        persist.setMaxDataFileLength(20 * 1024 * 1024);
        broker.setPersistenceAdapter(persist);

        String brokerUrl = "tcp://localhost:4501";
        broker.addConnector(brokerUrl);

        broker.start();

        addNetworkBroker();

        startUsageMonitor(broker);

        startMessageSend();

        synchronized(this) {
            this.wait();
        }
    }

    private void startUsageMonitor(final BrokerService brokerService) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("ActiveMQ memeory " + brokerService.getSystemUsage().getMemoryUsage().getPercentUsage()
                            + " " + brokerService.getSystemUsage().getMemoryUsage().getUsage());
                    System.out.println("ActiveMQ message store " + brokerService.getSystemUsage().getStoreUsage().getPercentUsage());
                    System.out.println("ActiveMQ temp space " + brokerService.getSystemUsage().getTempUsage().getPercentUsage());
                }
            }
        }).start();
    }

    private void addNetworkBroker() throws Exception {

        DiscoveryNetworkConnector dnc = new DiscoveryNetworkConnector();
        dnc.setNetworkTTL(1);
        dnc.setBrokerName("TestBroker");
        dnc.setName("Broker1Connector");
        dnc.setDynamicOnly(true);

        SimpleDiscoveryAgent discoveryAgent = new SimpleDiscoveryAgent();
        String remoteUrl = "tcp://localhost:4500";
        discoveryAgent.setServices(remoteUrl);

        dnc.setDiscoveryAgent(discoveryAgent);

        broker.addNetworkConnector(dnc);
        dnc.start();
    }

    private void startMessageSend() {
        new Thread(new MessageSend()).start();
    }

    private class MessageSend implements Runnable {
        public void run() {
            try {
                String url = "vm://TestBroker";
                ActiveMQConnection connection = ActiveMQConnection.makeConnection(url);
                connection.setDispatchAsync(true);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination dest = session.createTopic("TestDestination");

                MessageProducer producer = session.createProducer(dest);
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);

                for(int i = 0; i < 99999999; i++)  {
                    TextMessage message = session.createTextMessage("test" + i);

                    /*
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    */

                    try {
                        producer.send(message);
                    } catch (Exception e ) {
                        e.printStackTrace();
                        System.out.println("TOTAL number of messages sent " + i);
                        break;
                    }

                    if (i % 1000 == 0) {
                        System.out.println("sent message " + message.getJMSMessageID());
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
