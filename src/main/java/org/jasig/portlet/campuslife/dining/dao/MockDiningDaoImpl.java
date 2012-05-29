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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.dao.MockDataService;
import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Dish;
import org.jasig.portlet.campuslife.dining.model.menu.xml.FoodCategory;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Meal;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Menu;
import org.jasig.portlet.campuslife.dining.model.menu.xml.MenuDay;
import org.joda.time.DateMidnight;

public class MockDiningDaoImpl extends MockDataService<Menu> implements IDiningMenuDao {

    protected final static String PACKAGE_NAME = Menu.class.getPackage().getName();

    protected final Log log = LogFactory.getLog(getClass());
    
    @Override
    public List<DiningHall> getDiningHalls(DateMidnight date, PortletRequest request) {
        final List<MenuDay> days = this.getData().getDays();
        if (days.size() > 0) {
            return days.get(0).getDiningHalls();
        }
        
        log.info("Unable to locate day " + date.toString());
        return null;
    }

    @Override
    public DiningHall getDiningHall(DateMidnight date, String diningHall) {
        final List<MenuDay> days = this.getData().getDays();
        if (days.size() > 0) {
            final List<DiningHall> halls = days.get(0).getDiningHalls();
            for (DiningHall hall : halls) {
                if (hall.getKey().equals(diningHall)) {
                    return hall;
                }
            }
        }

        log.info("Unable to locate dining hall " + diningHall);
        return null;
    }

    @Override
    public Meal getMeal(DateMidnight date, String diningHall, String mealName) {
        final DiningHall hall = getDiningHall(date, diningHall);
        if (hall != null) {
            for (Meal meal : hall.getMeals()) {
                if (meal.getName().equals(mealName)) {
                    return meal;
                }
            }
            
            log.info("Unable to locate meal " + mealName + " at dining hall " + diningHall);
            return null;

        }
        
        return null;
    }

    @Override
    public FoodCategory getFoodCategory(DateMidnight date, String diningHall, String mealName,
            String categoryName) {
        final Meal meal = getMeal(date, diningHall, mealName);
        if (meal != null) {
            for (FoodCategory category : meal.getFoodCategories()) {
                if (category.getName().equals(categoryName)) {
                    return category;
                }
            }
            
            log.info("Unable to locate food category " + categoryName + " for dining hall " + diningHall + " at meal " + mealName);
            return null;
        }
        
        else {
            return null;
        }
    }

    @Override
    public Dish getDish(DateMidnight date, String diningHall, String mealName, String dishName) {
        final Meal meal = getMeal(date, diningHall, mealName);
        if (meal != null) {
            for (FoodCategory category : meal.getFoodCategories()) {
                for (Dish dish : category.getDishes()) {
                    if (dish.getName().equals(dishName)) {
                        return dish;
                    }
                }
            }
            
            log.info("Unable to locate dish " + dishName + " for dining hall " + diningHall + " at meal " + mealName);
            return null;
        }
        
        else {
            return null;
        }
    }

    @Override
    public String getPackageName() {
        return PACKAGE_NAME;
    }

}
