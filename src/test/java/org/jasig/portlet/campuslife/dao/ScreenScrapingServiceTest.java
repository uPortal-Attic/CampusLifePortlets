package org.jasig.portlet.campuslife.dao;

import static org.junit.Assert.assertEquals;

import org.jasig.portlet.campuslife.dining.dao.DiningScreenScrapingService;
import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;
import org.junit.Test;

public class ScreenScrapingServiceTest {
    
    @Test
    public void testResolvePackage() {
        ScreenScrapingService<DiningHall> service = new DiningScreenScrapingService<DiningHall>();
        assertEquals(DiningHall.class.getPackage().getName(), service.getPackageName());
    }

}
