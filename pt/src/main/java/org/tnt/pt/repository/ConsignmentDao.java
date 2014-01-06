package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Consignment;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface ConsignmentDao extends BaseDao<Consignment> {

	Consignment get(Long id);
	
	List<Consignment> findAll();
	
	List<Consignment> findAllByBusId(Map<String,Object> parameter);
	
	List<Consignment>  findAllByBusIdandZonegroupId(Map<String,Long>  hashMap);
	
	void batchInsert(List<Consignment> consignmentList);
	
	void deleteConsignmentList(Consignment cons);
}
