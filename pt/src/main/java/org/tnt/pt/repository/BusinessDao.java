package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.PTSLoad;
import org.tnt.pt.vo.BusinessVO;




/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface BusinessDao extends BaseDao<Business> {

	Business get(Long id);
	
	List<Business> findAll();
	
	void updateTotalRev(Map<String,Object> parameter);
	
	Integer getMaxNum(Map<String,String> hashMap);
	
	void updateBus(Business business);
	
	void updateState(Business business);
	
	List<Business> getBusinessByBusiness(BusinessVO businessVO);
	
	List<PTSLoad> getAnalysePT(String state);
	
	
}
