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
package org.jasig.portlet.athletics.dao;

import org.jasig.portlet.athletics.model.feed.xml.AthleticsFeed;
import org.jasig.portlet.athletics.model.feed.xml.Sport;

/**
 * IAthleticsDao provides an interface for retrieving information about
 * athletic results at a university.
 * 
 * @author Jen Bourey
 * @revision $Revision$
 */
public interface IAthleticsDao {

    /**
     * Return an athletics feed representing all current news stories and
     * competitions for all known sports.
     * 
     * @return
     */
    public AthleticsFeed getFeed();

    /**
     * Return details, news stories, and competitions for an individual sport.
     * 
     * @param sport
     * @return
     */
    public Sport getSport(String sport);

}