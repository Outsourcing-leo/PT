package org.tnt.pt.web.ptProcess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tnt.pt.dmsentity.User;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Discount;
import org.tnt.pt.entity.GEOSummary;
import org.tnt.pt.entity.PTSLoad;
import org.tnt.pt.entity.Rate;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneSummary;
import org.tnt.pt.entity.ZoneTable;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.service.baseInfo.CountryGeoService;
import org.tnt.pt.service.baseInfo.CountryZoneService;
import org.tnt.pt.service.baseInfo.ProductService;
import org.tnt.pt.service.baseInfo.TariffService;
import org.tnt.pt.service.baseInfo.WeightBandService;
import org.tnt.pt.service.baseInfo.ZoneGroupService;
import org.tnt.pt.service.baseInfo.ZoneTypeService;
import org.tnt.pt.service.ptProcess.BusinessService;
import org.tnt.pt.service.ptProcess.ConsignmentService;
import org.tnt.pt.service.ptProcess.CustomerService;
import org.tnt.pt.service.ptProcess.DiscountService;
import org.tnt.pt.service.ptProcess.GeoSummaryService;
import org.tnt.pt.service.ptProcess.RateService;
import org.tnt.pt.service.ptProcess.RevService;
import org.tnt.pt.service.ptProcess.SpecificConsignmentSetService;
import org.tnt.pt.service.ptProcess.SpecificCountryService;
import org.tnt.pt.service.ptProcess.ZoneSummaryService;
import org.tnt.pt.util.DoubleUtil;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BaseVO;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.RevVO;

/**
 * ProductController负责产品的请求，
 * 
 * @author yuanchen
 */
@Controller
@RequestMapping(value = "/ptQuery")
public class PTQueryController{
    @Autowired
	BusinessService businessService;
    @Autowired
	ZoneGroupService zonegroupService;
    @Autowired
	ZoneTypeService zoneTypeService;
    @Autowired
	WeightBandService weightBandService;
    @Autowired
	CustomerService customerService;
    @Autowired
	ProductService productService;
    @Autowired
	DiscountService discountService;
    @Autowired
	TariffService tariffService;
    @Autowired
	RateService rateService;
    @Autowired
   	GeoSummaryService geoSummaryService;
    @Autowired
    ZoneSummaryService zoneSummaryService;
    @Autowired
    CountryGeoService countryGeoService;
    @Autowired
    CountryZoneService countryZoneService;
    @Autowired
    ConsignmentService consignmentService;
    @Autowired
    SpecificConsignmentSetService specificConsignmentSetService;
    @Autowired
    SpecificCountryService specificCountryService; 
    @Autowired
    RevService revService;
	/**
	 * 公斤_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="tariffPT", method = RequestMethod.GET)
	public String rateDetail(Model model) {
		/**
		 * 该处为保存该pt下折扣信息代码
		 */
		//List<WeightBand> weightBandList = new ArrayList<WeightBand>();
		List<Rate> rateList = new ArrayList<Rate>();
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> economyList = new ArrayList<WeightBand>();
		List<Discount> discountList = new ArrayList<Discount>();
		Map<String,Double> rateMap = new HashMap<String,Double>();//形成价格map 方便查询
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		
		business = businessService.getBusiness(0L);//1L为保存后获得的PT业务主表id
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		customer = customerService.getCustomer(business.getCustomerId());//客户信息
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		economyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		Long businessId = business.getId();
		discountList = discountService.getAllDiscountByBusId(businessId,customer.getPayment());
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		
		rateList = rateService.getAllRateByBusId(businessId,customer.getPayment());
		for (Rate rate:rateList) {
			rateMap.put(rate.getWeightBandId()+"_"+rate.getZoneGroupId(), rate.getRate());
		}
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("zoneType", zoneType);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("economyList", economyList);
		model.addAttribute("rateMap", rateMap);
		model.addAttribute("discountMap", discountMap);
		
