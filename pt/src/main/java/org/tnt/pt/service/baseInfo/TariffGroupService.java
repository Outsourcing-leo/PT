package org.tnt.pt.service.baseInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.TariffGroup;
import org.tnt.pt.repository.TariffGroupDao;
import org.tnt.pt.service.ServiceException;

/**
 * 时区_公斤段  价格管理
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class TariffGroupService {

	private TariffGroupDao tariffGroupDao;

	public List<TariffGroup> getAllTariffGroup(Long productId,String payment) {
		if(productId==null || payment==null){
			throw new ServiceException("调用Tariff Group 参数错误");
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("productId", productId);
		parameter.put("payment", payment);
		return (List<TariffGroup>) tariffGroupDao.findAllByWeightBand(parameter);
	}
	
	public TariffGroup getTariffGroupByWeightAndWbIdAndType(Double weight,Long weightBandId,String payment){
		if(weight==null || payment==null||weightBandId==null){
			throw new ServiceException("getTariffGroupByWeightAndWbIdAndType 调用Tariff Group 参数错误");
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("weight", weight);
		parameter.put("weightBandId", weightBandId);
		parameter.put("payment", payment);
		return (TariffGroup) tariffGroupDao.getTariffGroupByWeightAndWbIdAndType(parameter);
	}
	
	public TariffGroup getTariffGroupByWbIdAndType(Long weightBandId,String payment){
		if(payment==null||weightBandId==null){
			throw new ServiceException("getTariffGroupByWeightAndWbIdAndType 调用Tariff Group 参数错误");
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("weightBandId", weightBandId);
		parameter.put("payment", payment);
		return (TariffGroup) tariffGroupDao.getTariffGroupByWbIdAndType(parameter);
	}
	
	
	public TariffGroup getTariffGroup(Long id) {
		return tariffGroupDao.get(id);
	}
	
	@Autowired
	public void setTariffGroupDao(TariffGroupDao tariffGroupDao) {
		this.tariffGroupDao = tariffGroupDao;
	}
	
	
	

}
