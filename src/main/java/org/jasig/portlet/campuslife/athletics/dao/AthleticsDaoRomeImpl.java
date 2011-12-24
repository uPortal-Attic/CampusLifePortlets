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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.AthleticsFeed;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.NewsItem;
import org.jasig.portlet.campuslife.athletics.model.feed.xml.Sport;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class AthleticsDaoRomeImpl implements IAthleticsDao {
    
    protected Log log = LogFactory.getLog(getClass());

    private Map<String,String> newsUrls;
    
    @Override
    public AthleticsFeed getFeed() {
        
        AthleticsFeed feed = new AthleticsFeed();
        
        for (String sportKey : newsUrls.keySet()) {
            Sport sport = getSport(sportKey);
            if (sport != null) {
                feed.getSport().add(sport);
            }
        }
        
        return feed;
    }

    @Override
    public Sport getSport(String sportKey) {
        
        Sport sport = new Sport();
        sport.setName(sportKey);
        
        String url = newsUrls.get(sportKey);
        if (url != null) {
            final DefaultHttpClient httpclient = new DefaultHttpClient();
            final HttpGet httpget = new HttpGet(url);
            final HttpResponse response;
            try {
                response = httpclient.execute(httpget);
                final InputStream in = response.getEntity().getContent();
                final XmlReader reader = new XmlReader(in);
                final SyndFeedInput input = new SyndFeedInput();
                final SyndFeed feed = input.build(reader);
                @SuppressWarnings("unchecked")
                List<SyndEntry> entries =  (List<SyndEntry>) feed.getEntries();
                for (SyndEntry entry : entries) {
                    NewsItem item = new NewsItem();
                    item.setTitle(entry.getTitle());
                    item.setSummary(entry.getDescription().getValue());
                    sport.getNewsItem().add(item);
                }
            } catch (ClientProtocolException e) {
                log.error(e);
            } catch (IOException e) {
                log.error(e);
            } catch (IllegalArgumentException e) {
                log.error(e);
            } catch (FeedException e) {
                log.error(e);
            }
            
        }
        
        return sport;
    }

}