		return "ptProcess/tariffPT";
	}

	/**
	 * 公斤_时区_折扣  详细页 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value="tariffPT/{id}", method = RequestMethod.GET)
	public String rateDetail(Model model,@PathVariable("id") Long id) {
		
		/**
		 * 该处为保存该pt下折扣信息代码
		 */
		//List<WeightBand> weightBandList = new ArrayList<WeightBand>();
		List<Tariff> tariffList = new ArrayList<Tariff>();
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> economyList = new ArrayList<WeightBand>();
		List<Discount> discountList = new ArrayList<Discount>();
		List<Discount> recDiscountList = new ArrayList<Discount>();
		Map<String,Double> rateMap = new HashMap<String,Double>();//形成价格map 方便查询
		Map<String,Double> recRateMap = new HashMap<String,Double>();//形成价格map 方便查询
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> recDiscountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		ZoneSummary zoneSummary = new ZoneSummary();
		ZoneSummary recZoneSummary = new ZoneSummary();
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		String flag = "";//标识是否需要两套数据展示
		business = businessService.getBusiness(id);//1L为保存后获得的PT业务主表id
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		customer = customerService.getCustomer(business.getCustomerId());//客户信息
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		economyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		flag = customer.getPayment();
		if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
			discountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[0]);
			recDiscountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[1]);
			for (Discount discount:recDiscountList) {
				recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
			}
			/**
			 * 获取汇总信息
			 */
			String[] groupBy_1 = {};
			RevVO revVO = revService.getGroupBy(business.getId(),groupBy_1,PTPARAMETERS.PAYMENT[0]).get(0);
			zoneSummary.setConsM(revVO.getCons());
			zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			zoneSummary.setKiloM(revVO.getKilo());
			zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			zoneSummary.setRevM(revVO.getRev());
			zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
			/**
			 * 获取汇总信息
			 */
			revVO = null;
			revVO = revService.getGroupBy(business.getId(),groupBy_1,PTPARAMETERS.PAYMENT[1]).get(0);
			recZoneSummary.setConsM(revVO.getCons());
			recZoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			recZoneSummary.setKiloM(revVO.getKilo());
			recZoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			recZoneSummary.setRevM(revVO.getRev());
			recZoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
		}else if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
			discountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[2]);//都取both
			recDiscountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[2]);
			for (Discount discount:recDiscountList) {
				recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
			}
			/**
			 * 获取汇总信息
			 */
			String[] groupBy_1 = {};
			RevVO revVO = revService.getGroupBy(business.getId(),groupBy_1,PTPARAMETERS.PAYMENT[2]).get(0);
			zoneSummary.setConsM(revVO.getCons());
			zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			zoneSummary.setKiloM(revVO.getKilo());
			zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			zoneSummary.setRevM(revVO.getRev());
			zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
		}else{
			discountList = discountService.getAllDiscountByBusId(id,customer.getPayment());
			/**
			 * 获取汇总信息
			 */
			String[] groupBy_1 = {};
			RevVO revVO = revService.getGroupBy(business.getId(),groupBy_1,customer.getPayment()).get(0);
			zoneSummary.setConsM(revVO.getCons());
			zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			zoneSummary.setKiloM(revVO.getKilo());
			zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			zoneSummary.setRevM(revVO.getRev());
			zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
		}
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		tariffList = tariffService.getAllTariff();
		for (Tariff tariff:tariffList) {
			String keyId = tariff.getWeightBandId()+"_"+tariff.getZoneGroupId();
			if(discountMap.get(keyId)!=null){
				rateMap.put(keyId, tariff.getTariff()*discountMap.get(keyId)/100);
			}
			if(recDiscountMap.size()>1){
				if(recDiscountMap.get(keyId)!=null){
					recRateMap.put(keyId, tariff.getTariff()*recDiscountMap.get(keyId)/100);
				}
			}
			
		}
		model.addAttribute("flag",flag);
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("zoneType", zoneType);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("economyList", economyList);
		model.addAttribute("rateMap", rateMap);
		model.addAttribute("recRateMap", recRateMap);
		model.addAttribute("discountMap", discountMap);
		model.addAttribute("recDiscountMap", recDiscountMap);
		model.addAttribute("zoneSummary", zoneSummary);
		model.addAttribute("recZoneSummary", recZoneSummary);
		return "ptProcess/tariffPT";
	}
	
	/**
	 * copy 初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value="copy", method = RequestMethod.POST)
	public String copy(Model model) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model", businessVO);
		return "query/copyPTQuery";
	}
	
	/**
	 * PT QUERY 初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryPTInit", method = RequestMethod.GET)
	public String ptQueryInit(Model model) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, -3); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		businessVO.setStartDate(sdf.format(calendar.getTime()));
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		businessVO.setStartDate(null);
		model.addAttribute("model", businessVO);
		return "query/queryPT";
	}
	
	
	/**
	 * PT QUERY
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryPT", method = RequestMethod.POST)
	public String ptQuery(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "query/queryPT";
	}
	
	/**
	 * PT QUERY 发送邮件
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appeal", method = RequestMethod.POST)
	public String appeal(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		EmailSend cn = new EmailSend();
		// 设置发件人地址、收件人地址和邮件标题    获得commercial的邮箱即可
		cn.send("the applicationReference of "+businessVO.getApplicationReference()+"is waiting for approve!","24136471@qq.com");
		businessList = businessService.getBusinessByBusiness(new BusinessVO());
		businessVO.setApplicationReference("");
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "query/queryPT";
	}
	
	/**
	 * PT MODIFY 初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value="ptModifyInit", method = RequestMethod.GET)
	public String ptModifyInit(Model model) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model", businessVO);
		return "query/modifyPT";
	}
	
	/**
	 * PT MODIFY
	 * @param model
	 * @return
	 */
	@RequestMapping(value="ptModify", method = RequestMethod.POST)
	public String ptModify(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "query/modifyPT";
	}
	
	/**
	 * PT delete
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deletePT", method = RequestMethod.POST)
	public String deletePT(Model model,@ModelAttribute BusinessVO businessVO) {
		Long businessId = Long.parseLong(businessVO.getId());
		businessService.deleteBusiness(businessId);
		List<Business> businessList = new ArrayList<Business>();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "query/modifyPT";
	}
	
	/**
	 * 修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updateCustomer/{id}", method = RequestMethod.POST)
	public String updateCustomer(Model model,@PathVariable("id") Long businessId) {
		Business business = businessService.getBusiness(businessId);
		Customer cus = customerService.getCustomer(business.getCustomerId());
		model.addAttribute("customer", cus);
		model.addAttribute("business", business);
		return "newPT/copyPTCustomer";
	}
	
	/**
	 * PT QUERY 初始 历史数据 默认三个月前的
	 * @param model
	 * @return
	 */
	@RequestMapping(value="hisPTQueryInit", method = RequestMethod.GET)
	public String hisPTQueryInit(Model model) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, -3); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		businessVO.setEndDate(sdf.format(calendar.getTime()));
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "query/hisPTQuery";
	}
	
	
	/**
	 * PT QUERY
	 * @param model
	 * @return
	 */
	@RequestMapping(value="hisPTQuery", method = RequestMethod.POST)
	public String hisPTQuery(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.MONTH, -3); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		businessVO.setEndDate(sdf.format(calendar.getTime()));
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "query/hisPTQuery";
	}
	
	/**
	 * PT Approve
	 * @param model
	 * @return
	 */
	@RequestMapping(value="ptApprove", method = RequestMethod.GET)
	public String ptApprove(Model model,HttpServletRequest request) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		User user = (User) request.getSession().getAttribute("user");
		businessList = businessService.getBusinessByUser(user,businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/approvePT";
	}
	
	/**
	 * PT PTSLoad
	 * @param model
	 * @return
	 */
	@RequestMapping(value="PTSLoadInit", method = RequestMethod.GET)
	public String PTSLoadInit(Model model,HttpServletRequest request) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		User user = (User) request.getSession().getAttribute("user");
		businessList = businessService.getBusinessByUser(user,businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/PTSLoad";
	}
	
	/**
	 * PT PTSLoad
	 * @param model
	 * @return
	 */
	@RequestMapping(value="PTSLoad", method = RequestMethod.POST)
	public String PTSLoad(Model model,@ModelAttribute BusinessVO businessVO,HttpServletRequest request) {
		List<Business> businessList = new ArrayList<Business>();
		User user = (User) request.getSession().getAttribute("user");
		businessList = businessService.getBusinessByUser(user,businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/PTSLoad";
	}
	/**
	 * PT confirmRate 初始化
	 * @param model
	 * @return
	 */
	@RequestMapping(value="confirmRateInit", method = RequestMethod.GET)
	public String confirmRateInit(Model model) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		businessVO.setState(PTPARAMETERS.PROCESS_SATE[3]);
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/confirmRate";
	}
	
	
	/**
	 * PT confirmRate
	 * @param model
	 * @return
	 */
	@RequestMapping(value="confirmRate", method = RequestMethod.POST)
	public String confirmRate(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		businessVO.setState(PTPARAMETERS.PROCESS_SATE[3]);
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/confirmRate";
	}
	
	/**
	 * PT confirmToBilling 初始化 该节点即上面
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value="confirmToBillingInit", method = RequestMethod.GET)
	public String confirmToBillingInit(Model model) {
		List<Business> businessList = new ArrayList<Business>();
		BusinessVO businessVO = new BusinessVO();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/confirmToBilling";
	}
	
	
	*//**
	 * PT confirmToBilling
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value="confirmToBilling", method = RequestMethod.POST)
	public String confirmToBilling(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/confirmToBilling";
	}*/
	
	/**
	 * PT Analyse a PT
	 * @param model
	 * @return
	 */
	@RequestMapping(value="analysePT", method = RequestMethod.GET)
	public String analysePT(Model model) {
		String state = PTPARAMETERS.PROCESS_SATE[2];
		List<PTSLoad> loadList = businessService.getAnalysePT(state);
		model.addAttribute("loadList", loadList);
		return "ptProcess/analysePT";
	}
	
	/**
	 * PT confirmToBilling
	 * @param model
	 * @return
	 */
	@RequestMapping(value="analyseDetailPool", method = RequestMethod.POST)
	public String analyseDetailPool(Model model,@ModelAttribute BusinessVO businessVO) {
		List<Business> businessList = new ArrayList<Business>();
		businessVO.setState(PTPARAMETERS.PROCESS_SATE[2]);
		businessList = businessService.getBusinessByBusiness(businessVO);
		model.addAttribute("businessList", businessList);
		model.addAttribute("model",businessVO);
		return "ptProcess/analyseDetailPool";
	}
	
	/**
	 * PT confirmToBilling
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getZoneTableInit", method = RequestMethod.GET)
	public String getZoneTableInit(Model model) {
		List<ZoneTable> zoneTableList = new ArrayList<ZoneTable>();
		List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
		zoneTypeList =  zoneTypeService.getAllZoneType();
		Map hashMap = new HashMap();
		
		hashMap.put("baseVO", new BaseVO());
		if(zoneTypeList.size()>0){
			ZoneType zoneType = zoneTypeList.get(0);
			hashMap.put("type", zoneType.getZoneType());
			zoneTableList = zonegroupService.getZoneTable(hashMap);
		}
		model.addAttribute("zoneTableList", zoneTableList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		model.addAttribute("model",hashMap.get("baseVO"));
		return "ptProcess/zoneTable";
	}
	
	
	/**
	 * PT confirmToBilling
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getZoneTable", method = RequestMethod.POST)
	public String getZoneTable(Model model,@RequestParam(value = "id", required = false) Long id,BaseVO baseVO) {
		ZoneType zoneType = zoneTypeService.getZoneTypeById(id);
		List<ZoneTable> zoneTableList = new ArrayList<ZoneTable>();
		List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
		zoneTypeList =  zoneTypeService.getAllZoneType();
		Map hashMap = new HashMap();
		hashMap.put("type", zoneType.getZoneType());
		hashMap.put("baseVO", baseVO);
		zoneTableList = zonegroupService.getZoneTable(hashMap);
		model.addAttribute("zoneTableList", zoneTableList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		model.addAttribute("model",hashMap.get("baseVO"));
		return "ptProcess/zoneTable";
	}
	
	/**
	 * summaryInfo 详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="summaryInfoProcess/{id}", method = RequestMethod.GET)
	public String summaryInfoProcess(Model model,@PathVariable("id") Long id) {
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		List<ZoneSummary> zoneSummaryList = new ArrayList<ZoneSummary>();
		List<GEOSummary> geoSummaryList = new ArrayList<GEOSummary>();
		List<ZoneSummary> recZoneSummaryList = new ArrayList<ZoneSummary>();
		List<GEOSummary> recGeoSummaryList = new ArrayList<GEOSummary>();
		List<Discount> discountList = new ArrayList<Discount>();
		List<Discount> recDiscountList = new ArrayList<Discount>();
		List<RevVO> revList = new ArrayList<RevVO>();
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> recDiscountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		ZoneSummary zoneSummary = new ZoneSummary();
		ZoneSummary recZoneSummary = new ZoneSummary();
		String[] groupBy = {"zoneGroupId"};
		business = businessService.getBusiness(id);
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
		
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		String flag = "";//标识是否需要两套数据展示
		flag = customer.getPayment();
		if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
			geoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),PTPARAMETERS.PAYMENT[0]);
			recZoneSummaryList = zoneSummaryService.getAllZoneSummaryByBusinessId(business.getId(),PTPARAMETERS.PAYMENT[1]);
			recGeoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),PTPARAMETERS.PAYMENT[1]);
			discountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[0]);
			recDiscountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[1]);
			for (Discount discount:recDiscountList) {
				recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
			}
			revList = revService.getGroupBy(business.getId(),groupBy,PTPARAMETERS.PAYMENT[0]);
			for (RevVO rev : revList) {
				ZoneSummary zs = new ZoneSummary();
				zs.setConsM(rev.getCons());
				zs.setConsY(DoubleUtil.get2Double(rev.getCons()*12));
				zs.setKiloM(rev.getKilo());
				zs.setKiloY(DoubleUtil.get2Double(rev.getKilo()*12));
				zs.setRevM(rev.getRev());
				zs.setRevY(DoubleUtil.get2Double(rev.getRev()*12));
				zs.setZoneType(rev.getZone());
				zoneSummaryList.add(zs);
			}
			/**
			 * 获取汇总信息
			 */
			String[] groupBy_1 = {};
			RevVO revVO = revService.getGroupBy(business.getId(),groupBy_1,PTPARAMETERS.PAYMENT[0]).get(0);
			zoneSummary.setConsM(revVO.getCons());
			zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			zoneSummary.setKiloM(revVO.getKilo());
			zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			zoneSummary.setRevM(revVO.getRev());
			zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
			revList = null;
			revList = revService.getGroupBy(business.getId(),groupBy,PTPARAMETERS.PAYMENT[1]);
			for (RevVO rev : revList) {
				ZoneSummary zs = new ZoneSummary();
				zs.setConsM(rev.getCons());
				zs.setConsY(DoubleUtil.get2Double(rev.getCons()*12));
				zs.setKiloM(rev.getKilo());
				zs.setKiloY(DoubleUtil.get2Double(rev.getKilo()*12));
				zs.setRevM(rev.getRev());
				zs.setRevY(DoubleUtil.get2Double(rev.getRev()*12));
				zs.setZoneType(rev.getZone());
				recZoneSummaryList.add(zs);
			}
			/**
			 * 获取汇总信息
			 */
			revVO = null;
			revVO = revService.getGroupBy(business.getId(),groupBy_1,PTPARAMETERS.PAYMENT[1]).get(0);
			recZoneSummary.setConsM(revVO.getCons());
			recZoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			recZoneSummary.setKiloM(revVO.getKilo());
			recZoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			recZoneSummary.setRevM(revVO.getRev());
			recZoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
			
		}else if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
			geoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),PTPARAMETERS.PAYMENT[2]);
			recZoneSummaryList = zoneSummaryService.getAllZoneSummaryByBusinessId(business.getId(),PTPARAMETERS.PAYMENT[2]);
			recGeoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),PTPARAMETERS.PAYMENT[2]);
			discountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[2]);//都取both
			recDiscountList = discountService.getAllDiscountByBusId(id,PTPARAMETERS.PAYMENT[2]);
			for (Discount discount:recDiscountList) {
				recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
			}
			
			revList = revService.getGroupBy(business.getId(),groupBy,PTPARAMETERS.PAYMENT[2]);
			for (RevVO rev : revList) {
				ZoneSummary zs = new ZoneSummary();
				zs.setConsM(rev.getCons());
				zs.setConsY(DoubleUtil.get2Double(rev.getCons()*12));
				zs.setKiloM(rev.getKilo());
				zs.setKiloY(DoubleUtil.get2Double(rev.getKilo()*12));
				zs.setRevM(rev.getRev());
				zs.setRevY(DoubleUtil.get2Double(rev.getRev()*12));
				zs.setZoneType(rev.getZone());
				zoneSummaryList.add(zs);
			}
			/**
			 * 获取汇总信息
			 */
			String[] groupBy_1 = {};
			RevVO revVO = revService.getGroupBy(business.getId(),groupBy_1,PTPARAMETERS.PAYMENT[2]).get(0);
			zoneSummary.setConsM(revVO.getCons());
			zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			zoneSummary.setKiloM(revVO.getKilo());
			zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			zoneSummary.setRevM(revVO.getRev());
			zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
		}else{
			zoneSummaryList = zoneSummaryService.getAllZoneSummaryByBusinessId(business.getId(),customer.getPayment());
			geoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),customer.getPayment());
			discountList = discountService.getAllDiscountByBusId(id,customer.getPayment());
			
			revList = revService.getGroupBy(business.getId(),groupBy,customer.getPayment());
			for (RevVO rev : revList) {
				ZoneSummary zs = new ZoneSummary();
				zs.setConsM(rev.getCons());
				zs.setConsY(DoubleUtil.get2Double(rev.getCons()*12));
				zs.setKiloM(rev.getKilo());
				zs.setKiloY(DoubleUtil.get2Double(rev.getKilo()*12));
				zs.setRevM(rev.getRev());
				zs.setRevY(DoubleUtil.get2Double(rev.getRev()*12));
				zs.setZoneType(rev.getZone());
				zoneSummaryList.add(zs);
			}
			/**
			 * 获取汇总信息
			 */
			String[] groupBy_1 = {};
			RevVO revVO = revService.getGroupBy(business.getId(),groupBy_1,customer.getPayment()).get(0);
			zoneSummary.setConsM(revVO.getCons());
			zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
			zoneSummary.setKiloM(revVO.getKilo());
			zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
			zoneSummary.setRevM(revVO.getRev());
			zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
		}
		
		model.addAttribute("flag", flag);
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("geoSummaryList", geoSummaryList);
		model.addAttribute("zoneSummaryList", zoneSummaryList);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("recZoneSummaryList", recZoneSummaryList);
		model.addAttribute("recGeoSummaryList", recGeoSummaryList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("eonomyList", eonomyList);
		model.addAttribute("zoneSummary", zoneSummary);
		model.addAttribute("recZoneSummary", recZoneSummary);
		
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		model.addAttribute("recDiscountMap", recDiscountMap);
		model.addAttribute("discountMap", discountMap);
		
		return "ptProcess/summaryInfoProgress";
	}
}
