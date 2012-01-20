/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.campuslife.dining.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.portlet.campuslife.dao.IScreenScrapingPostProcessor;
import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;

public class DefaultDiningHallInfoPostProcessor implements
        IScreenScrapingPostProcessor<DiningHall> {

    private Map<String,DiningHall> defaultDiningHalls = new HashMap<String,DiningHall>();
    
    public void setDiningHallInfo(List<DiningHall> diningHalls) {
        for (DiningHall hall : diningHalls) {
            defaultDiningHalls.put(hall.getName(), hall);
        }
    }
    
    @Override
    public void postProcess(String key, DiningHall diningHall) {
        
        final DiningHall hall = defaultDiningHalls.get(diningHall.getName());
        
        if (diningHall.getKey() == null && hall.getKey() != null) {
            diningHall.setKey(hall.getKey());
        }
        
        if (diningHall.getLocationCode() == null && hall.getLocationCode() != null) {
            diningHall.setLocationCode(hall.getLocationCode());
        }
        
    }

}
