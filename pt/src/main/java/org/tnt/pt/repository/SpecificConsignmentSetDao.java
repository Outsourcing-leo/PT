package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Country;
import org.tnt.pt.entity.SpecificConsignmentSet;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface SpecificConsignmentSetDao extends BaseDao<SpecificConsignmentSet>{

	void batchInsert(List<SpecificConsignmentSet>  specificConsignmentSetList) ;
	
	void deletespecificConsignmentSetList(SpecificConsignmentSet scs);
	
	List<SpecificConsignmentSet> getAllspecificConsignmentSetByBusId(Long businessId);
	
	List<Country> getALLCountryListinSpec(Map<String,Long> map);
}
