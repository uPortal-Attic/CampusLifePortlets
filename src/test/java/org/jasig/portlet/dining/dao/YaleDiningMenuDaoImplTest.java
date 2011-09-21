package org.jasig.portlet.dining.dao;
import java.io.IOException;

import javax.portlet.PortletRequest;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
public class YaleDiningMenuDaoImplTest {

    @Autowired YaleDiningMenuDaoImpl dao;
    @Mock PortletRequest request;
    
    @Autowired
    ApplicationContext ctx;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCleanHtml() throws ScanException, PolicyException, IOException {
        Resource menu = ctx.getResource("classpath:/yale-menu.html");
        String cleaned = dao.getCleanedHtmlContent(IOUtils.toString(menu.getInputStream()));
    }

}