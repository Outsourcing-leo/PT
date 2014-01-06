package org.tnt.pt.service.baseInfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneTable;
import org.tnt.pt.repository.ZoneGroupDao;
import org.tnt.pt.vo.BaseVO;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ZoneGroupService {

	private ZoneGroupDao zoneGroupDao;

	public List<ZoneGroup> getAllZoneGroup() {
		return (List<ZoneGroup>) zoneGroupDao.findAll();
	}

	
	public ZoneGroup getZoneGroup(Long id) {
		return zoneGroupDao.get(id);
	}
	
	
	public List<ZoneGroup> getAllZoneGroupByZoneType(String zoneType) {
		return (List<ZoneGroup>) zoneGroupDao.findAllByZoneType(zoneType);
	}
	
	public List<ZoneTable> getZoneTable(Map hashMap){
		Integer rowCount = zoneGroupDao.selectByVOCount(hashMap);
		((BaseVO)hashMap.get("baseVO")).getNavigate().setRowCount(rowCount);
		return zoneGroupDao.getZoneTable(hashMap);
	}
	
	@Autowired
	public void setZoneGroupDao(ZoneGroupDao zoneGroupDao) {
		this.zoneGroupDao = zoneGroupDao;
	}
	
	
	

	
}
