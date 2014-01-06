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
import org.tnt.pt.util.FileUtil;
import org.tnt.pt.vo.BusinessVO;

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
	String logoPath = request.getSession().getServletContext().getRealPath("/static/images/logoCard.png");
	String oilFee = request.getSession().getServletContext().getRealPath("/static/images/oilFee.png");
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
	List<Tariff> tariffList = new ArrayList<Tariff>();
	ZoneType zoneType = new ZoneType();
	Customer customer = new Customer();
	Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Map<String,Double> traiffMap = new HashMap<String,Double>();//形成折扣map 方便查询
	Business business = businessService.getBusiness(businessId);
	customer = customerService.getCustomer(business.getCustomerId());
	zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
	zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
	documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
	ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
	eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
	discountList = discountService.getAllDiscountByBusId(business.getId(),customer.getPayment());
	for (Discount discount:discountList) {
		discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
	}
	tariffList = tariffService.getAllTariff();
	for (Tariff tariff:tariffList) {
		traiffMap.put(tariff.getWeightBandId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
	}
	try {
		pdfPathString = pdfPathString+"/"+business.getApplicationReference()+".pdf";
		FileOutputStream fos = new FileOutputStream(pdfPathString);
		response.setContentType("application/pdf;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((business.getApplicationReference()+".pdf").getBytes(), "ISO8859-1"));
		OutputStream out = response.getOutputStream();//返回ServletOutputStream
		ByteArrayOutputStream baos = new PDFGenerater().generatePDF(zoneGroupList,documentList,ndocumentList,eonomyList,discountMap,
        			traiffMap,zoneGroupDefaultList,productList,discountDefaultMap,business,customer,logoPath,oilFee,pdfPathString);
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
