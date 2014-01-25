package org.tnt.pt.repository;

import java.util.List;
import java.util.Map;

import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.BusinessFile;
import org.tnt.pt.entity.Exam;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.HWRateVO;





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
	
	List<Tariff> getTariff(Map map);
	
	List<HWRateVO> getHWRateVO(Map map);
	
	List<Business> getBusinessByBusiness(BusinessVO businessVO);
}
