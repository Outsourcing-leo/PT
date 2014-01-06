package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.repository.TariffDao;

/**
 * 时区_公斤段  价格管理
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class TariffService {

	private TariffDao tariffDao;

	public List<Tariff> getAllTariff() {
		return (List<Tariff>) tariffDao.findAll();
	}

	
	public Tariff getTariff(Long id) {
		return tariffDao.get(id);
	}
	
	public void add(Tariff tariff) {
		if(isExist(tariff)){
			tariffDao.update(tariff);
		}else{
			tariffDao.insert(tariff);
		}
		
	}
	
	/**
	 * 判断是否存在
	 */
	private boolean isExist(Tariff tariff) {
		int count =  tariffDao.find(tariff);
		if(count>0) return true;
		else return false;
	}

	@Autowired
	public void setTariffDao(TariffDao tariffDao) {
		this.tariffDao = tariffDao;
	}

	
	



	
	
	
}
