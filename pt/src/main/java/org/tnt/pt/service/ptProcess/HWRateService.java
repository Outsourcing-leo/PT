package org.tnt.pt.service.ptProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Country;
import org.tnt.pt.entity.HWRate;
import org.tnt.pt.repository.CountryDao;
import org.tnt.pt.repository.HWRateDao;
import org.tnt.pt.service.ServiceException;


/**
 * high Weight rate重货价格
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class HWRateService {

	private HWRateDao HWRateDao;
	private CountryDao countryDao;

	public List<HWRate> getAllHWRate() {
		return (List<HWRate>) HWRateDao.findAll();
	}

	public List<HWRate> getAllHWRateByBusId(Long businessId,String payment) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return (List<HWRate>) HWRateDao.findAllByBusId(parameter);
	}
	
	public HWRate getHWRate(Long id) {
		return HWRateDao.get(id);
	}
	
	public HWRate getHWRate(Long busId,Long weightBandid,Long countryId,Long prodId) {
		Map<String,Long> hashMap = new HashMap<String,Long>();
		if(busId ==null||weightBandid==null||countryId==null||prodId==null){
			throw new ServiceException("参数不能为空"); 
		}
		hashMap.put("businessId", busId);
		hashMap.put("weightBandId",weightBandid);
		hashMap.put("countryId",countryId);
		hashMap.put("productId",prodId);
		return HWRateDao.getHWRate(hashMap);
	}
	
	public List<Country> getCountry(Long busId,Long prodId) {
		Map<String,Long> hashMap = new HashMap<String,Long>();
		if(busId ==null||prodId==null){
			throw new ServiceException("参数不能为空"); 
		}
		hashMap.put("businessId", busId);
		hashMap.put("productId",prodId);
		List<Long> ids = HWRateDao.getCountryIds(hashMap);
		List<Country> countrys = new ArrayList<Country>();
		for (Long id:ids) {
			Country country = new Country();
			country = countryDao.get(id);
			countrys.add(country);
		}
		return countrys;
	}
	 
	public void add(List<HWRate> hwRateList){
		HWRateDao.deleteHwRateList(hwRateList.get(0));
		HWRateDao.batchInsert(hwRateList);
	}
	
	public void delete(Long businessId){
		HWRateDao.delete(businessId);
	}
	
	
	@Autowired
	public void setHWRateDao(HWRateDao hWRateDao) {
		HWRateDao = hWRateDao;
	}
	
	@Autowired
	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

	
	
}
