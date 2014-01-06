package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.DiscountDefault;
import org.tnt.pt.repository.DiscountDefaultDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class DiscountDefaultService {

	private DiscountDefaultDao discountDefaultDao;

	public List<DiscountDefault> getAllDiscountDefault() {
		return (List<DiscountDefault>) discountDefaultDao.findAll();
	}
	public DiscountDefault getDiscountDefault(Long id) {
		return discountDefaultDao.get(id);
	}
	
	public void add(DiscountDefault discountDefault) {
		if(isExist(discountDefault)){
			discountDefaultDao.update(discountDefault);
		}else{
			discountDefaultDao.insert(discountDefault);
		}
		
	}
	
	/**
	 * 判断是否存在
	 */
	private boolean isExist(DiscountDefault discountDefault) {
		int count =  discountDefaultDao.find(discountDefault);
		if(count>0) return true;
		else return false;
	}
	/**
	 * @param discountDefaultDao the discountDefaultDao to set
	 */
	@Autowired
	public void setDiscountDefaultDao(DiscountDefaultDao discountDefaultDao) {
		this.discountDefaultDao = discountDefaultDao;
	}
	
}
