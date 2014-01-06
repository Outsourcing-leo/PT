package org.tnt.pt.web.baseInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.mapper.JsonMapper;
import org.tnt.pt.entity.Country;
import org.tnt.pt.service.baseInfo.CountryService;


/**
 * ProductController负责产品的请求，
 * 
 * @author yuanchen
 */
@Controller
@RequestMapping(value = "/country")
public class CountryController {
    @Autowired
	CountryService countryService;
    
    /**
	 * ajax 获取城市名称
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/getCountryList")
 	public String getOrgList(HttpServletRequest request,HttpServletResponse response){
     	try {
     		String countryName = request.getParameter("countryName");
 			if (StringUtils.isNotEmpty(countryName)) {
 				countryName = new String(countryName.getBytes("ISO-8859-1"),"UTF-8");
 			}else {
 				countryName = "";
 			}
 			List<Country> countryList = new ArrayList<Country>();
 			countryList = countryService.findByName(countryName);
 			String countrystr = JsonMapper.nonDefaultMapper().toJson(countryList);
 			response.setContentType("text/html; charset=UTF-8");
 			response.setHeader("Cathe-Control", "no-cache");
 			response.setHeader("Pragma", "no-cache");
 			response.getWriter().print(countrystr);
     	} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 		return null;
 	}

}
