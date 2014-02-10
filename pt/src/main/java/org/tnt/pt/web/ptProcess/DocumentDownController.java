package org.tnt.pt.web.ptProcess;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Discount;
import org.tnt.pt.entity.DiscountDefault;
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.entity.TariffGroup;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.service.baseInfo.CountryGeoService;
import org.tnt.pt.service.baseInfo.CountryZoneService;
import org.tnt.pt.service.baseInfo.DiscountDefaultService;
import org.tnt.pt.service.baseInfo.ProductService;
import org.tnt.pt.service.baseInfo.TariffGroupService;
import org.tnt.pt.service.baseInfo.TariffService;
import org.tnt.pt.service.baseInfo.WeightBandService;
import org.tnt.pt.service.baseInfo.ZoneGroupService;
import org.tnt.pt.service.baseInfo.ZoneTypeService;
import org.tnt.pt.service.downPDF.PDFGenerater;
import org.tnt.pt.service.ptProcess.BusinessService;
import org.tnt.pt.service.ptProcess.ConsignmentService;
import org.tnt.pt.service.ptProcess.CustomerService;
import org.tnt.pt.service.ptProcess.DiscountService;
import org.tnt.pt.service.ptProcess.ExamService;
import org.tnt.pt.service.ptProcess.HWRateService;
import org.tnt.pt.service.ptProcess.RateService;
import org.tnt.pt.service.ptProcess.SpecificConsignmentSetService;
import org.tnt.pt.service.ptProcess.SpecificCountryService;
import org.tnt.pt.service.ptProcess.ZoneSummaryService;
import org.tnt.pt.util.FileUtil;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.HWRateVO;

