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

package org.jasig.portlet.dining.mvc.portlet;

import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.dining.dao.IDiningMenuDao;
import org.jasig.portlet.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.dining.model.menu.xml.Dish;
import org.jasig.portlet.dining.model.menu.xml.FoodCategory;
import org.jasig.portlet.dining.model.menu.xml.Meal;
import org.jasig.portlet.dining.mvc.IViewSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * Main portlet view.
 */
@Controller
@RequestMapping("VIEW")
public class MainController {

    protected final Log logger = LogFactory.getLog(getClass());

    private IDiningMenuDao menuDao;
    
    @Autowired(required = true)
    public void setDiningMenuDao(IDiningMenuDao diningMenuDao) {
        this.menuDao = diningMenuDao;
    }
    
    private IViewSelector viewSelector;
    
    @Autowired(required = true)
    public void setViewSelector(IViewSelector viewSelector) {
        this.viewSelector = viewSelector;
    }
    
    private Map<String,String> dishCodeImages;
    
    @Resource(name="dishCodeImages")
    public void setDishCodeImages(Map<String,String> images){
        this.dishCodeImages = images;
    }
    
    @RenderMapping
    public ModelAndView showMainView(
            final RenderRequest request, final RenderResponse response) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "main-jQM" : "main";        
        final ModelAndView mav = new ModelAndView(viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }
        
        mav.addObject("diningHalls", menuDao.getDiningHalls(request));

        return mav;

    }

    @RenderMapping(params="action=diningHall")
    public ModelAndView showDiningHallView(
            final RenderRequest request, final RenderResponse response,
            final String diningHall) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "diningHall-jQM" : "diningHall";        
        final ModelAndView mav = new ModelAndView(viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }
        
        DiningHall menu = menuDao.getMenu(request, diningHall);
        mav.addObject("menu", menu);
        mav.addObject("diningHallKey", diningHall);

        mav.addObject("dishCodeImages", dishCodeImages);
        
        return mav;

    }

    @RenderMapping(params="action=dish")
    public ModelAndView showDishView(
            final RenderRequest request, final RenderResponse response,
            final String dishName, final String diningHall) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "dish-jQM" : "dish";        
        final ModelAndView mav = new ModelAndView(viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }
        
        mav.addObject("dishCodeImages", dishCodeImages);
        mav.addObject("diningHallKey", diningHall);
        
        DiningHall menu = menuDao.getMenu(request, diningHall);
        for (Meal meal : menu.getMeal()) {
            for (FoodCategory cat : meal.getFoodCategory()) {
                for (Dish dish : cat.getDish()) {
                    if (dishName.equals(dish.getName())) {
                        mav.addObject("dish", dish);
                        return mav;
                    }
                    
                }
            }
        }

        return mav;

    }

    @ActionMapping
    public void doAction() {
        // no-op action mapping to prevent accidental calls to this URL from
        // crashing the portlet
    }

}
