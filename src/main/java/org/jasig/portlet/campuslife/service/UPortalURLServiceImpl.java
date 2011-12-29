package org.jasig.portlet.campuslife.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UPortalURLServiceImpl implements IURLService {

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public String getLocationUrl(String identifier, PortletRequest request) {
        try {
            final String encodedLocation = URLEncoder.encode(identifier, "UTF-8");
            return "/uPortal/s/map?location=".concat(encodedLocation);
        } catch (UnsupportedEncodingException e) {
            log.error("Unable to encode location id " + identifier);
            return null;
        }
    }

}
