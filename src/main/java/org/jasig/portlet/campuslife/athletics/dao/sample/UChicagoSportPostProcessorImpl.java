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
package org.jasig.portlet.campuslife.athletics.dao.sample;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jasig.portlet.campuslife.athletics.model.feed.xml.Competition;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.Sport;
import org.jasig.portlet.campuslife.dao.IScreenScrapingPostProcessor;

public class UChicagoSportPostProcessorImpl implements IScreenScrapingPostProcessor<Sport> {

    private static Pattern whitespace = Pattern.compile("\\s+");
    private static Pattern invalidNameChars = Pattern.compile("[^a-zA-Z\\(\\)\\- ]");

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

    @Override
    public void postProcess(String key, Sport sport) {
        sport.setName(key);
        for (Competition competition : sport.getCompetition()) {
            
            // remove all invalid characters from the competition name
            String name = competition.getName().trim();
            final Matcher invalidCharsMatcher = invalidNameChars.matcher(name);
            name = invalidCharsMatcher.replaceAll("");
            competition.setName(name);
            
            // normalize the whitespace in the competition name
            final Matcher whitespaceMatcher = whitespace.matcher(name);
            name = whitespaceMatcher.replaceAll(" ");
            
            // add the full path to the url
            final String url = competition.getUrl();
            if (url != null) {
                final String sportUrl = this.sportUrls.get(sport.getName());
                final String fullUrl = sportUrl.substring(0, sportUrl.lastIndexOf("/")).concat("/").concat(url);
                competition.setUrl(fullUrl);
            }
        }
    }

}
