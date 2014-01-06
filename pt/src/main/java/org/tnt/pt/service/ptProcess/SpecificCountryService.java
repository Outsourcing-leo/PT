package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.SpecificCountry;
import org.tnt.pt.repository.SpecificCountryDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class SpecificCountryService {

	private SpecificCountryDao specificCountryDao;

	public List<SpecificCountry> getAllSpecificCountry() {
		return (List<SpecificCountry>) specificCountryDao.findAll();
	}
	
	public List<Long> getCountryIds(Long zoneGroupId,Long busId){
		Map<String,Long> parameter = new HashMap<String,Long>();
		parameter.put("zoneGroupId", zoneGroupId);
		parameter.put("businessId", busId);
		return specificCountryDao.getCountryIds(parameter);
	}
	
	public List<SpecificCountry> getAllCountry(SpecificCountry specificCountry) {
		return (List<SpecificCountry>) specificCountryDao.findAllCountry(specificCountry);
	}
	
	public  SpecificCountry getSpecificCountry(Long id) {
		return specificCountryDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public void add(List<SpecificCountry> specificCountryList){
		specificCountryDao.deleteSpecificCountryList(specificCountryList.get(0));
		specificCountryDao.batchInsert(specificCountryList);
	}

	public void delete(Long businessId){
		specificCountryDao.delete(businessId);
	}
	
	/**
	 * @param specificCountryDao the specificCountryDao to set
	 */
	@Autowired
	public void setSpecificCountryDao(SpecificCountryDao specificCountryDao) {
		this.specificCountryDao = specificCountryDao;
	}
	
}
