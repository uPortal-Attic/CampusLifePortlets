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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.portlet.campuslife.athletics.model.feed.xml.AthleticsFeed;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.Sport;

/**
 * AthleticsDaoMergingImpl merges together the results of multiple athletics
 * feed DAOs.  The news items and competition results for each sport will be
 * combined.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @revision $Revision$
 */
public class AthleticsDaoMergingImpl implements IAthleticsDao {

    private List<IAthleticsDao> daos;
    
    public void setDaos(List<IAthleticsDao> daos) {
        this.daos = daos;
    }
    
    @Override
    public AthleticsFeed getFeed() {
        
        AthleticsFeed feed = new AthleticsFeed();
        
        Map<String,Sport> sports = new HashMap<String,Sport>();
        
        for (IAthleticsDao dao : daos) {
            AthleticsFeed daoFeed = dao.getFeed();
            for (Sport daoSport : daoFeed.getSport()) {
                Sport sport = sports.get(daoSport.getName());
                if (sport == null) {
                    sport = new Sport();
                    sport.setName(daoSport.getName());
                    sports.put(daoSport.getName(), sport);
                }
                sport.getNewsItem().addAll(daoSport.getNewsItem());
                sport.getCompetition().addAll(daoSport.getCompetition());
            }
        }
        
        feed.getSport().addAll(sports.values());
        return feed;
    }

    @Override
    public Sport getSport(String sportKey) {
        
        Sport sport = new Sport();
        
        for (IAthleticsDao dao : daos) {
            Sport daoSport = dao.getSport(sportKey);
            if (daoSport != null) {
                sport.setName(sportKey);
                sport.getNewsItem().addAll(daoSport.getNewsItem());
                sport.getCompetition().addAll(daoSport.getCompetition());
            }
        }
        
        return sport;
    }

}
