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
