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

package org.jasig.portlet.campuslife.dining.mvc.portlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.dining.dao.IDiningMenuDao;
import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Dish;
import org.jasig.portlet.campuslife.dining.model.menu.xml.FoodCategory;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Meal;
import org.jasig.portlet.campuslife.mvc.IViewSelector;
import org.jasig.portlet.campuslife.service.IURLService;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    private DateTimeFormatter displayFormat = new DateTimeFormatterBuilder().appendPattern("EEE MMM d").toFormatter();
    private DateTimeFormatter paramFormat = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy").toFormatter();

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
    
    private IURLService urlService;
    
    @Autowired(required = true)
    public void setUrlService(IURLService urlService) {
        this.urlService = urlService;
    }
    
    private boolean showDishDetails = true;
    
    @Value("${dining.showDishDetails:true}")
    public void showDishDetails(boolean showDishDetails) {
        this.showDishDetails = showDishDetails;
    }
    
    @RenderMapping
    public ModelAndView showMainView(final RenderRequest request, String date) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "main-jQM" : "main";        
        final ModelAndView mav = new ModelAndView("dining/".concat(viewName));
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }

        DateMidnight d;
        if (date == null) {
            d = new DateMidnight();
        } else {
            d = paramFormat.parseLocalDate(date).toDateMidnight();
        }
        final List<DiningHall> halls = menuDao.getDiningHalls(d, request);
        
        if (halls != null && halls.size() == 1) {
            return showDiningHallView(request, halls.get(0).getKey(), paramFormat.print(d));
        }
        
        else {
            mav.addObject("diningHalls", menuDao.getDiningHalls(d, request));
            mav.addObject("displayDate", displayFormat.print(d));
            mav.addObject("date", paramFormat.print(d));
            mav.addObject("prev", paramFormat.print(d.minusDays(1)));
            mav.addObject("next", paramFormat.print(d.plusDays(1)));
            return mav;
        }

    }

    @RenderMapping(params="action=diningHall")
    public ModelAndView showDiningHallView(final RenderRequest request, 
            final String diningHall, final String date) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "diningHall-jQM" : "diningHall";        
        final ModelAndView mav = new ModelAndView("dining/".concat(viewName));
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for dining hall view");
        }
        
        final DateMidnight d = paramFormat.parseLocalDate(date).toDateMidnight();
        final DiningHall dh = menuDao.getDiningHall(d, diningHall);
        mav.addObject("diningHall", dh);
        
        if (dh.getLocationCode() != null) {
            final String url = urlService.getLocationUrl(dh.getLocationCode(), request);
            mav.addObject("locationUrl", url);
        }

        final List<DiningHall> halls = menuDao.getDiningHalls(d, request);
        mav.addObject("hasMultipleLocations", halls.size() > 1);
        mav.addObject("date", paramFormat.print(d));
        mav.addObject("prev", paramFormat.print(d.minusDays(1)));
        mav.addObject("next", paramFormat.print(d.plusDays(1)));
        mav.addObject("displayDate", displayFormat.print(d));

        return mav;

    }
    
    @RenderMapping(params="action=meal")
    public ModelAndView showMealView(final RenderRequest request,
            final String diningHall, final String mealName, final String date) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "meal-jQM" : "meal";
        final ModelAndView mav = new ModelAndView("dining/".concat(viewName));
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for meal view");
        }
        
        mav.addObject("dishCodeImages", dishCodeImages);
        mav.addObject("diningHallKey", diningHall);
        
        final DateMidnight d = paramFormat.parseLocalDate(date).toDateMidnight();
        final Meal meal = menuDao.getMeal(d, diningHall, mealName);
        
        final List<FoodCategory> categories = new ArrayList<FoodCategory>();
        for (FoodCategory category : meal.getFoodCategories()) {
            categories.add(menuDao.getFoodCategory(d, diningHall, mealName, category.getName()));
        }

        final DiningHall hall = menuDao.getDiningHall(d, diningHall);
        mav.addObject("diningHall", hall);

        final List<DiningHall> halls = menuDao.getDiningHalls(d, request);
        mav.addObject("hasMultipleLocations", halls.size() > 1);

        mav.addObject("date", paramFormat.print(d));
        mav.addObject("meal", meal);
        mav.addObject("categories", categories);
        mav.addObject("showDishDetails", this.showDishDetails);
        return mav;

    }

    @RenderMapping(params="action=dish")
    public ModelAndView showDishView(final RenderRequest request,
            final String diningHall, final String mealName, final String dishName, final String date) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "dish-jQM" : "dish";
        final ModelAndView mav = new ModelAndView("dining/".concat(viewName));
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for dish view");
        }
        
        mav.addObject("dishCodeImages", dishCodeImages);
        mav.addObject("diningHallKey", diningHall);
        mav.addObject("mealName", mealName);

        final DateMidnight d = paramFormat.parseLocalDate(date).toDateMidnight();
        mav.addObject("date", paramFormat.print(d));
        
        final Dish dish = menuDao.getDish(d, diningHall, mealName, dishName);
        mav.addObject("dish", dish);
        
        final DiningHall hall = menuDao.getDiningHall(d, diningHall);
        mav.addObject("diningHall", hall);
        
        final List<DiningHall> halls = menuDao.getDiningHalls(d, request);
        mav.addObject("hasMultipleLocations", halls.size() > 1);

        return mav;

    }

    @ActionMapping
    public void doAction() {
        // no-op action mapping to prevent accidental calls to this URL from
        // crashing the portlet
    }

}
