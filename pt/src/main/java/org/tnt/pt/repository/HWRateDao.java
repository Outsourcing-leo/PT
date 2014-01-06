package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.HWRate;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface HWRateDao extends BaseDao<HWRate>{

	HWRate get(Long id);
	
	HWRate getHWRate(Map<String,Long> hashMap );
	
	List<Long> getCountryIds(Map<String,Long> hashMap );
	
	List<HWRate> findAll();
	
	
	void batchInsert(List<HWRate>  hwRateList) ;
	
	List<HWRate> findAllByBusId(Map<String,Object> parameter);
	
	void deleteHwRateList(HWRate hwRate);
	
	
}
