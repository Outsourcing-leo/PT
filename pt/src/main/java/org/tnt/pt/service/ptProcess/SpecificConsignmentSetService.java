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

	public List<SpecificConsignmentSet> getAllspecificConsignmentSetByBusId(Long businessId){
		if(businessId==null) throw new ServiceException("businessId 参数为空"); 
		return specificConsignmentSetDao.getAllspecificConsignmentSetByBusId(businessId);
	}
	
	public List<Country> getALLCountryListinSpec(Long businessId,Long productId,Long zonegroupId){
		if(businessId==null||productId==null||zonegroupId==null) throw new ServiceException("参数不能为空"); 
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("businessId", businessId);
		map.put("productId", productId);
		map.put("zonegroupId", zonegroupId);
		return specificConsignmentSetDao.getALLCountryListinSpec(map);
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
