package org.tnt.pt.service.ptProcess;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.BusinessFile;
import org.tnt.pt.entity.Exam;
import org.tnt.pt.repository.ExamDao;

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
	
	@Autowired
	public void setExamDao(ExamDao examDao) {
		this.examDao = examDao;
	}
	
	
	
	
}
