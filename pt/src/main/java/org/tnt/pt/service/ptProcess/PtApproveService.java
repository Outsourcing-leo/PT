package org.tnt.pt.service.ptProcess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Review;
import org.tnt.pt.repository.PtApproveDao;
import org.tnt.pt.vo.BusinessVO;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class PtApproveService {

	private PtApproveDao ptApproveDao;

	public List<Review> getReview(BusinessVO businessVO){
		return ptApproveDao.getReview(businessVO);
	}
	
	@Autowired
	public void setBusinessDao(PtApproveDao ptApproveDao) {
		this.ptApproveDao = ptApproveDao;
	}

	
}
