package org.jasig.portlet.campuslife.service;

import javax.portlet.PortletRequest;

public interface IURLService {

    public String getLocationUrl(String identifier, PortletRequest request);

}
