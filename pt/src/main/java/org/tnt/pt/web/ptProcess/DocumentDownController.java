package org.tnt.pt.web.ptProcess;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
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
import org.tnt.pt.util.DoubleUtil;
import org.tnt.pt.util.FileUtil;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.RevVO;

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
	
 @RequestMapping(value="downDocument/{id}")
 public String downDocument1(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") Long businessId,
		 Model model,@ModelAttribute BusinessVO businessVO) {
		/*try {
			response.setContentType("application/vnd;charset=utf-8");
			// 表示以附件的形式把文件发送到客户端
			response.setHeader("Content-Disposition", "attachment;filename="
						+ new String((123+".PDF").getBytes(), "ISO8859-1"));
			// 通过response的输出流把工作薄的流发送浏览器形成文件
			OutputStream os = response.getOutputStream();
			*//**
			 * 把参数都放到context中去
			 *//*
			Map<String,Object> context = new HashMap<String,Object>();
			context.put("userName", "ggggggggggggg1232124");
			String html = document1Service.generateContent(context);
			PDFRender.PDFDown(html,os);	
	 } catch (IOException e) { 
         e.printStackTrace(); 
         throw new RuntimeException("IO出错！", e); 
     } catch (DocumentException e) {
    	 throw new RuntimeException("下载文档客户端出错！", e);
	}*/
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
	List<ZoneGroup> zoneGroupDefaultList = new ArrayList<ZoneGroup>();
	List<Product> productList = new ArrayList<Product>();
	List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
	List<DiscountDefault> discountDefaultList = new ArrayList<DiscountDefault>();
	Map<String,Double> discountDefaultMap = new HashMap<String,Double>();//形成折扣map 方便查询
	
	/**
	 * 假如zonetype不为空，则默认初始加载的为第一个zonetype
	 */
	zoneTypeList =  zoneTypeService.getAllZoneType();
	if(zoneTypeList.size()>0){
		ZoneType zoneType = zoneTypeList.get(0);
		zoneGroupDefaultList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		productList.add(productService.getProduct(zoneType.getDocument()));
		productList.add(productService.getProduct(zoneType.getNonDocument()));
		productList.add(productService.getProduct(zoneType.getEconomy()));
	}
	discountDefaultList = discountdefaultService.getAllDiscountDefault();
	for (DiscountDefault discountDefault:discountDefaultList) {
		discountDefaultMap.put(discountDefault.getProductId()+"_"+discountDefault.getZoneGroupId(), discountDefault.getDiscount());
	}
	
	List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
	List<WeightBand> documentList = new ArrayList<WeightBand>();
	List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
	List<WeightBand> eonomyList = new ArrayList<WeightBand>();
	List<Discount> discountList = new ArrayList<Discount>();
	List<Discount> recDiscountList = new ArrayList<Discount>();
	List<Tariff> tariffList = new ArrayList<Tariff>();
	ZoneType zoneType = new ZoneType();
	Customer customer = new Customer();
	Map<String,Double> recDiscountMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> traiffMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Business business = businessService.getBusiness(businessId);
	customer = customerService.getCustomer(business.getCustomerId());
	zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
	zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
	documentList = weightBandService.findByProductId(zoneType.getDocument());
	ndocumentList = weightBandService.findByProductId(zoneType.getNonDocument());
	eonomyList = weightBandService.findByProductId(zoneType.getEconomy());
	if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
		discountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[0]);
		recDiscountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[1]);
		for (Discount discount:recDiscountList) {
			recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
	}else if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
		discountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[2]);//都取both
		recDiscountList = discountService.getAllDiscountByBusId(businessId,PTPARAMETERS.PAYMENT[2]);
		for (Discount discount:recDiscountList) {
			recDiscountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
	}else{
		discountList = discountService.getAllDiscountByBusId(businessId,customer.getPayment());
	}
	
	for (Discount discount:discountList) {
		discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
	}
	tariffList = tariffService.getAllTariff();
	for (Tariff tariff:tariffList) {
		traiffMap.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	try {
		pdfPathString = pdfPathString+"/"+business.getApplicationReference()+".pdf";
		FileOutputStream fos = new FileOutputStream(pdfPathString);
		response.setContentType("application/pdf;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((business.getApplicationReference()+".pdf").getBytes(), "ISO8859-1"));
		OutputStream out = response.getOutputStream();//返回ServletOutputStream
		ByteArrayOutputStream baos = new PDFGenerater().generatePDF(zoneGroupList,documentList,ndocumentList,eonomyList,discountMap,recDiscountMap,traiffMap,zoneGroupDefaultList,productList,
        			discountDefaultMap,business,customer,logoPath,oilFee,zoneImage1,zoneImage2,zoneImage3,zoneImage4,zoneImage5,zoneImage6,pdfPathString);
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
 
 
}
