package org.tnt.pt.repository;

import java.util.Map;

import org.tnt.pt.entity.BusinessFile;
import org.tnt.pt.entity.Exam;





/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepository
public interface ExamDao extends BaseDao<Exam>{

	public Integer insertFile(BusinessFile bFile);
	
	public void deleteFile(Map map);
	
	public String getFilePath(Map map);
}
