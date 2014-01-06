package org.tnt.pt.repository;


import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Rev;
import org.tnt.pt.vo.RevVO;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface RevDao  extends BaseDao<Rev>{
	
	void batchInsert(List<Rev> revList);
	
	void deleteRevList(Rev rev);
	
	Rev getCountrygroupBy(Map<String,Object> parameter);
	
	List<RevVO> getgroupBy(Map<String,Object> parameter);
}
