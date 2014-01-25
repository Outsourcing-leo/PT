package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Country;
import org.tnt.pt.entity.SpecificConsignmentSet;
import org.tnt.pt.repository.SpecificConsignmentSetDao;
import org.tnt.pt.service.ServiceException;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class SpecificConsignmentSetService {

	private SpecificConsignmentSetDao specificConsignmentSetDao;

	
	public void add(List<SpecificConsignmentSet> specificConsignmentSetList){
		specificConsignmentSetDao.deletespecificConsignmentSetList(specificConsignmentSetList.get(0));
		specificConsignmentSetDao.batchInsert(specificConsignmentSetList);
	}

	public List<SpecificConsignmentSet> getAllspecificConsignmentSetByBusId(Long businessId,String payment){
		if(businessId==null||payment==null) throw new ServiceException("businessId 或者 payment参数为空"); 
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return specificConsignmentSetDao.getAllspecificConsignmentSetByBusId(parameter);
	}
	
	public List<Country> getALLCountryListinSpec(Long businessId,Long productId,Long zonegroupId,String payment){
		if(businessId==null||productId==null||zonegroupId==null) throw new ServiceException("参数不能为空"); 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", businessId);
		map.put("productId", productId);
		map.put("zonegroupId", zonegroupId);
		map.put("payment", payment);
		return specificConsignmentSetDao.getALLCountryListinSpec(map);
	}
	

	public List<Long> getCountrysInSpec(Long businessId,Long weightbandId,Long zonegroupId,String payment){
		if(businessId==null||weightbandId==null||zonegroupId==null) throw new ServiceException("参数不能为空"); 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", businessId);
		map.put("weightbandId", weightbandId);
		map.put("zonegroupId", zonegroupId);
		map.put("payment", payment);
		return specificConsignmentSetDao.getCountrysInSpec(map);
	}
	
	public void delete(Long businessId){
		specificConsignmentSetDao.delete(businessId);
	}
	/**
	 * @param specificConsignmentSetDao the specificConsignmentSetDao to set
	 */
	@Autowired
	public void setSpecificConsignmentSetDao(SpecificConsignmentSetDao specificConsignmentSetDao) {
		this.specificConsignmentSetDao = specificConsignmentSetDao;
	}

}
