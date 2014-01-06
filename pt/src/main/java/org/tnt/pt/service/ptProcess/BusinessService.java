package org.tnt.pt.service.ptProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.DateProvider;
import org.tnt.pt.dmsentity.User;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.PTSLoad;
import org.tnt.pt.repository.BusinessDao;
import org.tnt.pt.repository.ConsignmentDao;
import org.tnt.pt.repository.CustomerDao;
import org.tnt.pt.repository.DiscountDao;
import org.tnt.pt.repository.GEOSummaryDao;
import org.tnt.pt.repository.RateDao;
import org.tnt.pt.repository.SpecificConsignmentSetDao;
import org.tnt.pt.repository.SpecificCountryDao;
import org.tnt.pt.repository.ZoneSummaryDao;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BusCusVO;
import org.tnt.pt.vo.BusinessVO;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class BusinessService {

	private BusinessDao businessDao;
	private CustomerDao customerDao;
	private DateProvider dateProvider = DateProvider.DEFAULT;
	private ConsignmentDao consignmentDao;
	private DiscountDao discountDao;
	private GEOSummaryDao geoSummaryDao;
	private RateDao rateDao;
	private SpecificCountryDao specificCountryDao;
	private SpecificConsignmentSetDao specificConsignmentSetDao;
	private ZoneSummaryDao zoneSummaryDao;
	
	public List<Business> getAllBusiness() {
		return (List<Business>) businessDao.findAll();
	}

	public void deleteBusiness(Long id){
		businessDao.delete(id);
		//删除相关业务表
		consignmentDao.delete(id);
		discountDao.delete(id);
		geoSummaryDao.delete(id);
		rateDao.delete(id);
		specificCountryDao.delete(id);
		specificConsignmentSetDao.delete(id);
		zoneSummaryDao.delete(id);
	}
	
	public Business getBusiness(Long id) {
		return businessDao.get(id);
	}
	
	public void updateTotalRev(Double tolRev,Long businessId){
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("totalRev", tolRev);
		parameter.put("businessId", businessId);
		businessDao.updateTotalRev(parameter);
	}
	
	public List<Business> getBusinessByBusiness(BusinessVO businessVO){
		Integer rowCount = businessDao.selectByVOCount(businessVO);
		businessVO.getNavigate().setRowCount(rowCount);
		return businessDao.getBusinessByBusiness(businessVO);
	}
	
	//根据用户的角色和depot查找需要审批的pt
	public List<Business> getBusinessByUser(User user,BusinessVO businessVO){
		String state = "";//该state应根据user而定
		if(user!=null){
			if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[0])){
				state = PTPARAMETERS.PROCESS_SATE[0];
			}else if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[1])){
				state = PTPARAMETERS.PROCESS_SATE[1];
			}else if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[3])){
				state = PTPARAMETERS.PROCESS_SATE[4];
			}
		}
		Integer rowCount = businessDao.selectByVOCount(businessVO);
		businessVO.getNavigate().setRowCount(rowCount);
		businessVO.setState(state);
		return businessDao.getBusinessByBusiness(businessVO);
	}
	
	
	//New PTs Loading Applications
	public List<PTSLoad> getAnalysePT(String state){
		return businessDao.getAnalysePT(state);
	}
	
	public Integer getMaxNum(String depotcode,String date,String channel){
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("depotCode", depotcode);
		hashMap.put("date",date);
		hashMap.put("channel",channel);
		
		int num ;
		num = businessDao.getMaxNum(hashMap)==null?0:businessDao.getMaxNum(hashMap);
		Integer suffix;
		if(num == 0){
			suffix = 1;
		}else{
			suffix = num +1;
		}
		return suffix;
	}
	
	@Transactional(readOnly = false)
	public void updateBusAndCus(BusCusVO busCus) {
		Business business = new Business();
		Customer customer = new Customer();
		business = busCus.getBusiness();
		customer = busCus.getCustomer();
		
		customerDao.updateCus(customer);
		
		businessDao.updateBus(business);
	
	}
	
	@Transactional(readOnly = false)
	public Long insert(BusCusVO busCus) {
		Business business = new Business();
		Customer customer = new Customer();
		business = busCus.getBusiness();
		customer = busCus.getCustomer();
		
		customerDao.insert(customer);
		business.setCustomerId(customer.getId());
		business.setApplicationDate(dateProvider.getDate());
		businessDao.insert(business);
		Long  businessId = business.getId();
	
		return businessId;
	}
	
	public void update(Business business){
		businessDao.update(business);
	}
	
	public void updateState(Business business){
		businessDao.updateState(business);
	}
	
	@Autowired
	public void setBusinessDao(BusinessDao businessDao) {
		this.businessDao = businessDao;
	}

	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@Autowired
	public void setConsignmentDao(ConsignmentDao consignmentDao) {
		this.consignmentDao = consignmentDao;
	}
	
	@Autowired
	public void setDiscountDao(DiscountDao discountDao) {
		this.discountDao = discountDao;
	}
	
	@Autowired
	public void setGEOSummaryDao(GEOSummaryDao geoSummaryDao) {
		this.geoSummaryDao = geoSummaryDao;
	}
	
	@Autowired
	public void setRateDao(RateDao rateDao) {
		this.rateDao = rateDao;
	}
	
	@Autowired
	public void setSpecificCountryDao(SpecificCountryDao specificCountryDao) {
		this.specificCountryDao = specificCountryDao;
	}
	
	@Autowired
	public void setSpecificConsignmentSetDao(SpecificConsignmentSetDao specificConsignmentSetDao) {
		this.specificConsignmentSetDao = specificConsignmentSetDao;
	}
	
	@Autowired
	public void setZoneSummaryDao(ZoneSummaryDao zoneSummaryDao) {
		this.zoneSummaryDao = zoneSummaryDao;
	}
	
	
}
