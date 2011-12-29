package org.jasig.portlet.campuslife.laundry.dao;

import java.util.List;

import javax.portlet.PortletRequest;

import org.jasig.portlet.campuslife.laundry.model.laundry.xml.Laundromat;

public interface ILaundryDao {

    public Laundromat getDefaultLaundromat(PortletRequest request);
    
    public List<Laundromat> getLaundromats(PortletRequest request);

}
