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
package org.jasig.portlet.campuslife.laundry.dao;

import java.util.List;

import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.dao.MockDataService;
import org.jasig.portlet.campuslife.laundry.model.laundry.xml.Laundromat;
import org.jasig.portlet.campuslife.laundry.model.laundry.xml.LaundromatList;

public class MockLaundryDaoImpl extends MockDataService<LaundromatList> implements ILaundryDao {

    protected final static String PACKAGE_NAME = LaundromatList.class.getPackage().getName();
    
    protected final Log log = LogFactory.getLog(getClass());
    
    @Override
    public Laundromat getDefaultLaundromat(PortletRequest request) {
        return null;
    }

    @Override
    public List<Laundromat> getLaundromats(PortletRequest request) {
        return this.getData().getLaundromats();
    }

    @Override
    public String getPackageName() {
        return PACKAGE_NAME;
    }
    
}
