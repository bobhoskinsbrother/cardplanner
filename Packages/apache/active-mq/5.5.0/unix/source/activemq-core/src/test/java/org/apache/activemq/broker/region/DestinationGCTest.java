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
package org.apache.activemq.broker.region;

import org.apache.activemq.EmbeddedBrokerTestSupport;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;

public class DestinationGCTest extends EmbeddedBrokerTestSupport {

    ActiveMQQueue queue = new ActiveMQQueue("TEST");

    @Override
    protected BrokerService createBroker() throws Exception {
        BrokerService broker = super.createBroker();
        broker.setDestinations(new ActiveMQDestination[] {queue});
        broker.setSchedulePeriodForDestinationPurge(1000);
        PolicyEntry entry = new PolicyEntry();
        entry.setGcInactiveDestinations(true);
        entry.setInactiveTimoutBeforeGC(3000);
        PolicyMap map = new PolicyMap();
        map.setDefaultEntry(entry);
        broker.setDestinationPolicy(map);
        return broker;
    }

    public void testDestinationGc() throws Exception {
        assertEquals(1, broker.getAdminView().getQueues().length);
        Thread.sleep(7000);
        assertEquals(0, broker.getAdminView().getQueues().length);
    }
}
