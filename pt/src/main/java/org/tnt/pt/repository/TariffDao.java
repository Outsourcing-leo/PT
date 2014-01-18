package org.tnt.pt.repository;


import java.util.List;

import org.tnt.pt.entity.Consignment;
import org.tnt.pt.entity.Tariff;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface TariffDao {

	Tariff get(Long id);
	
	List<Tariff> findAll();
	

	void insert(Tariff tariff);
	
	void update(Tariff tariff);
	
	int find(Tariff tariff);
	
	void deleteTariffList(Tariff tariff);
	
	void batchInsert(List<Tariff> tariffList);
}
