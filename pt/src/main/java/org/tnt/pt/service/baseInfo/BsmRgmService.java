package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.BsmRgm;
import org.tnt.pt.repository.BsmRgmDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class BsmRgmService {

	private BsmRgmDao bsmRgmDao;

	public List<BsmRgm> getAllBsmRgm() {
		return (List<BsmRgm>) bsmRgmDao.findAll();
	}

	
	public BsmRgm getBsmRgm(Long id) {
		return bsmRgmDao.get(id);
	}
	
	@Autowired
	public void setBsmRgmDao(BsmRgmDao bsmRgmDao) {
		this.bsmRgmDao = bsmRgmDao;
	}

	
}
