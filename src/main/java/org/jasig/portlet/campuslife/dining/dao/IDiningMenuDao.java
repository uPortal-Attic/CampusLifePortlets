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

import java.util.List;

import javax.portlet.PortletRequest;

import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Dish;
import org.jasig.portlet.campuslife.dining.model.menu.xml.FoodCategory;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Meal;

/**
 * IDiningMenuDao presents a generic interface for collecting dining hall
 * menu information.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @version $Revision$
 */
public interface IDiningMenuDao {
    
    /**
     * Get the list of dining halls for a given portlet request.
     * 
     * @param request
     * @return
     */
    public List<String> getDiningHalls(PortletRequest request);
    
    public DiningHall getDiningHall(String diningHall);
    
    public Meal getMeal(String diningHall, String mealName);

    public FoodCategory getFoodCategory(String diningHall, String mealName, String categoryName);
    
    public Dish getDish(String dininghall, String meal, String dishName);
    
}
