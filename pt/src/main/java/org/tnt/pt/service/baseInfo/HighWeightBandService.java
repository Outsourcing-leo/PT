package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.HighWeightBand;
import org.tnt.pt.repository.HighWeightBandDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class HighWeightBandService {

	private HighWeightBandDao highWeightBandDao;

	public List<HighWeightBand> getAllHighWeightBand() {
		return (List<HighWeightBand>) highWeightBandDao.findAll();
	}

	
	public HighWeightBand getHighWeightBand(Long id) {
		return highWeightBandDao.get(id);
	}
	

	@Autowired
	public void setHighWeightBandDao(HighWeightBandDao highWeightBandDao) {
		this.highWeightBandDao = highWeightBandDao;
	}
	

	
}
