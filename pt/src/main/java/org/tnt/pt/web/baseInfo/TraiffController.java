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
import org.tnt.pt.entity.Product;
import org.tnt.pt.entity.Tariff;
import org.tnt.pt.entity.TariffGroup;
import org.tnt.pt.entity.WeightBand;
import org.tnt.pt.entity.ZoneGroup;
import org.tnt.pt.entity.ZoneType;
import org.tnt.pt.service.baseInfo.ProductService;
import org.tnt.pt.service.baseInfo.TariffGroupService;
import org.tnt.pt.service.baseInfo.TariffService;
import org.tnt.pt.service.baseInfo.WeightBandService;
import org.tnt.pt.service.baseInfo.ZoneGroupService;
import org.tnt.pt.service.baseInfo.ZoneTypeService;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.JsonData;


/**
 * ProductController负责产品的请求，
 * 
 * @author yuanchen
 */
@Controller
@RequestMapping(value = "/traiff")
public class TraiffController {
    @Autowired
	ZoneGroupService zonegroupService;
    @Autowired
	ZoneTypeService zoneTypeService;
    @Autowired
	ProductService productService;
    @Autowired
	TariffService tariffService;
    @Autowired
   	TariffGroupService tariffGroupService;
    @Autowired
    WeightBandService weightBandService;
    
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
		//List<WeightBand> weightBandList = new ArrayList<WeightBand>();
		List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
		List<Tariff> tariffList = new ArrayList<Tariff>();
		Map<String,Double> traiffMap = new HashMap<String,Double>();//形成折扣map 方便查询
		List<TariffGroup> tariffGroupList= new ArrayList<TariffGroup>();
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
			//weightBandList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
			tariffGroupList = tariffGroupService.getAllTariffGroup(zoneType.getDocument(),PTPARAMETERS.PAYMENT[1]);
		}
		
		tariffList = tariffService.getAllTariff();
		for (Tariff tariff:tariffList) {
			traiffMap.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
		}
		
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("productList", productList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		model.addAttribute("tariffGroupList", tariffGroupList);
		model.addAttribute("traiffMap", traiffMap);
		
		return "discountRate/fullTariffMaintenance";
	}
	
	/**
	 * 产品_时区_折扣  详细页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="query")
	public String query(@RequestParam(value = "productId", required = false) Long productId,
			@RequestParam(value = "zoneTypeId", required = false) Long zoneTypeId,
			@RequestParam(value = "payment", required = false) String payment,Model model) {
		
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<Product> productList = new ArrayList<Product>();
		//List<WeightBand> weightBandList = new ArrayList<WeightBand>();
		List<ZoneType> zoneTypeList = new ArrayList<ZoneType>();
		List<Tariff> tariffList = new ArrayList<Tariff>();
		List<TariffGroup> tariffGroupList = new ArrayList<TariffGroup>();
		Map<String,Double> traiffMap = new HashMap<String,Double>();//形成折扣map 方便查询
		
		ZoneType zoneType = zoneTypeService.getZoneTypeById(zoneTypeId);
		//weightBandList = weightBandService.getAllWeightBandByProductId(productId);
		tariffGroupList = tariffGroupService.getAllTariffGroup(productId,payment);
		
		/**
		 * 获取产品与zonegroup集合
		 */
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		productList.add(productService.getProduct(zoneType.getDocument()));
		productList.add(productService.getProduct(zoneType.getNonDocument()));
		productList.add(productService.getProduct(zoneType.getEconomy()));
		
		/**
		 * 获取默认价格map
		 */
		tariffList = tariffService.getAllTariff();
		for (Tariff tariff:tariffList) {
			traiffMap.put(tariff.getTariffGroupId()+"_"+tariff.getZoneGroupId(), tariff.getTariff());
		}
		
		
		zoneTypeList =  zoneTypeService.getAllZoneType();
		
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("productList", productList);
		model.addAttribute("zoneTypeList", zoneTypeList);
		//model.addAttribute("weightBandList", weightBandList);
		model.addAttribute("tariffGroupList", tariffGroupList);
		model.addAttribute("traiffMap", traiffMap);
		model.addAttribute("productId", productId);
		model.addAttribute("zoneTypeId", zoneTypeId);
		return "discountRate/fullTariffMaintenance";
	}
	
	
	@RequestMapping(value="add", method = {RequestMethod.POST })
	@ResponseBody 
	public String addTariff(@RequestBody String jsonDatas) {
		String msg = "";
		List<JsonData> jsonDataList = new ArrayList<JsonData>();
		//List<DiscountDefault>  discountDefaultList = new ArrayList<DiscountDefault>();
		//List<Tariff>  tariffList = new ArrayList<Tariff>();
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
			Tariff tariff = new Tariff();
			tariff.setTariffGroupId(Long.valueOf(discountArr[1]));
			tariff.setZoneGroupId(Long.valueOf(discountArr[2]));
			String value = jsonData.getValue();
			tariff.setTariff(Double.valueOf((value==null||"".equals(value))?"0":value));
			tariffService.add(tariff);
		}
		return msg;
	}
	

}
