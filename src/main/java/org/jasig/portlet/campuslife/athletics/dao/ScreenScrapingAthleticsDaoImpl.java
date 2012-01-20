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
package org.jasig.portlet.campuslife.athletics.dao;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.AthleticsFeed;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.Sport;

/**
 * ScreenScrapingAthleticsDaoImpl provides a reusable athletics DAO 
 * implementation targeted for collecting information from HTML content.  
 * Whenever possible, results should instead be retrieved from some more 
 * well-formatted web service or other high quality data source. 
 * 
 * This implementation uses OWASP AntiSamy to clean and validate external
 * HTML pages, then uses an XSLT to transform the HTML page into the 
 * portlet's default athletics feed XML structure, at which point the data
 * can be deseriablized.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @Revision $Revision$
 */
public class ScreenScrapingAthleticsDaoImpl implements IAthleticsDao {

    protected Log log = LogFactory.getLog(getClass());
    
    private AthleticsScreenScrapingService<Sport> screenScrapingService;
    
    public void setScreenScrapingService(AthleticsScreenScrapingService<Sport> screenScrapingService) {
        this.screenScrapingService = screenScrapingService;
    }

    private Map<String,String> sportUrls;

    /**
     * Set the mapping of URLs for each sport.  This implementation assumes that
     * each sport is represented by its own HTML page.
     * 
     * @param urlMap
     */
    public void setSportUrls(Map<String,String> urlMap) {
        this.sportUrls = urlMap;
    }

    /**
     * Get the mapping of URLs by sport.
     * 
     * @return
     */
    public Map<String,String> getSportUrls() {
        return this.sportUrls;
    }

    @Override
    public AthleticsFeed getFeed() {
        
        final AthleticsFeed feed = new AthleticsFeed();

        // Retrieve and parse each URL in the sport URL mapping and assemble
        // the results into a single athletics feed.
        for (String sportKey : sportUrls.keySet()) {
            final Sport sport = getSport(sportKey);
            if (sport != null) {
                feed.getSport().add(sport);
            }
        }
        
        return feed;
    }

    @Override
    public Sport getSport(String sportKey) {
        
        // get the URL associated with this sport key
        final String url = sportUrls.get(sportKey);        
        if (url != null) {
            final Sport sport = screenScrapingService.getItem(sportKey, url);
            sport.setName(sportKey);            
            return sport;
        }
        
        return null;
    }
        
}
