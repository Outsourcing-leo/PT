package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.WeightBand;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface WeightBandDao {

    WeightBand get(Long id);
	List<WeightBand> findAll();
	List<WeightBand> findAllByProductId(Long productId);
	List<WeightBand> findAllByProductIdWithCommerical(Long productId);
	
	List<WeightBand>  findAllHighWeightBandByProductId(Long productId);
	
	 List<WeightBand> getAllWeightBandByProductIdAndGroupId(Map<String,Long> map);
}
