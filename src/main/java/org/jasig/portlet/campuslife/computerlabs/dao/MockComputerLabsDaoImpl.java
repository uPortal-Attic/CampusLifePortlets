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
package org.jasig.portlet.campuslife.computerlabs.dao;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.computerlabs.model.labs.xml.ComputerLab;
import org.jasig.portlet.campuslife.computerlabs.model.labs.xml.ComputerLabList;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class MockComputerLabsDaoImpl implements InitializingBean, IComputerLabsDao {
    
    protected final Log log = LogFactory.getLog(getClass());
    
    private Resource mockData;
    
    @Autowired(required = true)
    public void setMockData(Resource mockData) {
        this.mockData = mockData;
    }
    
    private ComputerLabList labs;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ComputerLabList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            this.labs = (ComputerLabList) unmarshaller.unmarshal(mockData.getInputStream());
        } catch (IOException e) {
            log.error("Failed to read mock data", e);
        } catch (JAXBException e) {
            log.error("Failed to unmarshall mock data", e);
        }
    }

    @Override
    public List<ComputerLab> getComputerLabs(PortletRequest request) {
        return labs.getComputerLabs();
    }
    
    @Override
    public ComputerLab getDefaultComputerLab(PortletRequest request) {
        return null;
    }

}
