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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.dining.model.menu.xml.Menu;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractXmlBasedDiningMenuDaoImpl implements IDiningMenuDao {

    protected final Log log = LogFactory.getLog(getClass());
    
    private Cache cache;

    @Required
    public void setCache(Cache cache) {
        this.cache = cache;
    }

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
    
    @Override
    public DiningHall getMenu(PortletRequest request, String diningHall) {

        String key = "diningHallMenu.".concat(diningHall);
        Element cachedElement = this.cache.get(key);
        if (cachedElement == null) {
            
            final String xml = getXmlMenu(request, diningHall);
            final DiningHall hall = getMenu(xml);
            
            cachedElement = new Element(key, hall);
            this.cache.put(cachedElement);
            
            return hall;
        } else {
            return (DiningHall) cachedElement.getValue();
        }

    }
    
    protected abstract String getXmlMenu(PortletRequest request, String diningHall);
    
    /**
     * Deserialize a menu from the provided XML.
     * 
     * @param xml
     * @return
     * @throws JAXBException
     */
    protected DiningHall getMenu(String xml) {
        try {
            
            final String packageName = Menu.class.getPackage().getName();
            final JAXBContext jc = JAXBContext.newInstance( packageName );
            final Unmarshaller u = jc.createUnmarshaller();
            final DiningHall hall = (DiningHall) u.unmarshal(IOUtils.toInputStream(xml));
            return (DiningHall) hall;
            
        } catch (JAXBException e) {
            log.error("Exception deserializing menu xml", e);
            return null;
        }
    }

}
