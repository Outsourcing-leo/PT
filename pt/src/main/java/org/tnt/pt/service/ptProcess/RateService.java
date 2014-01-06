package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Rate;
import org.tnt.pt.repository.RateDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class RateService {

	private RateDao rateDao;

	public List<Rate> getAllRate() {
		return (List<Rate>) rateDao.findAll();
	}

	public List<Rate> getAllRateByBusId(Long businessId,String payment) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return (List<Rate>) rateDao.findAllByBusId(parameter);
	}
	
	public Rate getRate(Long id) {
		return rateDao.get(id);
	}
	 
	public void save(Rate rate) {
		rateDao.save(rate);
	}
	
	public void delete(Long businessId){
		rateDao.delete(businessId);
	}

	@Autowired
	public void setRateDao(RateDao rateDao) {
		this.rateDao = rateDao;
	}
	
}
