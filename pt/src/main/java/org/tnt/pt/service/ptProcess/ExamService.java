package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.BusinessFile;
import org.tnt.pt.entity.Exam;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.repository.ExamDao;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.HWRateVO;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ExamService {

	private ExamDao examDao;

	public void insertExam(Exam exam) {
		examDao.insert(exam);
	}

	public void insertFile(BusinessFile businessFile){
		examDao.insertFile(businessFile);
	}

	public void deleteFile(Long ids,String fileName) {
		Map map = new HashMap();
		map.put("ids", ids);
		map.put("fileName", fileName);
		examDao.deleteFile(map);
	}
	
	public String getFilePath(Long ids,String fileName) {
		Map map = new HashMap();
		map.put("ids", ids);
		map.put("fileName", fileName);
		return examDao.getFilePath(map);
	}
	public List<Tariff> getTariff(Long ids,String type,Long productId) {
		Map map = new HashMap();
		map.put("productid", productId);
		map.put("businessid", ids);
		map.put("type", type);
		return (List<Tariff>) examDao.getTariff(map);
	}
	public List<HWRateVO> getHWRateVO(Long ids,String type,Long productId) {
		Map map = new HashMap();
		map.put("productid", productId);
		map.put("businessid", ids);
		map.put("type", type);
		return (List<HWRateVO>) examDao.getHWRateVO(map);
	}
	
	public List<Business> getBusinessByBusiness(BusinessVO businessVO){
		Integer rowCount = examDao.selectByVOCount(businessVO);
		businessVO.getNavigate().setRowCount(rowCount);
		return examDao.getBusinessByBusiness(businessVO);
	}
	
	@Autowired
	public void setExamDao(ExamDao examDao) {
		this.examDao = examDao;
	}
	
	
	
	
}
