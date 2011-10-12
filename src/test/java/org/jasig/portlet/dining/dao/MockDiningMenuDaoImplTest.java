package org.jasig.portlet.dining.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.portlet.PortletRequest;

import org.jasig.portlet.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.dining.model.menu.xml.Meal;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

public class MockDiningMenuDaoImplTest {
    
    @Autowired
    MockDiningMenuDaoImpl dao;
    
    @Mock PortletRequest request;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetDiningHalls() {
        List<String> diningHalls = dao.getDiningHalls(request);
        assertEquals(1, diningHalls.size());
    }
    
    @Test
    public void setGetMenu() {
        DiningHall hall = dao.getMenu(request, "Berkeley");
        assertEquals("Berkeley", hall.getName());
        assertEquals(2, hall.getMeal().size());
        
        Meal meal = hall.getMeal().get(0);
        assertEquals("Brunch", meal.getName());
        assertEquals(8, meal.getFoodCategory().size());
    }
}
