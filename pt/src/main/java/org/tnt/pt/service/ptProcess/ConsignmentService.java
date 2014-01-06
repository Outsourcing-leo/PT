package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Consignment;
import org.tnt.pt.repository.ConsignmentDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ConsignmentService {

	private ConsignmentDao consignmentDao;

	public List<Consignment> getAllConsignment() {
		return (List<Consignment>) consignmentDao.findAll();
	}

	public List<Consignment> getAllConsignmentByBusId(Long businessId,String payment) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return (List<Consignment>) consignmentDao.findAllByBusId(parameter);
	}
	
	public List<Consignment> getConsignmentByBusIdandZonegroupId(Long businessId,Long zonegroupId) {
		Map<String,Long> hashMap = new HashMap<String,Long>();
		hashMap.put("businessId", businessId);
		hashMap.put("zoneGroupId",zonegroupId);
		return (List<Consignment>) consignmentDao.findAllByBusIdandZonegroupId(hashMap);
	}
	
	public Consignment getConsignment(Long id) {
		return consignmentDao.get(id);
	}
	
	public void add(List<Consignment> consignmentList){
		consignmentDao.deleteConsignmentList(consignmentList.get(0));
		consignmentDao.batchInsert(consignmentList);
	}
	
	
	public void delete(Long businessId){
		consignmentDao.delete(businessId);
	}

	/**
	 * @param consignmentDao the consignmentDao to set
	 */
	@Autowired
	public void setConsignmentDao(ConsignmentDao consignmentDao) {
		this.consignmentDao = consignmentDao;
	}
	
}
