package org.tnt.pt.web.ptProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Consignment;
import org.tnt.pt.entity.Country;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Discount;
import org.tnt.pt.entity.DiscountDefault;
import org.tnt.pt.entity.GEOSummary;
import org.tnt.pt.entity.HWRate;
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneSummary;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.service.baseInfo.CountryGeoService;
import org.tnt.pt.service.baseInfo.CountryService;
import org.tnt.pt.service.baseInfo.CountryZoneService;
import org.tnt.pt.service.baseInfo.DiscountDefaultService;
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
import org.tnt.pt.service.ptProcess.HWRateService;
import org.tnt.pt.service.ptProcess.RateService;
import org.tnt.pt.service.ptProcess.RevService;
import org.tnt.pt.service.ptProcess.SpecificConsignmentSetService;
import org.tnt.pt.service.ptProcess.SpecificCountryService;
import org.tnt.pt.service.ptProcess.ZoneSummaryService;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BusCusVO;
import org.tnt.pt.vo.RevVO;
import org.tnt.pt.web.ptProcess.PTCreateController.ComparatorWeightBand;

/**
 * ProductController负责产品的请求，
 * 
 * @author yuanchen
 */
@Controller
@RequestMapping(value = "/ptModify")
public class PTModifyController {
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
    
	@RequestMapping(value="updateCustomer/{businessId}", method = RequestMethod.GET)
	public String updateCustomer(Model model,@PathVariable("businessId") Long businessId) {
		Business bus = new Business();
		Customer cus = new Customer();
		
		bus = businessService.getBusiness(businessId);
		cus = customerService.getCustomer(bus.getCustomerId());
		
		model.addAttribute("business",bus);
		model.addAttribute("customer",cus);
		
		return "newPT/updatePTCustomer";
	}
	
	
	/**
	 * 产品_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="disConfirm", method = RequestMethod.POST)
	public String disConfirm(Model model,@ModelAttribute BusCusVO busCus) {
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		String payment = "";
		//如果是yes 并且 payment 为空 择进入SenderPay页面
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment="both";
		}else{
			payment=busCus.getCustomer().getPayment();
		}
		
		
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
		
		model.addAttribute("business", business);
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
		//如果是yes 并且 payment 为空 择进入SenderPay页面
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment="both";
		}else{
			payment=busCus.getCustomer().getPayment();
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
		//如果是yes 并且 payment 为空 择进入SenderPay页面
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment="both";
		}else{
			payment=customer.getPayment();
		}
		
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		List<Country> ndocountrys = new ArrayList<Country>();
		List<Country> ecocountrys = new ArrayList<Country>();
		String ndocumentIds = new String();
		String eonomyIds = new String();
		List<HWRate> hwRateList = new ArrayList<HWRate>();
		Map<String,Double> hwRateMap = new HashMap<String,Double>();//形成折扣map 方便查询
		
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
	
	/**
	 * 公斤_时区_consignment 详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="consProfile", method = RequestMethod.POST)
	public String consProfile(Model model,@ModelAttribute BusCusVO busCus) {
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		String payment = "";
		//如果是yes 并且 payment 为空 择进入SenderPay页面
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment=PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment="both";
		}else{
			payment=customer.getPayment();
		}
		
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		
		List<Consignment>  consignmentList = new ArrayList<Consignment>();//con数量集合 批量插入
		Map<String,Integer> consignmentMap = new HashMap<String,Integer>();//形成折扣map 方便查询
		

		
		
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
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
		return "newPT/consignmentProfile";
	}
	
	/**
	 * summaryInfo 详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="summaryInfo", method = RequestMethod.POST)
	public String summaryInfo(Model model,@ModelAttribute BusCusVO busCus) {
		String payment = "";
		if("NO".equals(busCus.getIsFollow())&&"".equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[0];
		//如果是yes 并且 payment 为SenderPay 择进入ReceivePay页面
		}else if("NO".equals(busCus.getIsFollow())&&PTPARAMETERS.PAYMENT[0].equals(busCus.getPayment())){
			payment = PTPARAMETERS.PAYMENT[1];
		}else if("YES".equals(busCus.getIsFollow())){
			payment = "both";
		}else{
			payment = busCus.getCustomer().getPayment();
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
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		
		
		business = businessService.getBusiness(busCus.getBusiness().getId());
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		geoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),payment);
		String[] groupBy = {"zoneGroupId"};
		revList = revService.getGroupBy(business.getId(),groupBy,payment);
		for (RevVO rev : revList) {
			ZoneSummary zs = new ZoneSummary();
			zs.setConsM(rev.getCons());
			zs.setConsY(rev.getCons()*12);
			zs.setKiloM(rev.getKilo());
			zs.setKiloY(rev.getKilo()*12);
			zs.setRevM(rev.getRev());
			zs.setRevY(rev.getRev()*12);
			zs.setZoneType(rev.getZone());
			zoneSummaryList.add(zs);
		}
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("geoSummaryList", geoSummaryList);
		model.addAttribute("zoneSummaryList", zoneSummaryList);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("eonomyList", eonomyList);
		
		discountList = discountService.getAllDiscountByBusId(business.getId(),payment);
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		model.addAttribute("discountMap", discountMap);
		
		model.addAttribute("isFollow", busCus.getIsFollow());
		model.addAttribute("payment",payment);
		return "newPT/summaryInfo";
	}
	
}
