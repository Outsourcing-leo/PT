package org.tnt.pt.repository;


import java.util.List;

import org.tnt.pt.entity.Country;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface CountryDao {

	Country get(Long id);

	List< Country> findAll();
	
	List<Country> getAllCountryByZoneGroupId(Long zoneGroupId);
	
	List<Country>  findByCode(String countryCode);
	
	List<Country> findBy15NCode(String countryCode);
	List<Country> findBy48NCode(String countryCode);
}
