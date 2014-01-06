package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.GEOSummary;
import org.tnt.pt.repository.GEOSummaryDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class GeoSummaryService {

	private GEOSummaryDao geoSummaryDao;

	public List<GEOSummary> getAllGeoSummaryByBusinessId(Long businessId,String payment) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		return (List<GEOSummary>) geoSummaryDao.findAllByBusinessId(parameter);
	}
	
	public void batchInsert(List<GEOSummary>  geoSummaryList) {
		 geoSummaryDao.deleteGeoSummaryList(geoSummaryList.get(0));
		 geoSummaryDao.batchInsert(geoSummaryList);
	}

	public void delete(Long businessId){
		geoSummaryDao.delete(businessId);
	}
	
	/**
	 * @param geoSummaryDao the geoSummaryDao to set
	 */
	@Autowired
	public void setGeoSummaryDao(GEOSummaryDao geoSummaryDao) {
		this.geoSummaryDao = geoSummaryDao;
	}
}
