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
package org.jasig.portlet.campuslife.laundry.mvc.portlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.laundry.dao.ILaundryDao;
import org.jasig.portlet.campuslife.laundry.model.laundry.xml.Laundromat;
import org.jasig.portlet.campuslife.mvc.IViewSelector;
import org.jasig.portlet.campuslife.service.IURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class LaundryController {

    protected final Log logger = LogFactory.getLog(getClass());
    
    private IViewSelector viewSelector;
    
    @Autowired(required = true)
    public void setViewSelector(IViewSelector viewSelector) {
        this.viewSelector = viewSelector;
    }
    
    private ILaundryDao laundryDao;
    
    @Autowired(required = true)
    public void setLaundryDao(ILaundryDao laundryDao) {
        this.laundryDao = laundryDao;
    }
    
    private IURLService urlService;
    
    @Autowired(required = true)
    public void setUrlService(IURLService urlService) {
        this.urlService = urlService;
    }
    
    @RenderMapping
    public ModelAndView showMainView(
            final RenderRequest request, final RenderResponse response) {

        // determine if the request represents a mobile browser and set the
        // view name accordingly
        final boolean isMobile = viewSelector.isMobile(request);
        final String viewName = isMobile ? "main-jQM" : "main";        
        final ModelAndView mav = new ModelAndView("laundry/" + viewName);
        
        if(logger.isDebugEnabled()) {
            logger.debug("Using view name " + viewName + " for main view");
        }

        // retrieve the laundromats and their statuses
        final List<Laundromat> laundromats = laundryDao.getLaundromats(request);
        mav.addObject("laundromats", laundromats);

        // use the URL service to get a map URL for each laundromat location
        final Map<String,String> locationUrls = new HashMap<String,String>();
        for (Laundromat laundromat : laundromats) {
            final String url = urlService.getLocationUrl(laundromat.getLocationCode(), request);
            locationUrls.put(laundromat.getLocationCode(), url);
        }
        mav.addObject("locationUrls", locationUrls);

        if(logger.isDebugEnabled()) {
            logger.debug("Rendering main view");
        }

        return mav;

    }

    @ActionMapping
    public void doAction() {
        // no-op action mapping to prevent accidental calls to this URL from
        // crashing the portlet
    }


}
