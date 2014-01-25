package org.tnt.pt.web.ptProcess;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import org.tnt.pt.dmsentity.User;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Consignment;
import org.tnt.pt.entity.Country;
import org.tnt.pt.entity.CountryZone;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Discount;
import org.tnt.pt.entity.DiscountDefault;
import org.tnt.pt.entity.GEOSummary;
import org.tnt.pt.entity.HWRate;
import org.tnt.pt.entity.HighWeightBand;
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.Rate;
import org.tnt.pt.entity.Rev;
import org.tnt.pt.entity.SpecificConsignmentSet;
import org.tnt.pt.entity.SpecificCountry;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.entity.TariffGroup;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneSummary;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.service.baseInfo.CountryGeoService;
import org.tnt.pt.service.baseInfo.CountryService;
import org.tnt.pt.service.baseInfo.CountryZoneService;
import org.tnt.pt.service.baseInfo.DiscountDefaultService;
import org.tnt.pt.service.baseInfo.ProductService;
import org.tnt.pt.service.baseInfo.TariffGroupService;
import org.tnt.pt.service.baseInfo.TariffService;
import org.tnt.pt.service.baseInfo.WeightBandService;
import org.tnt.pt.service.baseInfo.ZoneGroupService;
import org.tnt.pt.service.baseInfo.ZoneTypeService;
import org.tnt.pt.service.dms.FsiService;
import org.tnt.pt.service.downPDF.PDFGenerater;
import org.tnt.pt.service.ptProcess.BusinessService;
import org.tnt.pt.service.ptProcess.ConsignmentService;
import org.tnt.pt.service.ptProcess.CustomerService;
import org.tnt.pt.service.ptProcess.DiscountService;
import org.tnt.pt.service.ptProcess.GeoSummaryService;
import org.tnt.pt.service.ptProcess.HWRateService;
import org.tnt.pt.service.ptProcess.RateService;
import org.tnt.pt.service.ptProcess.RevService;
import org.tnt.pt.service.ptProcess.SpecificConsignmentSetService;
import org.tnt.pt.service.ptProcess.SpecificCountryService;
import org.tnt.pt.service.ptProcess.ZoneSummaryService;
import org.tnt.pt.util.DateUtil;
import org.tnt.pt.util.DoubleUtil;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BusCusVO;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.JsonData;
import org.tnt.pt.vo.RevVO;

import com.itextpdf.text.pdf.qrcode.Version.ECB;

/**
 * ProductController负责产品的请求，
 * 
 * @author yuanchen
 */
@Controller
@RequestMapping(value = "/ptCreate")
public class PTCreateController {
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
   	DiscountDefaultService discountdefaultService;
    @Autowired
   	ConsignmentService consignmentService;
    @Autowired
   	CountryService countryService;
    @Autowired
   	SpecificCountryService specificCountryService;
    @Autowired
   	SpecificConsignmentSetService specificConsignmentSetService;
    @Autowired
   	GeoSummaryService geoSummaryService;
    @Autowired
    ZoneSummaryService zoneSummaryService;
    @Autowired
    CountryGeoService countryGeoService;
    @Autowired
    CountryZoneService countryZoneService;
    @Autowired
    HWRateService hwRateService;
    @Autowired
    RevService revService;
    @Autowired
    FsiService fsiService;
    @Autowired
    TariffGroupService tariffGroupService;
    
	@RequestMapping(value="addCustomer", method = RequestMethod.GET)
	public String addCustomer() {
		return "newPT/addPTCustomer";
	}
	
	/**
	 * 新建页面copy
	 * @param model
	 * @return
	 */
	@RequestMapping(value="copyCustomer/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String copyCustomer(Model model,@PathVariable("id") Long businessId) {
		Business business = businessService.getBusiness(businessId);
		Customer cus = customerService.getCustomer(business.getCustomerId());
		BusCusVO busCusVO = new BusCusVO();
		busCusVO.setBusiness(business);
		busCusVO.setCustomer(cus);
		String cusstr = JsonMapper.nonDefaultMapper().toJson(cus);
		//model.addAttribute("customer", cus);
		//model.addAttribute("business", business);
		return cusstr;
	}
	
	/**
	 * 产品_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="disConfirm/{type}", method = RequestMethod.POST)
	public String disConfirm(HttpServletRequest request,Model model,@ModelAttribute BusCusVO busCus,@PathVariable("type") String type) {
		User user = (User) request.getSession().getAttribute("user");
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = busCus.getBusiness();
		customer = busCus.getCustomer();	
		String payment = "";
		//如果是yes 并且 payment 为空 择进入SenderPay页面
		if("NO".equals(busCus.getIsFollow())&&("".equals(busCus.getPayment())||PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment()))){
			payment=PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[1].equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment="both";
		}else{
			payment=customer.getPayment();
		}

		/**
		 * 保存business 和 customer
		 */
		if("add".equals(type)){
			if(busCus.getBusiness().getId()!=null&&("".equals(busCus.getBusiness().getZoneType())||busCus.getBusiness().getZoneType()==null)){
				businessService.updateBusAndCus(busCus);
			}
			if(busCus.getBusiness().getId()==null&&("".equals(busCus.getBusiness().getZoneType())||busCus.getBusiness().getZoneType()==null)){
					String date = DateUtil.getStringFromDate(new Date(), "yyyyMMdd");
					Integer suffix = businessService.getMaxNum("SHA",date,busCus.getCustomer().getChannel())==null?0:businessService.getMaxNum("SHA",date,busCus.getCustomer().getChannel());
					String suff = new String();
					int len = String.valueOf(suffix).length();
					if(len == 1){
						suff = "00"+suffix.toString();
					}else if(len == 2){
						suff = "0"+suffix.toString();
					}else{
						suff = suffix.toString();
					}
					busCus.getBusiness().setApplicationReference("PT-"+user.getCode()+"-"+date+"-"+busCus.getCustomer().getChannel()+"-"+suff);// 编号 需要自动生成
					busCus.getBusiness().setZoneType("11ZONE");
					busCus.getBusiness().setState("creating");
					busCus.getBusiness().setSuffix(suffix);
					busCus.getBusiness().setIsFollow(busCus.getIsFollow());
					busCus.getBusiness().setUserName(user.getUserName());
					businessService.insert(busCus);
			}else if(!"".equals(busCus.getBusiness().getZoneType())&&busCus.getBusiness().getZoneType()!=null){
				businessService.updateBusAndCus(busCus);
			}
		}else if("confirm".equals(type)){
			businessService.update(busCus.getBusiness());
		}
		
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		/**
		 * 初始化数据
		 */
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
		
		List<Product> productList = new ArrayList<Product>();
		List<DiscountDefault> discountDefaultList = new ArrayList<DiscountDefault>();
		Map<String,Double> discountDefaultMap = new HashMap<String,Double>();//形成折扣map 方便查询
		
		
		/**
		 * 假如zonetype不为空，则默认初始加载的为第一个zonetype
		 */
		zoneTypeList =  zoneTypeService.getAllZoneType();
		
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		productList.add(productService.getProduct(zoneType.getDocument()));
		productList.add(productService.getProduct(zoneType.getNonDocument()));
		productList.add(productService.getProduct(zoneType.getEconomy()));
		
