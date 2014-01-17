package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.TariffGroup;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface TariffGroupDao {

	TariffGroup get(Long id);
	
	List<TariffGroup> findAllByWeightBand(Map<String,Object> parameter);
	
	TariffGroup getTariffGroupByWeightAndWbIdAndType(Map<String,Object> parameter);
	
	TariffGroup getTariffGroupByWbIdAndType(Map<String,Object> parameter);
}
