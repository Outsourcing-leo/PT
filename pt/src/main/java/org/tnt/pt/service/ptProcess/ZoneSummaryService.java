package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.ZoneSummary;
import org.tnt.pt.repository.ZoneSummaryDao;

/**
 * 该service 目前处于无用状态
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ZoneSummaryService {
	
	private ZoneSummaryDao zoneSummaryDao;

	public List<ZoneSummary> getAllZoneSummaryByBusinessId(Long businessId,String payment) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return (List<ZoneSummary>) zoneSummaryDao.findAllByBusinessId(parameter);
	}
	
	public ZoneSummary getZoneSummaryByBusinessIdandZoneGroupId(Long businessId,Long zoneGroupId) {
		Map<String,Long> hashMap = new HashMap<String,Long>();
		hashMap.put("businessId", businessId);
		hashMap.put("zoneGroupId",zoneGroupId);
		return  zoneSummaryDao.getZoneSummaryByBusinessIdandZoneGroupId(hashMap);
	}
	
	public void batchInsert(List<ZoneSummary> zoneSummaryList){
		zoneSummaryDao.deleteZoneSummaryList(zoneSummaryList.get(0));
		zoneSummaryDao.batchInsert(zoneSummaryList);
	}

	public void delete(Long businessId){
		zoneSummaryDao.delete(businessId);
	}
	
	/**
	 * @param zoneSummaryDao the zoneSummaryDao to set
	 */
	@Autowired
	public void setZoneSummaryDao(ZoneSummaryDao zoneSummaryDao) {
		this.zoneSummaryDao = zoneSummaryDao;
	}

}
