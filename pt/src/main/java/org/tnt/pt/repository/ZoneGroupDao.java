package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneTable;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface ZoneGroupDao {

	ZoneGroup get(Long id);

	List<ZoneGroup> findAll();
	
	List<ZoneGroup> findAllByZoneType(String zoneType);
	
	List<ZoneTable> getZoneTable(Map hashMap);
	
	public Integer selectByVOCount(Map hashMap) ;
}
