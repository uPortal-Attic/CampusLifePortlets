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
package org.jasig.portlet.dining.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.portlet.PortletRequest;

import org.jasig.portlet.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.dining.model.menu.xml.Meal;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

public class MockDiningMenuDaoImplTest {
    
    @Autowired
    MockDiningMenuDaoImpl dao;
    
    @Mock PortletRequest request;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetDiningHalls() {
        List<String> diningHalls = dao.getDiningHalls(request);
        assertEquals(1, diningHalls.size());
    }
    
    @Test
    public void setGetMenu() {
        DiningHall hall = dao.getMenu(request, "Berkeley");
        assertEquals("Berkeley", hall.getName());
        assertEquals(2, hall.getMeal().size());
        
        Meal meal = hall.getMeal().get(0);
        assertEquals("Brunch", meal.getName());
        assertEquals(8, meal.getFoodCategory().size());
    }
}
