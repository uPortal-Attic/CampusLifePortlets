package org.jasig.portlet.campuslife.laundry.dao;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.laundry.model.laundry.xml.Laundromat;
import org.jasig.portlet.campuslife.laundry.model.laundry.xml.LaundromatList;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class MockLaundryDaoImpl implements ILaundryDao, InitializingBean {

    protected final Log log = LogFactory.getLog(getClass());
    
    private Resource mockData;
    
    @Autowired(required = true)
    public void setMockData(Resource mockData) {
        this.mockData = mockData;
    }
    
    private LaundromatList laundromats;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LaundromatList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            this.laundromats = (LaundromatList) unmarshaller.unmarshal(mockData.getInputStream());
        } catch (IOException e) {
            log.error("Failed to read mock data", e);
        } catch (JAXBException e) {
            log.error("Failed to unmarshall mock data", e);
        }
    }

    @Override
    public Laundromat getDefaultLaundromat(PortletRequest request) {
        return null;
    }

    @Override
    public List<Laundromat> getLaundromats(PortletRequest request) {
        return laundromats.getLaundromats();
    }
    
}
