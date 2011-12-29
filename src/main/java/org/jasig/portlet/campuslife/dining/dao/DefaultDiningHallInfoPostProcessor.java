package org.jasig.portlet.campuslife.dining.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;

public class DefaultDiningHallInfoPostProcessor implements
        IScreenScrapingPostProcessor<DiningHall> {

    private Map<String,DiningHall> defaultDiningHalls = new HashMap<String,DiningHall>();
    
    public void setDiningHallInfo(List<DiningHall> diningHalls) {
        for (DiningHall hall : diningHalls) {
            defaultDiningHalls.put(hall.getName(), hall);
        }
    }
    
    @Override
    public void postProcess(DiningHall diningHall) {
        
        final DiningHall hall = defaultDiningHalls.get(diningHall.getName());
        
        if (diningHall.getKey() == null && hall.getKey() != null) {
            diningHall.setKey(hall.getKey());
        }
        
        if (diningHall.getLocationCode() == null && hall.getLocationCode() != null) {
            diningHall.setLocationCode(hall.getLocationCode());
        }
        
    }

}
