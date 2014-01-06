package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.GEOSummary;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface GEOSummaryDao extends BaseDao<GEOSummary>{

	List<GEOSummary> findAllByBusinessId(Map<String,Object> parameter);
	
	void batchInsert(List<GEOSummary> geoSummaryList);
	
	void deleteGeoSummaryList(GEOSummary gs);
}
