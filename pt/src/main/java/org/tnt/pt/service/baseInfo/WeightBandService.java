package org.tnt.pt.service.baseInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.repository.WeightBandDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class WeightBandService {

	private WeightBandDao weightBandDao;

	public List<WeightBand> getAllWeightBand() {
		return (List<WeightBand>) weightBandDao.findAll();
	}
	
	public List<WeightBand> getAllWeightBandByProductId(Long ProductId) {
		return (List<WeightBand>) weightBandDao.findAllByProductId(ProductId);
	}
	
	public List<WeightBand> getAllWeightBandByProductIdAndGroupId(Long ProductId,Long groupId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("productId", ProductId);
		map.put("groupId", groupId);
		return (List<WeightBand>) weightBandDao.getAllWeightBandByProductIdAndGroupId(map);
	}
	
	public List<WeightBand> getAllWeightBandByProductIdWithCommerical(Long ProductId) {
		return (List<WeightBand>) weightBandDao.findAllByProductIdWithCommerical(ProductId);
	}
	
	
	public List<WeightBand> getAllHighWeightBandByProductId(Long ProductId) {
		return (List<WeightBand>) weightBandDao.findAllHighWeightBandByProductId(ProductId);
	}
	
	public WeightBand getWeightBand(Long id) {
		return weightBandDao.get(id);
	}

	@Autowired
	public void setWeightBandDao(WeightBandDao weightBandDao) {
		this.weightBandDao = weightBandDao;
	}
	
}
