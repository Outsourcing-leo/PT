package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.repository.ZoneTypeDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ZoneTypeService {

	private ZoneTypeDao zoneTypeDao;

	public List<ZoneType> getAllZoneType() {
		return (List<ZoneType>) zoneTypeDao.findAll();
	}
	
	public ZoneType getZoneTypeByZoneType(String zoneType) {
		return zoneTypeDao.getZoneTypeByZoneType(zoneType);
	}
	
	public ZoneType getZoneTypeById(Long id) {
		return zoneTypeDao.getZoneTypeById(id);
	}

	@Autowired
	public void setZoneTypeDao(ZoneTypeDao zoneTypeDao) {
		this.zoneTypeDao = zoneTypeDao;
	}
	
	
}
