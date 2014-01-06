package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Rate;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface RateDao extends BaseDao<Rate>{

	Rate get(Long id);
	
	List<Rate> findAll();
	
	void save(Rate rate);
	
	List<Rate> findAllByBusId(Map<String,Object> parameter);
}