		discountDefaultList = discountdefaultService.getAllDiscountDefault();
		for (DiscountDefault discountDefault:discountDefaultList) {
			discountDefaultMap.put(discountDefault.getProductId()+"_"+discountDefault.getZoneGroupId(), discountDefault.getDiscount());
		}
		
		model.addAttribute("business", businessService.getBusiness(business.getId()));
		model.addAttribute("customer", customer);
		model.addAttribute("zoneType_", zoneType.getZoneType());
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		model.addAttribute("productList", productList);
		model.addAttribute("discountDefaultMap", discountDefaultMap);
		
		model.addAttribute("isFollow", busCus.getIsFollow());
		model.addAttribute("payment",payment);
		
		return "newPT/disConfirm";
	}
	
	/**
	 * 公斤_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="disConProfile", method = RequestMethod.POST)
	public String disConProfile(Model model,@ModelAttribute BusCusVO busCus) {
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		String payment = "";
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment = "both";
		}else{
			payment = customer.getPayment();
		}
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		List<Discount> discountList = new ArrayList<Discount>();
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		discountList = discountService.getAllDiscountByBusId(business.getId(),payment);
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("zoneType", zoneType);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", getWeightBandList(documentList));
		model.addAttribute("ndocumentList", getWeightBandList(ndocumentList));
		model.addAttribute("eonomyList", getWeightBandList(eonomyList));
		model.addAttribute("discountMap", discountMap);
		
		model.addAttribute("isFollow", busCus.getIsFollow());
		model.addAttribute("payment",payment);
		
		return "newPT/disConProfile";
	}
	
	/**
	 * 根据weightbandGroup的值 只保留一个weightband
	 * @param wbs
	 * @return
	 */
	public List<WeightBand> getWeightBandList(List<WeightBand> wbs){
		List<WeightBand> wbList = new ArrayList<WeightBand>();
		Map<Long,WeightBand> wbMap = new HashMap<Long,WeightBand>();
		for(WeightBand wb : wbs){
			Long weightBandGroupId = wb.getWeightbandGroupId();
			wbMap.put(weightBandGroupId, wb);
		}
		for (Iterator i = wbMap.values().iterator(); i.hasNext();) {
			wbList.add((WeightBand)i.next());
	    }
		//根据weightbandgroupid值进行排序
		ComparatorWeightBand comparator=new ComparatorWeightBand();
		Collections.sort(wbList, comparator);
		return wbList;
	}
	
	class ComparatorWeightBand implements Comparator{
		 public int compare(Object arg0, Object arg1) {
			 WeightBand weightBand0=(WeightBand)arg0;
			 WeightBand weightBand1=(WeightBand)arg1;
		     //首先比较年龄，如果年龄相同，则比较名字
		    int flag=weightBand0.getWeightbandGroupId().compareTo(weightBand1.getWeightbandGroupId());
		    return flag;
		 }
	}
	
	/**
	 * specificCountry  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="specificCountry/{productId}/{zoneGroupId}", method = RequestMethod.GET)
	public String specificCountry(Model model,@PathVariable("productId") Long productId,@PathVariable("zoneGroupId") Long zoneGroupId
			,HttpServletRequest request) {
		
		ZoneGroup zoneGroup = new ZoneGroup();
		Product product = new Product();
		List<Country> countryList = new ArrayList<Country>();
		
		Long businessId = Long.valueOf(request.getParameter("businessId"));
		String payment = request.getParameter("payment");
		
		List<SpecificCountry> checkedCountryList = new ArrayList<SpecificCountry>();
		SpecificCountry specificCountry = new SpecificCountry();
		specificCountry.setBusinessId(businessId);
		specificCountry.setProductId(productId);
		specificCountry.setZoneGroupId(zoneGroupId);
		checkedCountryList = specificCountryService.getAllCountry(specificCountry);
		String checked ="";
		for (int i = 0; i < checkedCountryList.size(); i++) {
			checked += checkedCountryList.get(i).getCountryId()+";";
		}
		
		zoneGroup = zonegroupService.getZoneGroup(zoneGroupId);
		product =  productService.getProduct(productId);
		countryList = countryService.getAllCountryByZoneGroupId(zoneGroupId);
		
		model.addAttribute("zoneGroup", zoneGroup);
		model.addAttribute("product", product);
		model.addAttribute("countryList", countryList);
		model.addAttribute("checked", checked);
		model.addAttribute("businessId", businessId);
		model.addAttribute("payment", payment);
		
		return "newPT/specificCountry";
	}
	
	@RequestMapping(value="addspecificCountry/{payment}", method = {RequestMethod.POST })
	@ResponseBody 
	public String addspecificCountry(@RequestBody String jsonDatas,@PathVariable("payment") String payment) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		List<SpecificCountry>  specificCountryList = new ArrayList<SpecificCountry>();//折扣集合 批量插入
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
			for (JsonData jsonData:jsonDataList) {
				SpecificCountry specificCountry = new SpecificCountry();
				String name = jsonData.getName();
				String[] discountArr = name.split("_");
				specificCountry.setBusinessId(Long.valueOf(discountArr[0]));
				specificCountry.setCountryId(Long.valueOf(discountArr[3]));
				specificCountry.setProductId(Long.valueOf(discountArr[1]));
				specificCountry.setZoneGroupId(Long.valueOf(discountArr[2]));
				specificCountry.setPayment(payment);
				specificCountryList.add(specificCountry);
			}
			specificCountryService.add(specificCountryList);
		} catch (ParseException e) {
			msg = e.getMessage();
		}
		return msg;
	}
	
	
	@RequestMapping(value="addDiscount/{payment}", method = {RequestMethod.POST })
	@ResponseBody 
	public String addDiscount(@RequestBody String jsonDatas,@PathVariable("payment") String payment) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		List<Discount>  discountList = new ArrayList<Discount>();//折扣集合 批量插入
		List<WeightBand> wbList =  new ArrayList<WeightBand>(); //weightband集合
		Long defineProductId = -1L;
		
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
			for (JsonData jsonData:jsonDataList) {
				String name = jsonData.getName();
				String[] discountArr = name.split("_");
				Long pageProductId = Long.valueOf(discountArr[1]);
				if(defineProductId==-1L || defineProductId !=pageProductId ){
					defineProductId = pageProductId;
					wbList = weightBandService.getAllWeightBandByProductId(defineProductId);
				}
				if( wbList.size()>0){
					for (int i = 0,length = wbList.size(); i < length; i++) {
						Discount dd = new Discount();
						dd.setWeightBandId(wbList.get(i).getId());
						dd.setZoneGroupId(Long.valueOf(discountArr[2]));
						String value = jsonData.getValue();
						dd.setDiscount(Double.valueOf((value==null||"".equals(value))?"0":value));
						dd.setBusinessId(Long.valueOf(discountArr[3]));
						dd.setPayment(payment);
						discountList.add(dd);
					}
				}
			}
			discountService.add(discountList);
		} catch (ParseException e) {
			msg = e.getMessage();
		}
		return msg;
	}
	
	@RequestMapping(value="updateDiscount/{payment}", method = {RequestMethod.POST })
	@ResponseBody 
	public String updateDiscount(@RequestBody String jsonDatas,@PathVariable("payment") String payment) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		List<Discount>  discountList = new ArrayList<Discount>();//折扣集合 批量插入
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
			for (JsonData jsonData:jsonDataList) {
				String name = jsonData.getName();
				String value = jsonData.getValue();
				
				String[] discountArr = name.split("_");
				WeightBand wb = weightBandService.getWeightBand(Long.valueOf(discountArr[1]));
				Long weightbandGroupId = wb.getWeightbandGroupId();
				Long productId = wb.getProductId();
				List<WeightBand> wbs = weightBandService.getAllWeightBandByProductIdAndGroupId(productId,weightbandGroupId);
				
				for(WeightBand wb_ : wbs){
					Discount dd = new Discount();
					dd.setWeightBandId(wb_.getId());
					dd.setZoneGroupId(Long.valueOf(discountArr[2]));
					dd.setDiscount(Double.valueOf((value==null||"".equals(value))?"0":value));
					dd.setBusinessId(Long.valueOf(discountArr[3]));
					dd.setPayment(payment);
					discountList.add(dd);
				}
				
			}
			discountService.add(discountList);
		} catch (ParseException e) {
			msg = e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 重货_城市_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="hwRateProfile", method = RequestMethod.POST)
	public String hwRateProfile(Model model,@ModelAttribute BusCusVO busCus) {
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		String payment = "";
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment = "both";
		}else{
			payment = customer.getPayment();
		}
		
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		String ndocumentIds = new String();
		String eonomyIds = new String();
		List<HWRate> hwRateList = new ArrayList<HWRate>();
		Map<String,Double> hwRateMap = new HashMap<String,Double>();//形成折扣map 方便查询
		List<Country> ndocountrys = new ArrayList<Country>();
		List<Country> ecocountrys = new ArrayList<Country>();
		
		ndocumentList = weightBandService.getAllHighWeightBandByProductId(zoneType.getNonDocument());//获取重货
		eonomyList = weightBandService.getAllHighWeightBandByProductId(zoneType.getEconomy());//获取重货
		
		ndocountrys = hwRateService.getCountry(business.getId(), zoneType.getNonDocument());
		ecocountrys = hwRateService.getCountry(business.getId(), zoneType.getEconomy());
		
		for (WeightBand wb : ndocumentList) {
			ndocumentIds +=wb.getId()+";";
		}
		
		for (WeightBand wb : eonomyList) {
			eonomyIds +=wb.getId()+";";
		}
		
		hwRateList = hwRateService.getAllHWRateByBusId(business.getId(),payment);
		for (HWRate hwRate:hwRateList) {
			hwRateMap.put(hwRate.getBusinessId()+"_"+hwRate.getProductId()+"_"+hwRate.getWeightBandId()+"_"+hwRate.getCountryId(), hwRate.getRate());
		}
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("eonomyList", eonomyList);
		model.addAttribute("ndocumentIds", ndocumentIds);
		model.addAttribute("eonomyIds", eonomyIds);
		
		model.addAttribute("eonomy", zoneType.getEconomy());
		model.addAttribute("ndocument", zoneType.getNonDocument());
		model.addAttribute("ndocumentCountrys", ndocountrys);
		model.addAttribute("eonomyCountrys", ecocountrys);
		model.addAttribute("hwRateMap", hwRateMap);
		
		
		model.addAttribute("isFollow", busCus.getIsFollow());
		model.addAttribute("payment",payment);
				
		return "newPT/hwRateProfile";
	}
	
	@RequestMapping(value="addHwRate/{payment}", method = {RequestMethod.POST })
	@ResponseBody 
	public String addHwRate(@RequestBody String jsonDatas,@PathVariable("payment") String payment) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		Map<String,List<JsonData>> jdmap = new HashMap<String,List<JsonData>>();
		Map<String,List<JsonData>> jdnewmap = new HashMap<String,List<JsonData>>();
		List<HWRate> hwrateList = new ArrayList<HWRate>();
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
			for (JsonData jsonData:jsonDataList) {
				String condition = jsonData.getName().substring(jsonData.getName().lastIndexOf("_")+1);
				if(jsonData.getName().contains("tb1")){
					List<JsonData> jds = jdmap.get("tb1_"+condition)==null?new ArrayList<JsonData>():jdmap.get("tb1_"+condition);
					jds.add(jsonData);
					jdmap.put("tb1_"+condition,jds);
				}else{
					List<JsonData> jds = jdmap.get("tb2_"+condition)==null?new ArrayList<JsonData>():jdmap.get("tb2_"+condition);
					jds.add(jsonData);
					jdmap.put("tb2_"+condition,jds);
				}
				
			}
			Set<String> key = jdmap.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            if(s.contains("tb1")){
	            	List<JsonData> jds = jdmap.get(s);
	            	Long keys = 0L;
	            	for (JsonData jd: jds) {
						if(jd.getName().contains("tb1_country_name")){
							Long countryId = countryService.getIdByCountryCode(StringUtils.upperCase(jd.getValue().trim()));
							keys = countryId;
						}
					}
	            	jdnewmap.put("tb1_"+keys, jds);
	            }else{
	            	List<JsonData> jds = jdmap.get(s);
	            	Long keys = 0L;
	            	for (JsonData jd: jds) {
						if(jd.getName().contains("tb2_country_name")){
							//keys = jd.getValue();
							Long countryId = countryService.getIdByCountryCode(StringUtils.upperCase(jd.getValue().trim()));
							keys = countryId;
						}
					}
	            	jdnewmap.put("tb2_"+keys, jds);
	            }
	        }
	    	Set<String> keySet = jdnewmap.keySet();
	        for (Iterator it = keySet.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            //if(s.contains("tb1")){
	            	List<JsonData> jds = jdnewmap.get(s);
	            	for (JsonData jd: jds) {
	            		if(!jd.getName().contains("country")){
		            		HWRate hw = new HWRate();
		            		String value = jd.getValue();
		            		String[] nameArr = jd.getName().split("_");
		            		hw.setBusinessId(Long.valueOf(nameArr[2]));
		            		hw.setCountryId(Long.valueOf(s.substring(s.lastIndexOf("_")+1)));
		            		hw.setWeightBandId(Long.valueOf(nameArr[1]));
		            		hw.setRate(Double.valueOf((value==null||"".equals(value))?"0":value));
		            		hw.setProductId(Long.valueOf(nameArr[3]));
		            		hw.setPayment(payment);
		            		hwrateList.add(hw);
	            		}
					}
	           /* }else{
	            	List<JsonData> jds = jdnewmap.get(s);
	            	for (JsonData jd: jds) {
	            		if(!jd.getName().contains("country")){
		            		HWRate hw = new HWRate();
		            		String value = jd.getValue();
		            		hw.setBusinessId(Long.valueOf(jd.getName().split("_")[2]));
		            		hw.setCountryId(Long.valueOf(s.substring(s.lastIndexOf("_")+1)));
		            		hw.setWeightBandId(Long.valueOf(jd.getName().split("_")[1]));
		            		hw.setRate(Double.valueOf((value==null||"".equals(value))?"0":value));
		            		hwrateList.add(hw);
	            		}
					}
	            }*/
	        }
			hwRateService.add(hwrateList);
		} catch (ParseException e) {
			msg = e.getMessage();
		}
		return msg;
	}
	/**
	 * 公斤_时区_consignment 详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="consProfile/{isHighWight}", method = RequestMethod.POST)
	public String consProfile(Model model,@ModelAttribute BusCusVO busCus,@PathVariable("isHighWight") String isHighWight) {
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		String payment = "";
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment = "both";
		}else{
			payment = customer.getPayment();
		}
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		List<Consignment>  consignmentList = new ArrayList<Consignment>();//con数量集合 批量插入
		Map<String,Integer> consignmentMap = new HashMap<String,Integer>();//形成取件数量map 方便查询
		
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		consignmentList = consignmentService.getAllConsignmentByBusId(business.getId(),payment);
		for (Consignment consignment:consignmentList) {
			consignmentMap.put(consignment.getWeightBandId()+"_"+consignment.getZoneGroupId(), consignment.getConsignment());
		}
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("zoneType", zoneType);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("eonomyList", eonomyList);
		model.addAttribute("consignmentMap", consignmentMap);
		
		model.addAttribute("isFollow", busCus.getIsFollow());
		model.addAttribute("payment",payment);
		model.addAttribute("isHighWight",isHighWight);
		
		return "newPT/consignmentProfile";
	}
	
	/**
	 * specificConsignmentSet  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="specificConsignmentSet/{productId}/{zoneGroupId}", method = RequestMethod.GET)
	public String specificConsignmentSet(Model model,@PathVariable("productId") Long productId,@PathVariable("zoneGroupId") Long zoneGroupId
			,HttpServletRequest request) {
		
		ZoneGroup zoneGroup = new ZoneGroup();
		Product product = new Product();
		List<Country> countryList = new ArrayList<Country>();
		//获得已经存入的国家consignment
		List<Country> countryListinSpec = new ArrayList<Country>();
		List<WeightBand> weightBandList = new ArrayList<WeightBand>();
		List<SpecificConsignmentSet> specificConsignmentSets=  new ArrayList<SpecificConsignmentSet>();
		Map<String,Integer> scMap = new HashMap<String,Integer>();//形成取件数量map 方便查询
		
		Long businessId = Long.valueOf(request.getParameter("businessId"));
		String payment = request.getParameter("payment");
		
		zoneGroup = zonegroupService.getZoneGroup(zoneGroupId);
		product =  productService.getProduct(productId);
		countryList = countryService.getAllCountryByZoneGroupId(zoneGroupId);
		weightBandList = weightBandService.getAllWeightBandByProductId(productId);
		countryListinSpec = specificConsignmentSetService.getALLCountryListinSpec(businessId,productId,zoneGroupId,payment);
		//获取 特殊国家 的 cons
		specificConsignmentSets = specificConsignmentSetService.getAllspecificConsignmentSetByBusId(businessId,payment);
		for (SpecificConsignmentSet sc:specificConsignmentSets) {
			scMap.put(sc.getBusinessId()+"_"+sc.getProductId()+"_"+sc.getZoneGroupId()+"_"+sc.getWeightBandId()+"_"+sc.getCountryId(), sc.getConsignment());
		}
		
		
		model.addAttribute("zoneGroup", zoneGroup);
		model.addAttribute("product", product);
		model.addAttribute("countryLists", JsonMapper.nonDefaultMapper().toJson(countryList));
		model.addAttribute("businessId", businessId);
		model.addAttribute("weightBandList", weightBandList);
		model.addAttribute("weightBandLists", JsonMapper.nonDefaultMapper().toJson(weightBandList));
		model.addAttribute("payment", payment);
		model.addAttribute("scMap", scMap);
		model.addAttribute("countryListinSpec", countryListinSpec);
		
		return "newPT/specificConsignmentSet";
	}
	
	@RequestMapping(value="addSpecificConsignmentSet/{payment}", method = {RequestMethod.POST })
	@ResponseBody 
	public String addSpecificConsignmentSet(@RequestBody String jsonDatas,@PathVariable("payment") String payment) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		List<SpecificConsignmentSet>  specificConsignmentSetList = new ArrayList<SpecificConsignmentSet>();//折扣集合 批量插入
		Map<String,Integer> consMap = new HashMap<String,Integer>();
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
			for (JsonData jsonData:jsonDataList) {
				SpecificConsignmentSet specificConsignmentSet = new SpecificConsignmentSet();
				String name = jsonData.getName();
				String[] discountArr = name.split("_");
				specificConsignmentSet.setBusinessId(Long.valueOf(discountArr[0]));
				specificConsignmentSet.setCountryId(Long.valueOf(discountArr[4]));
				specificConsignmentSet.setProductId(Long.valueOf(discountArr[1]));
				specificConsignmentSet.setZoneGroupId(Long.valueOf(discountArr[2]));
				specificConsignmentSet.setWeightBandId(Long.valueOf(discountArr[3]));
				String value = jsonData.getValue();
				specificConsignmentSet.setConsignment(Integer.valueOf((value==null||"".equals(value))?"0":value));
				specificConsignmentSet.setPayment(payment);
				specificConsignmentSetList.add(specificConsignmentSet);
				if(consMap.containsKey(discountArr[3])){
					consMap.put(discountArr[3],consMap.get(discountArr[3])+Integer.valueOf((value==null||"".equals(value))?"0":value));
				}else{
	
					consMap.put(discountArr[3],Integer.valueOf((value==null||"".equals(value))?"0":value));
				}
			}
		    Set<String> key = consMap.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            String value = s+"_"+consMap.get(s);
	            msg+=value+";";
	        }
	        specificConsignmentSetService.add(specificConsignmentSetList);
		} catch (ParseException e) {
			msg = e.getMessage();
		}
		System.out.println(msg);
		return msg;
	}
	
	
	@RequestMapping(value="addConsignment/{payment}", method = {RequestMethod.POST })
	@ResponseBody 
	public String addConsignment(@RequestBody String jsonDatas,@PathVariable("payment") String payment) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		List<Consignment>  consignmentList = new ArrayList<Consignment>();//con数量集合 批量插入
		List<Discount>  discountList = new ArrayList<Discount>();//discount数量集合 批量插入
		List<Tariff>  tariffList = new ArrayList<Tariff>();//tariff数量集合 批量插入
		List<WeightBand>  wpList = new ArrayList<WeightBand>();//WeightBand数量集合 批量插入
		List<Rev>  revList = new ArrayList<Rev>();//Rev数量集合 批量插入
		List<Rev>  revNewList = new ArrayList<Rev>();//Rev数量集合 批量插入
		//List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<Long> countryIds = new ArrayList<Long>();
		List<Long> defaultCountryIds = new ArrayList<Long>();
		List<CountryZone> countryZones= new ArrayList<CountryZone>();
		List<SpecificConsignmentSet> specificConsignmentSets= new ArrayList<SpecificConsignmentSet>();
		List<HWRate> hwRateList = new ArrayList<HWRate>();
		
		//Map<String,Integer> conMap = new HashMap<String,Integer>();//形成折扣map 方便查询
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> tariffMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<Long,Double> weightBandMap = new HashMap<Long,Double>();//形成折扣map 方便查询
		Map<Long,String> isHighWeightBandMap = new HashMap<Long,String>();//形成折扣map 方便查询
		Map<Long,Long> productMap = new HashMap<Long,Long>();//形成折扣map 方便查询
		Map<Long,List<Long>> zgMap = new HashMap<Long,List<Long>>();//形成折扣map 方便查询
		Map<String,Double> ratioMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Integer> scsConMap = new HashMap<String,Integer>();//形成折扣map 方便查询
		Map<String,Double> hwRateMap = new HashMap<String,Double>();//形成hwRateMap方便查询
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
			for (JsonData jsonData:jsonDataList) {
				Consignment consignment = new Consignment();
				String name = jsonData.getName();
				String[] discountArr = name.split("_");
				
				consignment.setWeightBandId(Long.valueOf(discountArr[1]));
				consignment.setZoneGroupId(Long.valueOf(discountArr[2]));
				String value = jsonData.getValue();
				consignment.setConsignment(Integer.valueOf(value));
				consignment.setBusinessId(Long.valueOf(discountArr[3]));
				consignment.setPayment(payment);
				consignmentList.add(consignment);
			}
			consignmentService.add(consignmentList);
			/***********先得到zgMap（zonegroupId，List<Long> countryIds） 然后再得到 ratioMap*************/
			//获取zonegroupId 下的 国家id 是特殊国家 那么 zonegroupid下面就是单纯的 特殊国家
			Business bus = businessService.getBusiness(consignmentList.get(0).getBusinessId());
			Customer customer = customerService.getCustomer(bus.getCustomerId());
			//zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(bus.getZoneType());
			/*for (ZoneGroup zg:zoneGroupList) {
				  countryIds = specificCountryService.getCountryIds(zg.getId(),bus.getId());
				  if(countryIds.size() > 0){
					  zgMap.put(zg.getId(), countryIds);
				  }else{
					  //获取zone下面的默认国家
					  countryIds = countryZoneService.getAllCountryByZoneGroup(zg.getId());
					  zgMap.put(zg.getId(), countryIds);
				  }
			}*/
			hwRateList = hwRateService.getAllHWRateByBusId(bus.getId(), payment);
			for (HWRate hwRate : hwRateList) {
				hwRateMap.put(hwRate.getBusinessId()+"_"+hwRate.getWeightBandId()+"_"+hwRate.getCountryId(), hwRate.getRate());
			}
			//获取所有国家的ratio
			countryZones = countryZoneService.getAllCountryZone();
			for (CountryZone countryZone : countryZones) {
				ratioMap.put(countryZone.getZoneGroupId()+"_"+countryZone.getCountryId(), countryZone.getRatio());
			}
			//获取 特殊国家 的 cons
			specificConsignmentSets = specificConsignmentSetService.getAllspecificConsignmentSetByBusId(bus.getId(),payment);
			for (SpecificConsignmentSet scs : specificConsignmentSets) {
				scsConMap.put(bus.getId()+"_"+scs.getWeightBandId()+"_"+scs.getZoneGroupId()+"_"+scs.getCountryId(), scs.getConsignment());
			}
			/***********计算country************/
			
			/*for (Consignment consignment:consignmentList) {
				conMap.put(consignment.getBusinessId()+"_"+consignment.getWeightBandId()+"_"+consignment.getZoneGroupId(), consignment.getConsignment());
			}*/
			discountList = discountService.getAllDiscountByBusId(consignmentList.get(0).getBusinessId(),payment);
			for (Discount discount:discountList) {
				discountMap.put(discount.getBusinessId()+"_"+discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
			}
			tariffList = tariffService.getAllTariff();
			for (Tariff tariff:tariffList) {
				tariffMap.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
			}
			wpList = weightBandService.getAllWeightBand();
			for (WeightBand wp:wpList) {
				weightBandMap.put(wp.getId(), wp.getChargeableWeight());
				productMap.put(wp.getId(), wp.getProductId());
				isHighWeightBandMap.put(wp.getId(), wp.getIsHighWeight());
			}
			
			Double fsi = fsiService.getFsi(customer.getAccount());
			for (Consignment con:consignmentList) {
				//计算chargeableweightband，根据这个值 去匹配tariffGroup的单个值
				WeightBand wb = weightBandService.getWeightBand(con.getWeightBandId());
				//List<TariffGroup> tariffGroupList = new ArrayList<TariffGroup>(); 
				Double chargeWeightBand = wb.getChargeableWeight();//
				TariffGroup tariffGroup  = new TariffGroup();
				//根据weightband 以及chargeweightband的值，假如<=20 则根据 weight，weightBandId，type 三个确定一个对象
				if(chargeWeightBand <= 20){
					tariffGroup = tariffGroupService.getTariffGroupByWeightAndWbIdAndType(chargeWeightBand,wb.getId(),payment);
				}else{
					tariffGroup = tariffGroupService.getTariffGroupByWbIdAndType(wb.getId(),payment);
				}
				//根据tariffkey 获取 tariff值
				String tariffkey = tariffGroup.getId()+"_"+con.getZoneGroupId();
				String key = con.getBusinessId()+"_"+con.getWeightBandId()+"_"+con.getZoneGroupId();
				String hwRatekey = con.getBusinessId()+"_"+con.getWeightBandId();
				
				/**
				 * 一个zone以及一个产品 设置的特殊国家 不能这么简单的取出来
				 * 首先定位 cons 根据 rev.getBusinessId()+"_"+rev.getWeightBandId()+"_"+rev.getZoneGroupId();
				 * 然后得到 每个 cons 下面的特殊国家，如果有 country 从而得出每个country 的rev
				 * 如果没有 就根据 zonetype下的defaultcountry 以及ratio得到每个国家的rev
				 */
/*				if(con.getZoneGroupId()==1){
					System.out.println(11);
				}
*/				countryIds = specificConsignmentSetService.getCountrysInSpec(con.getBusinessId(),con.getWeightBandId(),con.getZoneGroupId(),payment);
				if(countryIds.size()>0){
					for(Long id : countryIds){
						Rev rev = new Rev();
						Double value = 0.0;
						rev.setBusinessId(con.getBusinessId());
						rev.setKilo(weightBandMap.get(con.getWeightBandId())*scsConMap.get(key+"_"+id)); //con.getConsignment()
						
						if(hwRateMap.get(hwRatekey+"_"+id) != null){
							//针对country的重货的rev
							value = hwRateMap.get(hwRatekey+"_"+id)*chargeWeightBand*scsConMap.get(key+"_"+id)*(1+fsi);
						}else{
							//假如是重货.
							if("0".equals(isHighWeightBandMap.get(con.getWeightBandId()))){
								value = DoubleUtil.get2Double(tariffMap.get(tariffkey)*(1-discountMap.get(key)*0.01)*chargeWeightBand*scsConMap.get(key+"_"+id)*(1+fsi));//full tariff *（1－dis％off）*CONS*chargeWeightBand*（1+fsi%）   --con.getConsignment()*
							}else{
								value = DoubleUtil.get2Double(tariffMap.get(tariffkey)*(1-discountMap.get(key)*0.01)*scsConMap.get(key+"_"+id)*(1+fsi));//tariff*(1-discount)*con*（1+fsi%） --con.getConsignment()*
							}
						}
						
						rev.setRev(value);
						rev.setWeightBandId(con.getWeightBandId());
						rev.setZoneGroupId(con.getZoneGroupId());
						rev.setCons(scsConMap.get(key+"_"+id).doubleValue()); //con.getConsignment().doubleValue()
						rev.setProductId(productMap.get(con.getWeightBandId()));
						rev.setCountryId(id);
						rev.setPayment(payment);
						revList.add(rev);
					}
					
				}else{
					//如果没有设置特殊国家，就根据默认国家的ratio来获取
					defaultCountryIds = countryZoneService.getAllCountryByZoneGroup(con.getZoneGroupId());
					Double fullPercent = 1.00;
					for (int i=0,len=defaultCountryIds.size();i<len;i++) {
						Rev rev = new Rev();
						Double value = 0.0;
						Long id = defaultCountryIds.get(i);
						Double ratio  = ratioMap.get(con.getZoneGroupId()+"_"+id)*0.01;
						Integer cons = con.getConsignment();
						if(i < len-1){
							rev.setBusinessId(con.getBusinessId());
							rev.setCountryId(id);
							rev.setKilo(weightBandMap.get(con.getWeightBandId())*cons*ratio);
							if(hwRateMap.get(hwRatekey+"_"+id) != null){
								//针对country的重货的rev  hwrate
								value = hwRateMap.get(hwRatekey+"_"+id)*chargeWeightBand*cons*ratio*(1+fsi);
							}else{
								//假如是在weightband标记为重货（0）.
								if("0".equals(isHighWeightBandMap.get(con.getWeightBandId()))){
									value = DoubleUtil.get2Double(tariffMap.get(tariffkey)*(1-discountMap.get(key)*0.01)*chargeWeightBand*cons*ratio*(1+fsi));//full tariff *（1－dis％off）*CONS*chargeWeightBand*（1+fsi%）   --con.getConsignment()*
								}else{
									value = DoubleUtil.get2Double(tariffMap.get(tariffkey)*(1-discountMap.get(key)*0.01)*cons*ratio*(1+fsi));//tariff*(1-discount)*con*（1+fsi%） --con.getConsignment()*
								}
							}
							rev.setRev(value);
							rev.setCons(DoubleUtil.get2Double(cons*ratio));
							rev.setWeightBandId(con.getWeightBandId());
							rev.setZoneGroupId(con.getZoneGroupId());
							rev.setProductId(productMap.get(con.getWeightBandId()));
							rev.setPayment(payment);
							revList.add(rev);
						}else{
							/**
							 * fullpercent =1-ratio1-ratio2-ratio3..
							 */
							rev.setBusinessId(con.getBusinessId());
							rev.setCountryId(id);
							rev.setKilo(weightBandMap.get(con.getWeightBandId())*cons*fullPercent);
							
							if(hwRateMap.get(hwRatekey+"_"+id) != null){
								//针对country的重货的rev  hwrate
								value = hwRateMap.get(hwRatekey+"_"+id)*chargeWeightBand*cons*fullPercent*(1+fsi);
							}else{
								//假如是在weightband标记为重货（0）.
								if("0".equals(isHighWeightBandMap.get(con.getWeightBandId()))){
									value = DoubleUtil.get2Double(tariffMap.get(tariffkey)*(1-discountMap.get(key)*0.01)*chargeWeightBand*cons*fullPercent*(1+fsi));//full tariff *（1－dis％off）*CONS*chargeWeightBand*（1+fsi%）   --con.getConsignment()*
								}else{
									value = DoubleUtil.get2Double(tariffMap.get(tariffkey)*(1-discountMap.get(key)*0.01)*cons*fullPercent*(1+fsi));//tariff*(1-discount)*con*（1+fsi%） --con.getConsignment()*
								}
							}
							rev.setRev(value);
							rev.setCons(DoubleUtil.get2Double(cons*fullPercent));
							rev.setWeightBandId(con.getWeightBandId());
							rev.setZoneGroupId(con.getZoneGroupId());
							rev.setProductId(productMap.get(con.getWeightBandId()));
							rev.setPayment(payment);
							revList.add(rev);
						}
						fullPercent = fullPercent - ratio;
					}
				}
				
			}
			
			/*for (Rev rev : revList) {
				Rev revNew = new Rev();
				String key = rev.getBusinessId()+"_"+rev.getWeightBandId()+"_"+rev.getZoneGroupId();
				//-------------以下代码是错误  --------------//
				*//**
				 * 一个zone以及一个产品 设置的特殊国家 不能这么简单的取出来
				 * 首先定位 cons 根据 rev.getBusinessId()+"_"+rev.getWeightBandId()+"_"+rev.getZoneGroupId();
				 * 然后得到 每个 cons 下面的特殊国家，如果有 country 从而得出每个country 的rev
				 * 如果没有 就根据 zonetype下的defaultcountry 以及ratio得到每个国家的rev
				 *//*
				List<Long> countrys = zgMap.get(rev.getZoneGroupId());
				if(countrys.size()>0){
					Double fullPercent = 1.00;
					for (int i=0,len=countrys.size();i<len;i++) {
						Long id = countrys.get(i);
						Double ratio  = ratioMap.get(rev.getZoneGroupId()+"_"+id)*0.01;
						Double cons = scsConMap.get(key+"_"+id)==null?rev.getCons():scsConMap.get(key+"_"+id).doubleValue();
						if(i < len-1){
							revNew.setBusinessId(rev.getBusinessId());
							revNew.setCountryId(id);
							revNew.setKilo(DoubleUtil.get2Double(rev.getKilo()));
							revNew.setRev(DoubleUtil.get2Double(rev.getRev()*ratio));
							revNew.setCons(DoubleUtil.get2Double(cons*ratio));
							revNew.setWeightBandId(rev.getWeightBandId());
							revNew.setZoneGroupId(rev.getZoneGroupId());
							revNew.setProductId(rev.getProductId());
							revNew.setPayment(payment);
						}else{
							revNew.setBusinessId(rev.getBusinessId());
							revNew.setCountryId(id);
							revNew.setKilo(DoubleUtil.get2Double(rev.getKilo()));
							revNew.setRev(DoubleUtil.get2Double(rev.getRev()*fullPercent));
							revNew.setCons(DoubleUtil.get2Double(cons*fullPercent));//假如是最后一次  那么 比例为（ 1-前面所有的ratio）
							revNew.setWeightBandId(rev.getWeightBandId());
							revNew.setZoneGroupId(rev.getZoneGroupId());
							revNew.setProductId(rev.getProductId());
							revNew.setPayment(payment);
						}
						fullPercent = fullPercent - ratio;
					}
				}else{
					revNew = rev;
					revNew.setPayment(payment);
				}
				revNewList.add(revNew);
			}*/
			
			revService.add(revList);
			
			Double totalRev = 0.0;
			for (Rev rev:revList) {
				totalRev+=rev.getRev();
			}
			msg = DoubleUtil.get2Double(totalRev)+"";
		} catch (ParseException e) {
			msg = "parseException";
		}
		return msg;
	}
	
	/**
	 * 保存zonetype，GEOZONE，country计算的值，更新totalRev
	 * @param jsonDatas
	 * @return
	 */
	@RequestMapping(value="addSummary/{payment}", method = {RequestMethod.GET })
	@ResponseBody 
	public String addSummary(HttpServletRequest request,@PathVariable("payment") String payment) {
		String msg = "";
		List<String> geoList = new ArrayList<String>();
		List<Long> countryIds = new ArrayList<Long>();
		List<GEOSummary> geoSummaryList = new ArrayList<GEOSummary>();
		
		/**
		 * 从页面获取数据
		 */
		String businessId = request.getParameter("businessId");
		String totalRev = request.getParameter("totalRev");
		String zonetype = request.getParameter("zonetype");
		if("".equals(businessId)||"".equals(totalRev)||"".equals(zonetype)){
			msg = "system is get some errors ，pls check again";
			return msg;
		}
		Long busId = Long.valueOf(businessId);
		
		//修改totalRev
		if(payment.equals(PTPARAMETERS.PAYMENT[1])){
			businessService.updateTotalRev_R(Double.valueOf(totalRev),busId);
		}else if(payment.equals(PTPARAMETERS.PAYMENT[0])){
			businessService.updateTotalRev_S(Double.valueOf(totalRev),busId);
		}else if(payment.equals(PTPARAMETERS.PAYMENT[2])){
			businessService.updateTotalRev(Double.valueOf(totalRev),busId);
		}
		
		
		geoList =  countryZoneService.getAllGEO();//得到所有的geo
		for (String  geo : geoList ) {
			if(geo==null||"".equals(geo)) continue;
			GEOSummary geoSummary =  new GEOSummary();
			List<Rev> revs = new ArrayList<Rev>();
			countryIds = countryZoneService.getAllCountryByGeo(geo);
			for (Long countryId:countryIds) {
				Rev rev = new Rev();
				rev = revService.getCountryGroupBy(busId,countryId,payment);
				if (rev!=null) revs.add(rev);
			}
				
			Double consM = DoubleUtil.get2Double(geo_consSum(revs));
			Double consY = DoubleUtil.get2Double(consM*12);
			Double kiloM = DoubleUtil.get2Double(geo_kiloSum(revs));
			Double kiloY = DoubleUtil.get2Double(kiloM*12);
			Double revM = DoubleUtil.get2Double(geo_revSum(revs));
			Double revY = DoubleUtil.get2Double(revM*12);
			
			geoSummary.setBusinessId(busId);
			geoSummary.setConsM(consM);
			geoSummary.setConsY(consY);
			geoSummary.setKiloM(kiloM);
			geoSummary.setKiloY(kiloY);
			geoSummary.setGeoZone(geo);
			geoSummary.setRevM(revM);
			geoSummary.setRevY(revY);
			geoSummary.setPayment(payment);
			geoSummaryList.add(geoSummary);
		}
		if(geoSummaryList.size()>0)	geoSummaryService.batchInsert(geoSummaryList);
		return msg;
	}
	
	/**
	 * summaryInfo 详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="summaryInfo", method = RequestMethod.POST)
	public String summaryInfo(Model model,@ModelAttribute BusCusVO busCus) {
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		String payment = "";
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment = "both";
		}else{
			payment = customer.getPayment();
		}
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		List<GEOSummary> geoSummaryList = new ArrayList<GEOSummary>();
		List<ZoneSummary> zoneSummaryList = new ArrayList<ZoneSummary>();
		List<RevVO> revList = new ArrayList<RevVO>();
		
		List<Discount> discountList = new ArrayList<Discount>();
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		
		
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		geoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),payment);
		String[] groupBy = {"zoneGroupId"};
		revList = revService.getGroupBy_(business.getId(),groupBy,payment);
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
		RevVO revVO = revService.getGroupBy_(business.getId(),groupBy_1,payment).get(0);
		ZoneSummary zoneSummary = new ZoneSummary();
		zoneSummary.setConsM(revVO.getCons());
		zoneSummary.setConsY(DoubleUtil.get2Double(revVO.getCons()*12));
		zoneSummary.setKiloM(revVO.getKilo());
		zoneSummary.setKiloY(DoubleUtil.get2Double(revVO.getKilo()*12));
		zoneSummary.setRevM(revVO.getRev());
		zoneSummary.setRevY(DoubleUtil.get2Double(revVO.getRev()*12));
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("geoSummaryList", geoSummaryList);
		model.addAttribute("zoneSummaryList", zoneSummaryList);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("eonomyList", eonomyList);
		model.addAttribute("zoneSummary", zoneSummary);
		
		discountList = discountService.getAllDiscountByBusId(business.getId(),payment);
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		model.addAttribute("discountMap", discountMap);
		
		model.addAttribute("isFollow", busCus.getIsFollow());
		model.addAttribute("payment",payment);
		return "newPT/summaryInfo";
	}
	
	
	/**
	 * 完成pt的保存动作
	 * @param jsonDatas
	 * @return
	 */
	@RequestMapping(value="finishPT", method = {RequestMethod.GET })
	@ResponseBody 
	public String finishPT(HttpServletRequest request) {
		String msg = "";
		/**
		 * 从页面获取数据
		 */
		String businessId = request.getParameter("businessId");
		if("".equals(businessId)){
			msg = "businessId is empty ，pls check again";
			return msg;
		}else{
			msg = "this pt save success!";
		}
		Long busId = Long.valueOf(businessId);
		Business business = businessService.getBusiness(busId);
		/**
		 * 只有状态为creating 状态才会修改为created，其他的不变
		 */
		if("creating".equals(business.getState())){
			business.setState("created");
		}
		business.setId(busId);
		//修改state
		businessService.updateState(business);
		return msg;
	}
	/**
	 * 计算cons
	 * @param consignmentList
	 * @return
	 *//*
	private Integer consSum(List<Consignment> consignmentList){
		Integer sum = 0;
		for (Consignment consignment : consignmentList) {
			sum += consignment.getConsignment();
		}
		return sum;
	}
	
	*//**
	 * 计算cons
	 * @param consignmentList
	 * @return
	 *//*
	private Double kiloSum(List<Consignment> consignmentList){
		Double sum = 0.0;
		for (Consignment consignment : consignmentList) {
			WeightBand wb = weightBandService.getWeightBand(consignment.getWeightBandId());
			sum += consignment.getConsignment()*wb.getChargeableWeight();
		}
		return sum;
	}*/
	
	/**
	 * 计算geo_cons
	 * @param consignmentList
	 * @return
	 */
	private Double geo_consSum(List<Rev> revs){
		Double sum = 0.0;
		for (Rev rev : revs) {
			sum += rev.getCons();
		}
		return sum;
	}
	
	/**
	 * 计算geo_kilos
	 * @param consignmentList
	 * @return
	 */
	private Double geo_kiloSum(List<Rev> revs){
		Double sum = 0.0;
		for (Rev rev : revs) {
			sum += rev.getKilo();
		}
		return sum;
	}
	
	/**
	 * 计算geo_rev 收入
	 * @param consignmentList
	 * @return
	 */
	private Double geo_revSum(List<Rev> revs){
		Double sum = 0.0;
		for (Rev rev : revs) {
			sum+=rev.getRev();
		}
		return sum;
	}
	
	/**
	 * 公斤_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="rateDetail/{payment}/{businessId}", method = RequestMethod.GET)
	public String rateDetail(Model model,@PathVariable("payment") String payment,@PathVariable("businessId") Long businessId) {
		/**
		 * 该处为保存该pt下折扣信息代码
		 */
		//List<WeightBand> weightBandList = new ArrayList<WeightBand>();
		List<Rate> rateList = new ArrayList<Rate>();
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> economyList = new ArrayList<WeightBand>();
		List<TariffGroup> documentTGList = new ArrayList<TariffGroup>();
		List<TariffGroup> ndocumentTGList = new ArrayList<TariffGroup>();
		List<TariffGroup> economyTGList = new ArrayList<TariffGroup>();
		
		List<Discount> discountList = new ArrayList<Discount>();
		List<Tariff> tariffList = new ArrayList<Tariff>();
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> tariffMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> rateMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		
		business = businessService.getBusiness(businessId);//1L为保存后获得的PT业务主表id
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		customer = customerService.getCustomer(business.getCustomerId());//客户信息
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		economyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		discountList = discountService.getAllDiscountByBusId(business.getId(),payment);
//------------这边可以优化(可以取出相应的数据)	
		tariffList = tariffService.getAllTariff();
