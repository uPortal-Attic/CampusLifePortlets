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

import java.util.List;

import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.dining.model.menu.xml.Dish;
import org.jasig.portlet.dining.model.menu.xml.FoodCategory;
import org.jasig.portlet.dining.model.menu.xml.Meal;

/**
 * Dining menu DAO capable of collecting menu information vi screen scraping
 * HTML content. This implementation should only be used if no more structured
 * content can be made available.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @version $Revision$
 */
public class YaleDiningMenuDaoImpl implements IDiningMenuDao {

    private String diningUrl = "http://www.yaledining.org/menu.cfm?mDH=";

    protected final Log log = LogFactory.getLog(getClass());

    private List<String> diningHalls;

    /**
     * Set the list of dining hall names.
     * 
     * @param diningHalls
     */
    public void setDiningHalls(List<String> diningHalls) {
        this.diningHalls = diningHalls;
    }

    @Override
    public List<String> getDiningHalls(PortletRequest request) {
        return this.diningHalls;
    }

    private ScreenScrapingService<DiningHall> diningHallService;
    
    public void setDiningHallService(ScreenScrapingService<DiningHall> diningHallService) {
        this.diningHallService = diningHallService;
    }


    @Override
    public DiningHall getDiningHall(String diningHall) {
        return diningHallService.getDiningItem(diningUrl + diningHall);
    }

    
    @Override
    public Meal getMeal(String diningHall, String mealName) {
        
        DiningHall hall = getDiningHall(diningHall);
        for (Meal meal : hall.getMeal()) {
            if (meal.getName().equals(mealName)) {
                return meal;
            }
        }

        log.warn("Unable to find meal matching diningHall " + diningHall
                + " and meal name " + mealName);
        return null;
    }

    @Override
    public FoodCategory getFoodCategory(String diningHall, String mealName,
            String categoryName) {
        
        Meal meal = getMeal(diningHall, mealName);
        for (FoodCategory category : meal.getFoodCategory()) {
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }

        log.warn("Unable to find food category matching diningHall "
                + diningHall + " and meal name " + mealName
                + ", and food category " + categoryName);
        return null;
    }

    @Override
    public Dish getDish(String diningHall, String mealName, String dishName) {
        
        Meal meal = getMeal(diningHall, mealName);
        for (FoodCategory category : meal.getFoodCategory()) {
            for (Dish dish : category.getDish()) {
                if (dishName.equals(dish.getName()))
                    return dish;
            }
        }

        log.warn("Unable to find dish matching diningHall " + diningHall
                + ", meal name " + mealName + ", and dish name " + dishName);
        return null;
    }

}
