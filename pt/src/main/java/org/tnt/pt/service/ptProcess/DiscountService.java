package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Discount;
import org.tnt.pt.repository.DiscountDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class DiscountService {

	private DiscountDao discountDao;

	public List<Discount> getAllDiscount() {
		return (List<Discount>) discountDao.findAll();
	}

	public List<Discount> getAllDiscountByBusId(Long businessId,String payment) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return (List<Discount>) discountDao.findAllByBusId(parameter);
	}
	
	public Discount getDiscount(Long id) {
		return discountDao.get(id);
	}
	
	public void add(List<Discount> discountList){
		discountDao.deleteDiscountList(discountList.get(0));
		discountDao.batchInsert(discountList);
	}

	public void delete(Long businessId){
		discountDao.delete(businessId);
	}
	
	@Autowired
	public void setDiscountDao(DiscountDao discountDao) {
		this.discountDao = discountDao;
	}



	
	
	
}
