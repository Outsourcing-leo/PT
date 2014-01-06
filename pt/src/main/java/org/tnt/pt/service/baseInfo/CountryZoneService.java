package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.CountryZone;
import org.tnt.pt.repository.CountryZoneDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class CountryZoneService {

	private CountryZoneDao countryZoneDao;

	public List<CountryZone> getAllCountryZone() {
		return (List<CountryZone>) countryZoneDao.findAll();
	}
	
	
	public List<CountryZone> getCountryZoneByCountryId(Long countryId) {
		return (List<CountryZone>) countryZoneDao.findAllByCountryId(countryId);
	}
	
	public List<String> getAllGEO(){
		return countryZoneDao.getAllGEO();
	}
	public List<Long>	getAllCountryByGeo(String geo){
		return countryZoneDao.getAllCountryByGeo(geo);
	}

	public List<Long>	getAllCountryByZoneGroup(Long zonegroupId){
		return countryZoneDao.getAllCountryByZoneGroup(zonegroupId);
	}
	
	public  CountryZone getCountryZone(Long id) {
		return countryZoneDao.get(id);
	}

	@Autowired
	public void setCountryZoneDao(CountryZoneDao countryZoneDao) {
		this.countryZoneDao = countryZoneDao;
	}
	

	
}