@Controller
@RequestMapping(value = "/documentDown")
public class DocumentDownController {

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
    ExamService examService;
    @Autowired
   	SpecificCountryService specificCountryService;
    @Autowired
   	SpecificConsignmentSetService specificConsignmentSetService;
    @Autowired
    TariffGroupService tariffGroupService;
    @Autowired
    ZoneSummaryService zoneSummaryService;
    @Autowired
    CountryGeoService countryGeoService;
    @Autowired
    CountryZoneService countryZoneService;
    @Autowired
    HWRateService hwRateService;
    
	
 //pdf价卡导出
 @RequestMapping(value="downDocument/{id}")
 public String downDocument1(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") Long businessId,
		 Model model,@ModelAttribute BusinessVO businessVO) {
	//生成对应pdf价卡
	String logoPath = request.getSession().getServletContext().getRealPath("/static/images/logoCard.jpg");
	String oilFee = request.getSession().getServletContext().getRealPath("/static/images/base.jpg");
	String zoneImage1 = request.getSession().getServletContext().getRealPath("/static/images/zoneDetail.jpg");
	String zoneImage2 = request.getSession().getServletContext().getRealPath("/static/images/zoneDetail2.jpg");
	String zoneImage3 = request.getSession().getServletContext().getRealPath("/static/images/zoneDetail3.jpg");
	String zoneImage4 = request.getSession().getServletContext().getRealPath("/static/images/zoneDetail4.jpg");
	String zoneImage5 = request.getSession().getServletContext().getRealPath("/static/images/zoneDetail5.jpg");
	String zoneImage6 = request.getSession().getServletContext().getRealPath("/static/images/serverAdd.jpg");
	String pdfPathString = request.getSession().getServletContext().getRealPath("/attached/temp/");
	List<Product> productList = new ArrayList<Product>();
	List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
	/**
	 * 假如zonetype不为空，则默认初始加载的为第一个zonetype
	 */
	zoneTypeList =  zoneTypeService.getAllZoneType();
	if(zoneTypeList.size()>0){
		ZoneType zoneType = zoneTypeList.get(0);
		productList.add(productService.getProduct(zoneType.getDocument()));
		productList.add(productService.getProduct(zoneType.getNonDocument()));
		productList.add(productService.getProduct(zoneType.getEconomy()));
	}
	Map<String,Double> traiffMapSP = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> traiffMapRP = new HashMap<String,Double>();//形成折扣map 方便查询
	
	Map<String,Double> HWtraiffMapSP = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> HWtraiffMapRP = new HashMap<String,Double>();//形成折扣map 方便查询
	
	List<HWRateVO> HWList1 = new ArrayList<HWRateVO>();
	List<HWRateVO> HWList2 = new ArrayList<HWRateVO>();
	List<HWRateVO> HWList3 = new ArrayList<HWRateVO>();
	List<HWRateVO> HWList4 = new ArrayList<HWRateVO>();
	
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	List<String> list3 = new ArrayList<String>();
	List<String> list4 = new ArrayList<String>();
	
	List<Tariff> documentListSP = new ArrayList<Tariff>();
	List<Tariff> ndocumentListSP = new ArrayList<Tariff>();
	List<Tariff> eonomyListSP = new ArrayList<Tariff>();
	List<Tariff> documentListRP = new ArrayList<Tariff>();
	List<Tariff> ndocumentListRP = new ArrayList<Tariff>();
	List<Tariff> eonomyListRP = new ArrayList<Tariff>();
	
	List<TariffGroup> GdocumentListSP = new ArrayList<TariffGroup>();
	List<TariffGroup> GndocumentListSP = new ArrayList<TariffGroup>();
	List<TariffGroup> GeonomyListSP = new ArrayList<TariffGroup>();
	List<TariffGroup> GdocumentListRP = new ArrayList<TariffGroup>();
	List<TariffGroup> GndocumentListRP = new ArrayList<TariffGroup>();
	List<TariffGroup> GeonomyListRP = new ArrayList<TariffGroup>();
	
	List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
	ZoneType zoneType = new ZoneType();
	Customer customer = new Customer();
	Business business = businessService.getBusiness(businessId);
	customer = customerService.getCustomer(business.getCustomerId());
	zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
	zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
	
	documentListSP = examService.getTariff(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getDocument());
	ndocumentListSP = examService.getTariff(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getNonDocument());
	eonomyListSP = examService.getTariff(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getEconomy());
	documentListRP = examService.getTariff(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getDocument());
	ndocumentListRP = examService.getTariff(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getNonDocument());
	eonomyListRP = examService.getTariff(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getEconomy());
	
	GdocumentListSP = tariffGroupService.getAllTariffGroup(zoneType.getDocument(),PTPARAMETERS.PAYMENT[0]);
	GndocumentListSP = tariffGroupService.getAllTariffGroup(zoneType.getNonDocument(),PTPARAMETERS.PAYMENT[0]);
	GeonomyListSP = tariffGroupService.getAllTariffGroup(zoneType.getEconomy(),PTPARAMETERS.PAYMENT[0]);
	GdocumentListRP = tariffGroupService.getAllTariffGroup(zoneType.getDocument(),PTPARAMETERS.PAYMENT[1]);
	GndocumentListRP = tariffGroupService.getAllTariffGroup(zoneType.getNonDocument(),PTPARAMETERS.PAYMENT[1]);
	GeonomyListRP = tariffGroupService.getAllTariffGroup(zoneType.getEconomy(),PTPARAMETERS.PAYMENT[1]);
	for (Tariff tariff:documentListSP) {
		traiffMapSP.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	for (Tariff tariff:ndocumentListSP) {
		traiffMapSP.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	for (Tariff tariff:eonomyListSP) {
		traiffMapSP.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	for (Tariff tariff:documentListRP) {
		traiffMapRP.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	for (Tariff tariff:ndocumentListRP) {
		traiffMapRP.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	for (Tariff tariff:eonomyListRP) {
		traiffMapRP.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	//特价重货记录
	HWList1 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getNonDocument());// SP 
	for(HWRateVO hwRateVO:HWList1){
		list1.add(hwRateVO.getCountryName());
		HWtraiffMapSP.put(hwRateVO.getCountryName()+"2_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	//特价重货记录
	HWList2 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getEconomy());// SP 
	for(HWRateVO hwRateVO:HWList2){
		list2.add(hwRateVO.getCountryName());
		HWtraiffMapSP.put(hwRateVO.getCountryName()+"3_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	//特价重货记录
	HWList3 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getNonDocument());// SP 
	for(HWRateVO hwRateVO:HWList3){
		list3.add(hwRateVO.getCountryName());
		HWtraiffMapRP.put(hwRateVO.getCountryName()+"2_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	//特价重货记录
	HWList4 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getEconomy());// SP 
	for(HWRateVO hwRateVO:HWList4){
		list4.add(hwRateVO.getCountryName());
		HWtraiffMapRP.put(hwRateVO.getCountryName()+"3_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	list1 = removeDuplicateWithOrder(list1);
	list2 = removeDuplicateWithOrder(list2);
	list3 = removeDuplicateWithOrder(list3);
	list4 = removeDuplicateWithOrder(list4);
	try {
		pdfPathString = pdfPathString+"\\"+business.getApplicationReference()+".pdf";
		FileOutputStream fos = new FileOutputStream(pdfPathString);
//		File file = new File(pdfPathString);
//		file.createNewFile();  
		response.setContentType("application/pdf;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((business.getApplicationReference()+".pdf").getBytes(), "ISO8859-1"));
		OutputStream out = response.getOutputStream();//返回ServletOutputStream
		ByteArrayOutputStream baos = new PDFGenerater().generatePDF(zoneGroupList,productList,traiffMapSP,traiffMapRP,GdocumentListSP,GndocumentListSP,GeonomyListSP,GdocumentListRP,GndocumentListRP,
									GeonomyListRP,business,customer,logoPath,oilFee,zoneImage1,zoneImage2,zoneImage3,zoneImage4,zoneImage5,zoneImage6,pdfPathString,HWtraiffMapSP,HWtraiffMapRP,list1,
									list2,list3,list4);
		response.setContentLength(baos.size());
		baos.writeTo(out);
		out.flush();
        fos.close();
        //下载结束后进行删除动作
        FileUtil.delFile(pdfPathString);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
 }
 
 //财务需要的pdf导出
 @RequestMapping(value="downDocument2/{id}")
 public String downDocument2(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") Long businessId,
		 Model model,@ModelAttribute BusinessVO businessVO) {
	String pdfPathString = request.getSession().getServletContext().getRealPath("/attached/temp/");
	List<Product> productList = new ArrayList<Product>();
	List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
	List<DiscountDefault> discountDefaultList = new ArrayList<DiscountDefault>();
	Map<String,Double> discountDefaultMap = new HashMap<String,Double>();//形成折扣map 方便查询
	zoneTypeList =  zoneTypeService.getAllZoneType();
	if(zoneTypeList.size()>0){
		ZoneType zoneType = zoneTypeList.get(0);
		productList.add(productService.getProduct(zoneType.getDocument()));
		productList.add(productService.getProduct(zoneType.getNonDocument()));
		productList.add(productService.getProduct(zoneType.getEconomy()));
	}
	discountDefaultList = discountdefaultService.getAllDiscountDefault();
	for (DiscountDefault discountDefault:discountDefaultList) {
		discountDefaultMap.put(discountDefault.getProductId()+"_"+discountDefault.getZoneGroupId(), discountDefault.getDiscount());
	}
	Map<String,Double> HWtraiffMapSP = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> HWtraiffMapRP = new HashMap<String,Double>();//形成折扣map 方便查询
	List<TariffGroup> GdocumentListSP = new ArrayList<TariffGroup>();
	List<TariffGroup> GndocumentListSP = new ArrayList<TariffGroup>();
	List<TariffGroup> GeonomyListSP = new ArrayList<TariffGroup>();
	List<TariffGroup> GdocumentListRP = new ArrayList<TariffGroup>();
	List<TariffGroup> GndocumentListRP = new ArrayList<TariffGroup>();
	List<TariffGroup> GeonomyListRP = new ArrayList<TariffGroup>();
	List<HWRateVO> HWList1 = new ArrayList<HWRateVO>();
	List<HWRateVO> HWList2 = new ArrayList<HWRateVO>();
	List<HWRateVO> HWList3 = new ArrayList<HWRateVO>();
	List<HWRateVO> HWList4 = new ArrayList<HWRateVO>();
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	List<String> list3 = new ArrayList<String>();
	List<String> list4 = new ArrayList<String>();
	List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
	List<WeightBand> documentList = new ArrayList<WeightBand>();
	List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
	List<WeightBand> eonomyList = new ArrayList<WeightBand>();
	List<Discount> discountList = new ArrayList<Discount>();
	List<Discount> recDiscountList = new ArrayList<Discount>();
	ZoneType zoneType = new ZoneType();
	Customer customer = new Customer();
	Map<String,Double> recDiscountMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Business business = businessService.getBusiness(businessId);
	customer = customerService.getCustomer(business.getCustomerId());
	zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
	zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
	documentList = weightBandService.findByProductId(zoneType.getDocument());
	ndocumentList = weightBandService.findByProductId(zoneType.getNonDocument());
	eonomyList = weightBandService.findByProductId(zoneType.getEconomy());
	
	GdocumentListSP = tariffGroupService.getAllTariffGroup(zoneType.getDocument(),PTPARAMETERS.PAYMENT[0]);
	GndocumentListSP = tariffGroupService.getAllTariffGroup(zoneType.getNonDocument(),PTPARAMETERS.PAYMENT[0]);
	GeonomyListSP = tariffGroupService.getAllTariffGroup(zoneType.getEconomy(),PTPARAMETERS.PAYMENT[0]);
	GdocumentListRP = tariffGroupService.getAllTariffGroup(zoneType.getDocument(),PTPARAMETERS.PAYMENT[1]);
	GndocumentListRP = tariffGroupService.getAllTariffGroup(zoneType.getNonDocument(),PTPARAMETERS.PAYMENT[1]);
	GeonomyListRP = tariffGroupService.getAllTariffGroup(zoneType.getEconomy(),PTPARAMETERS.PAYMENT[1]);
	if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
		discountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[0]);
		recDiscountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[1]);
		for (Discount discount:recDiscountList) {
			recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
	}else if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
		discountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[2]);//都取both
	}else{
		discountList = discountService.getAllDiscountByBusId(businessId,customer.getPayment());
	}
	for (Discount discount:discountList) {
		discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
	}
	
	
	//特价重货记录
	HWList1 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getNonDocument());// SP 
	for(HWRateVO hwRateVO:HWList1){
		list1.add(hwRateVO.getCountryName());
		HWtraiffMapSP.put(hwRateVO.getCountryName()+"2_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	//特价重货记录
	HWList2 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[0], zoneType.getEconomy());// SP 
	for(HWRateVO hwRateVO:HWList2){
		list2.add(hwRateVO.getCountryName());
		HWtraiffMapSP.put(hwRateVO.getCountryName()+"3_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	//特价重货记录
	HWList3 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getNonDocument());// SP 
	for(HWRateVO hwRateVO:HWList3){
		list3.add(hwRateVO.getCountryName());
		HWtraiffMapRP.put(hwRateVO.getCountryName()+"2_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	//特价重货记录
	HWList4 = examService.getHWRateVO(businessId, PTPARAMETERS.PAYMENT[1], zoneType.getEconomy());// SP 
	for(HWRateVO hwRateVO:HWList4){
		list4.add(hwRateVO.getCountryName());
		HWtraiffMapRP.put(hwRateVO.getCountryName()+"3_"+hwRateVO.getWeightBandName(), hwRateVO.getRate());
	}
	list1 = removeDuplicateWithOrder(list1);
	list2 = removeDuplicateWithOrder(list2);
	list3 = removeDuplicateWithOrder(list3);
	list4 = removeDuplicateWithOrder(list4);
	try {
		pdfPathString = pdfPathString+"\\"+"Finacial-"+business.getApplicationReference()+".pdf";
		FileOutputStream fos = new FileOutputStream(pdfPathString);
		response.setContentType("application/pdf;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("RateCard-"+business.getApplicationReference()+".pdf").getBytes(), "ISO8859-1"));
		OutputStream out = response.getOutputStream();//返回ServletOutputStream
//		ByteArrayOutputStream baos = new PDFGenerater().generatePDF_C(zoneGroupList,documentList,ndocumentList,eonomyList,discountMap,recDiscountMap,productList,
//        			business,customer,pdfPathString,HWtraiffMapSP,HWtraiffMapRP,list1,list2,list3,list4,GdocumentListSP,GndocumentListSP,GeonomyListSP,GdocumentListRP,GndocumentListRP,GeonomyListRP);
//		response.setContentLength(baos.size());
//		baos.writeTo(out);
		out.flush();
        fos.close();
        out.close();
        //下载结束后进行删除动作
        FileUtil.delFile(pdfPathString);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
 }
 
 

	private List<String> removeDuplicateWithOrder(List<String> list) { 
        Set<String> set = new HashSet<String>(); 
        List<String> newList = new ArrayList<String>(); 
         for (Iterator<String> iter = list.iterator(); iter.hasNext();) { 
             String element = iter.next(); 
            if (set.add(element)) 
                 newList.add(element); 
         } 
         return newList; 
     } 
	
	public static void main(String[] args) {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add("xxx");
		hashSet.add("aaaa");
		hashSet.add("xxx");
		System.out.println(hashSet.size());
		System.out.println(hashSet.toArray());
	}
}
