package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.CountryGeo;
import org.tnt.pt.repository.CountryGeoDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class CountryGeoService {

	private CountryGeoDao countryGeoDao;

	public List<CountryGeo> getAllCountryGeo() {
		return (List<CountryGeo>) countryGeoDao.findAll();
	}

	
	public CountryGeo getCountryGeo(Long id) {
		return countryGeoDao.get(id);
	}
	
	public List<String> getAllGEO(){
		return countryGeoDao.getAllGEO();
	}
	public List<Long>	getAllCountryByGeo(String geo){
		return countryGeoDao.getAllCountryByGeo(geo);
	}

	@Autowired
	public void setCountryGeoDao(CountryGeoDao countryGeoDao) {
		this.countryGeoDao = countryGeoDao;
	}
	
	
	

	
}
