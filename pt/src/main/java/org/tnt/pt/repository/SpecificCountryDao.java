package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.SpecificCountry;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface SpecificCountryDao  extends BaseDao<SpecificCountry>{

	SpecificCountry get(Long id);

	List<SpecificCountry> findAll();
	
	List<SpecificCountry> findAllCountry(SpecificCountry specificCountry);
	
	void batchInsert(List<SpecificCountry> specificCountryList) ;
	
	void deleteSpecificCountryList(SpecificCountry specificCountry);
	
	List<Long> getCountryIds(Map<String,Long> parameter);
}
