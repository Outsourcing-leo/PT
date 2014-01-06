package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Discount;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface DiscountDao extends BaseDao<Discount>{

	Discount get(Long id);
	
	List<Discount> findAll();
	
	List<Discount> findAllByBusId(Map<String,Object> parameter);
	
	void batchInsert(List<Discount> discountList);
	
	void  deleteDiscountList(Discount discount);
}
