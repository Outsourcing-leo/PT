package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Rev;
import org.tnt.pt.repository.RevDao;
import org.tnt.pt.service.ServiceException;
import org.tnt.pt.vo.RevVO;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class RevService {

	private RevDao revDao;

	
	public void add(List<Rev> revList){
		revDao.deleteRevList(revList.get(0));
		revDao.batchInsert(revList);
	}
	
	public List<RevVO> getGroupBy(Long businessId,String[] groupBy,String payment){
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		groupBy = returnGroupBy(groupBy);
		for (String str : groupBy) {
			if(!str.equals("")){
				parameter.put(str, str);
			}
		}
		return revDao.getgroupBy(parameter);
	}
	//new pt使用
	public List<RevVO> getGroupBy_(Long businessId,String[] groupBy,String payment){
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("payment", payment);
		groupBy = returnGroupBy(groupBy);
		for (String str : groupBy) {
			if(!str.equals("")){
				parameter.put(str, str);
			}
		}
		return revDao.getgroupBy_(parameter);
	}
	
	public Rev getCountryGroupBy(Long businessId,Long countryId,String payment){
		if(businessId==null || countryId==null){
			throw new ServiceException("参数不能为空");
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("businessId", businessId);
		parameter.put("countryId", countryId);
		parameter.put("payment", payment);
		return revDao.getCountrygroupBy(parameter);
	}
	
	public void delete(Long businessId){
		revDao.delete(businessId);
	}

	public String[] returnGroupBy(String[] sourceStrings){
		for(int i = 1;i<sourceStrings.length;i++){
			if(sourceStrings[i].equals("Country")) sourceStrings[i] = "countryId";
			if(sourceStrings[i].equals("Product")) sourceStrings[i] = "productId";
			if(sourceStrings[i].equals("PT Zone")) sourceStrings[i] = "zoneGroupId";
			if(sourceStrings[i].equals("WeightBand")) sourceStrings[i] = "weightBandId";
		}
		return sourceStrings;
	}
	
	/**
	 * @param consignmentDao the consignmentDao to set
	 */
	@Autowired
	public void setRevDao(RevDao revDao) {
		this.revDao = revDao;
	}

	
	

	
	



	
	
	
}
