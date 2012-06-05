package org.jasig.portlet.campuslife.dining.dao;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.campuslife.dining.model.menu.xml.DiningHall;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Dish;
import org.jasig.portlet.campuslife.dining.model.menu.xml.FoodCategory;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Meal;
import org.jasig.portlet.campuslife.dining.model.menu.xml.Menu;
import org.jasig.portlet.campuslife.dining.model.menu.xml.MenuDay;
import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class HttpDiningMenuDaoImpl implements IDiningMenuDao {

    protected Log log = LogFactory.getLog(getClass());

    private RestTemplate restTemplate;

    @Autowired(required = true)
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Cache cache;
    
    /**
     * @param cache the cache to set
     */
    @Required
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    
    private String menuUrl;

    @Value("${dining.httpdao.url:http://localhost:8080/CampusLifePortlets/data/menu.xml}")
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    protected Menu getMenu() {
        final Element cachedMenu = this.cache.get("menu");
        if (cachedMenu != null) {
            log.debug("Retrieving menu from cache");
            return (Menu) cachedMenu.getValue();
        } else {
            log.debug("Requesting menu data from " + this.menuUrl);
            final Menu menu = restTemplate.getForObject(menuUrl,
                    Menu.class, Collections.<String, String> emptyMap());
            cache.put(new Element("menu", menu));
            return menu;
        }
    }

    @Override
    public List<DiningHall> getDiningHalls(DateMidnight date,
            PortletRequest request) {
        final Menu menu = getMenu();
        final List<MenuDay> days = menu.getDays();
        for (MenuDay day : days) {
            DateMidnight menuDate = new DateMidnight(day.getDate().getYear(), day.getDate().getMonth(), day.getDate().getDay());
            if (menuDate.equals(date)) {
                return day.getDiningHalls();
            }
        }
        
        log.info("Unable to locate day " + date.toString());
        return null;
    }

    @Override
    public DiningHall getDiningHall(DateMidnight date, String diningHall) {
        final List<DiningHall> halls = getDiningHalls(date, null);
        if (halls != null) {
            for (DiningHall hall : halls) {
                if (hall.getKey().equals(diningHall)) {
                    return hall;
                }
            }
        }

        log.info("Unable to locate dining hall " + diningHall);
        return null;
    }

    @Override
    public Meal getMeal(DateMidnight date, String diningHall, String mealName) {
        final DiningHall hall = getDiningHall(date, diningHall);
        if (hall != null) {
            for (Meal meal : hall.getMeals()) {
                if (meal.getName().equals(mealName)) {
                    return meal;
                }
            }
            
            log.info("Unable to locate meal " + mealName + " at dining hall " + diningHall);
            return null;

        }
        
        return null;
    }

    @Override
    public FoodCategory getFoodCategory(DateMidnight date, String diningHall,
            String mealName, String categoryName) {
        final Meal meal = getMeal(date, diningHall, mealName);
        if (meal != null) {
            for (FoodCategory category : meal.getFoodCategories()) {
                if (category.getName().equals(categoryName)) {
                    return category;
                }
            }
            
            log.info("Unable to locate food category " + categoryName + " for dining hall " + diningHall + " at meal " + mealName);
            return null;
        }
        
        else {
            return null;
        }
    }

    @Override
    public Dish getDish(DateMidnight date, String diningHall, String mealName,
            String dishName) {
        final Meal meal = getMeal(date, diningHall, mealName);
        if (meal != null) {
            for (FoodCategory category : meal.getFoodCategories()) {
                for (Dish dish : category.getDishes()) {
                    if (dish.getName().equals(dishName)) {
                        return dish;
                    }
                }
            }
            
            log.info("Unable to locate dish " + dishName + " for dining hall " + diningHall + " at meal " + mealName);
            return null;
        }
        
        else {
            return null;
        }
    }

}