//------------这边可以优化		
		
		
		//Long businessId = business.getId();
		for (Discount discount :discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		for (Tariff tariff :tariffList) {
			tariffMap.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
		}
		
		
		for (WeightBand weightBand :documentList) {
			for (ZoneGroup zoneGroup :zoneGroupList) {
				List<TariffGroup> tGroupList = new ArrayList<TariffGroup>();
				tGroupList = tariffGroupService.getAllTariffGroup(weightBand.getProductId(), payment);
				for(TariffGroup tfg : tGroupList){
					Rate rate =  new Rate();
					Long weightBandId = weightBand.getId();
					Long zoneGroupId = zoneGroup.getId();
					rate.setBusinessId(businessId);
					//rate = discount * tariff
				/*	Double chargeWeightBand = weightBand.getChargeableWeight();//
					TariffGroup tariffGroup  = new TariffGroup();
					//根据weightband 以及chargeweightband的值，假如<=20 则根据 weight，weightBandId，type 三个确定一个对象
					if(chargeWeightBand <= 20){
						tariffGroup = tariffGroupService.getTariffGroupByWeightAndWbIdAndType(chargeWeightBand,weightBand.getId(),payment);
					}else{
						tariffGroup = tariffGroupService.getTariffGroupByWbIdAndType(weightBand.getId(),payment);
					}*/
					
					rate.setRate(discountMap.get(weightBandId+"_"+zoneGroupId)*tariffMap.get(tfg.getId()+"_"+zoneGroupId));
					rate.setTariffGroupId(tfg.getId());
					rate.setZoneGroupId(zoneGroup.getId());
					rateList.add(rate);
					documentTGList.add(tfg);
				}
				
			}
		}
		for (WeightBand weightBand :ndocumentList) {
			for (ZoneGroup zoneGroup :zoneGroupList) {
				List<TariffGroup> tGroupList = new ArrayList<TariffGroup>();
				tGroupList = tariffGroupService.getAllTariffGroup(weightBand.getProductId(), payment);
				for(TariffGroup tfg : tGroupList){
					Rate rate =  new Rate();
					Long weightBandId = weightBand.getId();
					Long zoneGroupId = zoneGroup.getId();
					rate.setBusinessId(businessId);
					//rate = discount * tariff
				/*	Double chargeWeightBand = weightBand.getChargeableWeight();//
					TariffGroup tariffGroup  = new TariffGroup();
					//根据weightband 以及chargeweightband的值，假如<=20 则根据 weight，weightBandId，type 三个确定一个对象
					if(chargeWeightBand <= 20){
						tariffGroup = tariffGroupService.getTariffGroupByWeightAndWbIdAndType(chargeWeightBand,weightBand.getId(),payment);
					}else{
						tariffGroup = tariffGroupService.getTariffGroupByWbIdAndType(weightBand.getId(),payment);
					}*/
					
					rate.setRate(discountMap.get(weightBandId+"_"+zoneGroupId)*tariffMap.get(tfg.getId()+"_"+zoneGroupId));
					rate.setTariffGroupId(tfg.getId());
					rate.setZoneGroupId(zoneGroup.getId());
					rateList.add(rate);
					ndocumentTGList.add(tfg);
				}
			}
		}
		for (WeightBand weightBand :economyList) {
			for (ZoneGroup zoneGroup :zoneGroupList) {
				List<TariffGroup> tGroupList = new ArrayList<TariffGroup>();
				tGroupList = tariffGroupService.getAllTariffGroup(weightBand.getProductId(), payment);
				for(TariffGroup tfg : tGroupList){
					Rate rate =  new Rate();
					Long weightBandId = weightBand.getId();
					Long zoneGroupId = zoneGroup.getId();
					rate.setBusinessId(businessId);
					//rate = discount * tariff
				/*	Double chargeWeightBand = weightBand.getChargeableWeight();//
					TariffGroup tariffGroup  = new TariffGroup();
					//根据weightband 以及chargeweightband的值，假如<=20 则根据 weight，weightBandId，type 三个确定一个对象
					if(chargeWeightBand <= 20){
						tariffGroup = tariffGroupService.getTariffGroupByWeightAndWbIdAndType(chargeWeightBand,weightBand.getId(),payment);
					}else{
						tariffGroup = tariffGroupService.getTariffGroupByWbIdAndType(weightBand.getId(),payment);
					}*/
					
					rate.setRate(discountMap.get(weightBandId+"_"+zoneGroupId)*tariffMap.get(tfg.getId()+"_"+zoneGroupId));
					rate.setTariffGroupId(tfg.getId());
					rate.setZoneGroupId(zoneGroup.getId());
					rateList.add(rate);
					economyTGList.add(tfg);
				}
			}
		}
		
		for (Rate rate:rateList) {
			rateService.save(rate);
			rateMap.put(rate.getTariffGroupId()+"_"+rate.getZoneGroupId(), rate.getRate());
		}
		
		rateList = rateService.getAllRateByBusId(businessId,payment);
		for (Rate rate:rateList) {
			rateMap.put(rate.getTariffGroupId()+"_"+rate.getZoneGroupId(), rate.getRate());
		}
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("zoneType", zoneType);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentTGList", documentTGList);
		model.addAttribute("ndocumentTGList", ndocumentTGList);
		model.addAttribute("economyTGList", economyTGList);
		model.addAttribute("rateMap", rateMap);
		
		return "ptProcess/rateDetail";
	}
	
	
	


	public static void main(String[] args) {
		System.out.println(new Date(System.currentTimeMillis()));
	}
}
