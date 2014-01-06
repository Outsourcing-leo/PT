package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Country;
import org.tnt.pt.repository.CountryDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class CountryService {

	private CountryDao countryDao;

	public List<Country> getAllCountry() {
		return (List<Country>) countryDao.findAll();
	}
	public List<Country> getAllCountryByZoneGroupId(Long zoneGroupId) {
		return (List<Country>) countryDao.getAllCountryByZoneGroupId(zoneGroupId);
	}
	
	public List<Country> findByName(String countryName) {
		return  countryDao.findByName(countryName);
	}
	
	public Country getCountry(Long id) {
		return countryDao.get(id);
	}
	

	@Autowired
	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	

	
}
