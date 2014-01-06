package org.tnt.pt.web.baseInfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import org.tnt.pt.entity.DiscountDefault;
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.service.baseInfo.DiscountDefaultService;
import org.tnt.pt.service.baseInfo.ProductService;
import org.tnt.pt.service.baseInfo.ZoneGroupService;
import org.tnt.pt.service.baseInfo.ZoneTypeService;
import org.tnt.pt.vo.JsonData;


/**
 * ProductController负责产品的请求，
 * 
 * @author yuanchen
 */
@Controller
@RequestMapping(value = "/discount")
public class DiscountController {
    @Autowired
	ZoneGroupService zonegroupService;
    @Autowired
	ZoneTypeService zoneTypeService;
    @Autowired
	ProductService productService;
    @Autowired
	DiscountDefaultService discountdefaultService;
    
    
    /**
	 * 产品_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="init")
	public String init(Model model) {
		/**
		 * 该处为zonetypeList获取代码
		 */
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
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
			zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
			productList.add(productService.getProduct(zoneType.getDocument()));
			productList.add(productService.getProduct(zoneType.getNonDocument()));
			productList.add(productService.getProduct(zoneType.getEconomy()));
		}
		discountDefaultList = discountdefaultService.getAllDiscountDefault();
		for (DiscountDefault discountDefault:discountDefaultList) {
			discountDefaultMap.put(discountDefault.getProductId()+"_"+discountDefault.getZoneGroupId(), discountDefault.getDiscount());
		}
		
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("productList", productList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		model.addAttribute("discountDefaultMap", discountDefaultMap);
		
		return "discountRate/discountRateMaintenance";
	}
	
	/**
	 * 产品_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="query")
	public String query(@RequestParam(value = "id", required = false) Long id,Model model) {
		ZoneType zoneType = zoneTypeService.getZoneTypeById(id);
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<Product> productList = new ArrayList<Product>();
		List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
		List<DiscountDefault> discountDefaultList = new ArrayList<DiscountDefault>();
		Map<String,Double> discountDefaultMap = new HashMap<String,Double>();//形成折扣map 方便查询
		
		/**
		 * 获取产品与zonegroup集合
		 */
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		productList.add(productService.getProduct(zoneType.getDocument()));
		productList.add(productService.getProduct(zoneType.getNonDocument()));
		productList.add(productService.getProduct(zoneType.getEconomy()));
		
		/**
		 * 获取默认折扣map
		 */
		discountDefaultList = discountdefaultService.getAllDiscountDefault();
		for (DiscountDefault discountDefault:discountDefaultList) {
			discountDefaultMap.put(discountDefault.getProductId()+"_"+discountDefault.getZoneGroupId(), discountDefault.getDiscount());
		}
		
		
		zoneTypeList =  zoneTypeService.getAllZoneType();
		
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("productList", productList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		model.addAttribute("discountDefaultMap", discountDefaultMap);
		model.addAttribute("id", id);
		return "discountRate/discountRateMaintenance";
	}
	
	
	@RequestMapping(value="add", method = {RequestMethod.POST })
	@ResponseBody 
	public String addDiscountDefault(@RequestBody String jsonDatas) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		//List<DiscountDefault>  discountDefaultList = new ArrayList<DiscountDefault>();
		try {
			JSONArray array = new JSONArray(jsonDatas); 
			for(int i = 0; i < array.length(); i++) {  
				JsonData jsonData = JsonMapper.nonDefaultMapper().fromJson(array.getString(i), JsonData.class);  
                jsonDataList.add(jsonData);  
            }  
		} catch (ParseException e) {
			msg = e.getMessage();
		}
		for (JsonData jsonData:jsonDataList) {
			String name = jsonData.getName();
			String[] discountArr = name.split("_");
			DiscountDefault dd = new DiscountDefault();
			dd.setProductId(Long.valueOf(discountArr[1]));
			dd.setZoneGroupId(Long.valueOf(discountArr[2]));
			String value = jsonData.getValue();
			dd.setDiscount(Double.valueOf((value==null||"".equals(value))?"0":value));
			discountdefaultService.add(dd);
		}
		return msg;
	}
	

}
