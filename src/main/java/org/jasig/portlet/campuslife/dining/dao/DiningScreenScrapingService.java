package org.jasig.portlet.campuslife.dining.dao;

import org.jasig.portlet.campuslife.dao.ScreenScrapingService;
import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;

public class DiningScreenScrapingService<T> extends ScreenScrapingService<T> {

    private final String packageName = DiningHall.class.getPackage().getName();
    
    @Override
    public String getPackageName() {
        return packageName;
    }

}
