package org.tnt.pt.web.ptProcess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONArray;
import org.openqa.selenium.net.OlderWindowsVersionEphemeralPortDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springside.modules.mapper.JsonMapper;
import org.tnt.pt.dmsentity.User;
import org.tnt.pt.entity.Business;
import org.tnt.pt.entity.BusinessFile;
import org.tnt.pt.entity.Consignment;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.entity.Discount;
import org.tnt.pt.entity.Exam;
import org.tnt.pt.entity.GEOSummary;
import org.tnt.pt.entity.Review;
import org.tnt.pt.entity.Tariff;
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
import org.tnt.pt.service.costcalculator.CostCalculator;
import org.tnt.pt.service.costcalculator.CostCalculator_Service;
import org.tnt.pt.service.ptProcess.BusinessService;
import org.tnt.pt.service.ptProcess.ConsignmentService;
import org.tnt.pt.service.ptProcess.CustomerService;
import org.tnt.pt.service.ptProcess.DiscountService;
import org.tnt.pt.service.ptProcess.ExamService;
import org.tnt.pt.service.ptProcess.GeoSummaryService;
import org.tnt.pt.service.ptProcess.PtApproveService;
import org.tnt.pt.service.ptProcess.RateService;
import org.tnt.pt.service.ptProcess.RevService;
import org.tnt.pt.service.ptProcess.SpecificConsignmentSetService;
import org.tnt.pt.service.ptProcess.SpecificCountryService;
import org.tnt.pt.service.ptProcess.ZoneSummaryService;
import org.tnt.pt.util.DoubleUtil;
import org.tnt.pt.util.FileUtil;
import org.tnt.pt.util.PTPARAMETERS;
import org.tnt.pt.vo.BusinessVO;
import org.tnt.pt.vo.JsonData;
import org.tnt.pt.vo.RevVO;

/**
 * PT审批流程controller
 * 
 * @author mcl
 */
@Controller
@RequestMapping(value = "/ptApprove")
public class PTApproveController {
	private static final long serialVersionUID = 1L;
	
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
    ExamService examService;
    @Autowired
    PtApproveService approveService;
    @Autowired
    RevService revService;
    
    /**
	 * BSM 和 RSM approve的方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value="salesAdminApprove", method = RequestMethod.POST)
	public String salesAdminApprove(HttpServletRequest request,Model model,@ModelAttribute BusinessVO businessVO) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			String state = "";
			if(user!=null){
				if(user.getRole_name()!=null&&user.getRole_name().equals("salesAdmin")){
					state = PTPARAMETERS.PROCESS_SATE[1];
				}else if(user.getRole_name()!=null&&user.getRole_name().equals("rsm")){
					state = PTPARAMETERS.PROCESS_SATE[2];
				}
			}
			Business business = new Business();
			business.setState(state);business.setId(Long.parseLong(businessVO.getId()));
			Exam exam = new Exam();
			exam.setExamOppion(businessVO.getExamOppion());
			exam.setBusinessId(Long.parseLong(businessVO.getId()));
			exam.setExamTime(new Date());
			exam.setUserId("BSM");//取当前用户id
			examService.insertExam(exam);
			businessService.updateState(business);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/summaryInfoProgress";
	}
	
	/**
	 * 到billing提交页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toBilling", method = RequestMethod.POST)
	public String toBilling(Model model,@RequestParam(value = "id", required = false) Long busiId) {
		try {
			String state =  PTPARAMETERS.PROCESS_SATE[4];
			Business business = new Business();
			business.setState(state);business.setId(busiId);
			businessService.updateState(business);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/tariffPTApprove";
	}
	
	/**
	 * billing center 提交的方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value="billingApprove", method = RequestMethod.POST)
	public String billingApprove(Model model,@ModelAttribute BusinessVO businessVO) {
		try {
			String state =  PTPARAMETERS.PROCESS_SATE[5];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Business business = new Business();
			business.setState(state);business.setEffectiveDate(sdf.parse(businessVO.getEffectiveDate()));
			business.setId(Long.parseLong(businessVO.getId()));
			Customer cus = customerService.getCustomer(business.getCustomerId());
			cus.setAccount(businessVO.getAccount());
			customerService.update(cus);
			Exam exam = new Exam();
			exam.setExamOppion(businessVO.getExamOppion());
			exam.setBusinessId(Long.parseLong(businessVO.getId()));
			exam.setExamTime(new Date());
			exam.setUserId("BSM");//取当前用户id
			examService.insertExam(exam);
			businessService.updateState(business);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/tariffPTBillingApprove";
	}
	
	/**
	 * 进入分析pt 页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="ptAnalysing", method = RequestMethod.POST)
	public String ptAnalysing(Model model,@RequestParam(value = "id", required = false) Long businessId) {
		try {
			Business business = businessService.getBusiness(businessId);
			Customer cus = customerService.getCustomer(business.getCustomerId());
			model.addAttribute("customer", cus);
			model.addAttribute("business", business);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/PTAnalysing";
	}
	
	/**
	 * 由commercial确认  点击confirm后最后返回给sales
	 * @param model
	 * @return
	 */
	@RequestMapping(value="confirmCus", method = RequestMethod.POST)
	public String confirmCus(Model model,@ModelAttribute BusinessVO businessVO) {
		try {
			Business business = businessService.getBusiness(Long.parseLong(businessVO.getId()));
			Customer cus = customerService.getCustomer(business.getCustomerId());
			model.addAttribute("customer", cus);
			model.addAttribute("business", business);
			model.addAttribute("businessVO", businessVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/confirmCus";
	}
	
	/**
	 * billing center 提交的方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value="commercialConfirm", method = RequestMethod.POST)
	public String commercialConfirm(Model model,@ModelAttribute BusinessVO businessVO) {
		try {
			String state =  PTPARAMETERS.PROCESS_SATE[3];
			Business business = new Business();
			business.setState(state);
			business.setId(Long.parseLong(businessVO.getId()));
			Exam exam = new Exam();
			exam.setExamOppion(businessVO.getExamOppion());
			exam.setBusinessId(Long.parseLong(businessVO.getId()));
			exam.setExamTime(new Date());
			exam.setUserId("Commercial");//取当前用户id
			examService.insertExam(exam);
			businessService.updateState(business);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/confirmCus";
	}
	
	/**
	 * 审批拒绝通用方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value="reject", method = RequestMethod.POST)
	public String reject(Model model,@ModelAttribute BusinessVO businessVO) {
		try {
			Business business = new Business();
			business.setState(businessVO.getState());business.setId(Long.parseLong(businessVO.getId()));
			Exam exam = new Exam();
			exam.setExamOppion(businessVO.getExamOppion());
			exam.setBusinessId(Long.parseLong(businessVO.getId()));
			exam.setExamTime(new Date());
			exam.setUserId("BSM");//取当前用户id
			examService.insertExam(exam);
			businessService.updateState(business);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/tariffPTBillingApprove";
	}
	
	/**
	 * 分析页面点击review调用方法
	 * @param model
	 * @return
	 */
	@RequestMapping(value="review", method = RequestMethod.GET)
	public String review(Model model,@ModelAttribute BusinessVO businessVO) {
		List<RevVO> sendReviewList = new ArrayList<RevVO>();
		List<RevVO> recieveReviewList = new ArrayList<RevVO>();
		List<String> showList = new ArrayList<String>();
		String flag = "";String payMent = "";
		String errorMsg = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#.00");
		try {
			String[] groupBy = businessVO.getMultichoised().split(",");
			Business business = businessService.getBusiness(Long.parseLong(businessVO.getId()));
			Customer cus = customerService.getCustomer(business.getCustomerId());
			for (String str : groupBy) {
				if(!str.equals("")){
					showList.add(str);
				}
			}
			flag = cus.getPayment();
			
			List<String> r;
			if(cus.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
				sendReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[0]);
				recieveReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[1]);payMent = "SP";
			}else if(cus.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
				sendReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[2]);
				recieveReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[2]);payMent = "SP";
			}else{
				sendReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, cus.getPayment());
				if(cus.getPayment().equals(PTPARAMETERS.PAYMENT[0])){
					payMent = "SP";
				}else{
					payMent = "RP";
				}
				
			}
//			CostCalculator_Service service = new CostCalculator_Service();
//			CostCalculator cc = service.getCostCalculatorPort();
			//新建一个存放total合计的对象
			RevVO rev = new RevVO();
			for(RevVO view:sendReviewList){
				if(view.getCons()!=0){
					view.setRpc(view.getRev()/view.getCons());
					view.setWpc(view.getKilo()/view.getCons());
					view.setWpc(Double.parseDouble(df.format(view.getWpc())));
					view.setRpc(Double.parseDouble(df.format(view.getRpc())));
				}
				if(view.getKilo()!=0){
					view.setRpk(view.getRev()/view.getKilo());
					view.setRpk(Double.parseDouble(df.format(view.getRpk())));
				}
				rev.setCons(view.getCons()+rev.getCons());
				rev.setKilo(view.getKilo()+rev.getKilo());
				rev.setRev(view.getRev()+rev.getRev());
				rev.setRpc(view.getRpc()+rev.getRpc());
				rev.setRpk(view.getRpk()+rev.getRpk());
				rev.setWpc(view.getWpc()+rev.getWpc());
				//计算 DM  FM
//				r = cc.getTotalCosts(cus.getAccount(), sdf.format(business.getApplicationDate()), payMent, view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), 
//						view.getCOUNTRYNAME(), view.getPRODUCTNAME(), view.getChargeableWeight(), "3", business.getConsStop()+"", Integer.parseInt(view.getChargeableWeight())>=20?"H":"L", "0OKM,[uytgh98}TR54", "");
				view.setDm(Double.parseDouble("523.2"));//r.get(0)
				view.setFm(Double.parseDouble("523.2"));//r.get(1)
				rev.setFm(view.getFm()+rev.getFm());
				rev.setDm(view.getDm()+rev.getDm());
			}
			rev.setRpk(Double.parseDouble(df.format(rev.getRpk())));
			rev.setFm(Double.parseDouble(df.format(rev.getFm())));
			rev.setDm(Double.parseDouble(df.format(rev.getDm())));
			rev.setMultichoised("Total");
			sendReviewList.add(rev);
			
			//新建一个存放total合计的对象
			RevVO rev2 = new RevVO();
			for(RevVO view:recieveReviewList){
				if(view.getCons()!=0){
					view.setRpc(view.getRev()/view.getCons());
					view.setWpc(view.getKilo()/view.getCons());
					view.setWpc(Double.parseDouble(df.format(view.getWpc())));
					view.setRpc(Double.parseDouble(df.format(view.getRpc())));
				}
				if(view.getKilo()!=0){
					view.setRpk(view.getRev()/view.getKilo());
					view.setRpk(Double.parseDouble(df.format(view.getRpk())));
				}
				rev2.setCons(view.getCons()+rev2.getCons());
				rev2.setKilo(view.getKilo()+rev2.getKilo());
				rev2.setRev(view.getRev()+rev2.getRev());
				rev2.setRpc(view.getRpc()+rev2.getRpc());
				rev2.setRpk(view.getRpk()+rev2.getRpk());
				rev2.setWpc(view.getWpc()+rev2.getWpc());
				//计算 DM  FM
//				r = cc.getTotalCosts(cus.getAccount(), sdf.format(business.getApplicationDate()), "RP", view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), 
//						view.getCOUNTRYNAME(), view.getPRODUCTNAME(), view.getChargeableWeight(), "3", business.getConsStop()+"", Integer.parseInt(view.getChargeableWeight())>=20?"H":"L", "0OKM,[uytgh98}TR54", "");
				view.setDm(Double.parseDouble("523.2"));//r.get(0)
				view.setFm(Double.parseDouble("523.2"));//r.get(1)
				rev2.setFm(view.getFm()+rev2.getFm());
				rev2.setDm(view.getDm()+rev2.getDm());
			}
			rev2.setRpk(Double.parseDouble(df.format(rev2.getRpk())));
			rev2.setFm(Double.parseDouble(df.format(rev2.getFm())));
			rev2.setDm(Double.parseDouble(df.format(rev2.getDm())));
			rev2.setMultichoised("Total");
			recieveReviewList.add(rev2);
			model.addAttribute("flag",flag);
			model.addAttribute("errorMsg",errorMsg);
			model.addAttribute("counter",showList.size());
			model.addAttribute("customer", cus);
			model.addAttribute("business", business);
			model.addAttribute("showList",showList);
			model.addAttribute("sendReviewList", sendReviewList);
			model.addAttribute("recieveReviewList", recieveReviewList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/caculateReview";
	}
	
	/**
	 * 导出计算结果
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/exportBatchExcel", method = RequestMethod.POST)
	public String exportBatchExcel(Model model,@ModelAttribute BusinessVO businessVO,HttpServletResponse response) {
		List<RevVO> sendReviewList = new ArrayList<RevVO>();
		List<RevVO> recieveReviewList = new ArrayList<RevVO>();
		List<String> showList = new ArrayList<String>();
		String flag = "";String payMent = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#.00");
		try {
			String[] groupBy = businessVO.getMultichoised().split(",");
			Business business = businessService.getBusiness(Long.parseLong(businessVO.getId()));
			Customer cus = customerService.getCustomer(business.getCustomerId());
			for (String str : groupBy) {
				if(!str.equals("")){
					showList.add(str);
				}
			}
			flag = cus.getPayment();
			
			List<String> r;
			if(cus.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
				sendReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[0]);
				recieveReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[1]);payMent = "SP";
			}else if(cus.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("YES")){//如果选择的是both 并且 isfollow为YES  此时需要展示两个同样的tab页
				sendReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[2]);
				recieveReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, PTPARAMETERS.PAYMENT[2]);payMent = "SP";
			}else{
				sendReviewList = revService.getGroupBy(Long.parseLong(businessVO.getId()), groupBy, cus.getPayment());
				if(cus.getPayment().equals(PTPARAMETERS.PAYMENT[0])){
					payMent = "SP";
				}else{
					payMent = "RP";
				}
				
			}
//			CostCalculator_Service service = new CostCalculator_Service();
//			CostCalculator cc = service.getCostCalculatorPort();
			//新建一个存放total合计的对象
			RevVO rev = new RevVO();
			for(RevVO view:sendReviewList){
				if(view.getCons()!=0){
					view.setRpc(view.getRev()/view.getCons());
					view.setWpc(view.getKilo()/view.getCons());
					view.setWpc(Double.parseDouble(df.format(view.getWpc())));
					view.setRpc(Double.parseDouble(df.format(view.getRpc())));
				}
				if(view.getKilo()!=0){
					view.setRpk(view.getRev()/view.getKilo());
					view.setRpk(Double.parseDouble(df.format(view.getRpk())));
				}
				rev.setCons(view.getCons()+rev.getCons());
				rev.setKilo(view.getKilo()+rev.getKilo());
				rev.setRev(view.getRev()+rev.getRev());
				rev.setRpc(view.getRpc()+rev.getRpc());
				rev.setRpk(view.getRpk()+rev.getRpk());
				rev.setWpc(view.getWpc()+rev.getWpc());
				//计算 DM  FM
//				r = cc.getTotalCosts(cus.getAccount(), sdf.format(business.getApplicationDate()), payMent, view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), 
//						view.getCOUNTRYNAME(), view.getPRODUCTNAME(), view.getChargeableWeight(), "3", business.getConsStop()+"", Integer.parseInt(view.getChargeableWeight())>=20?"H":"L", "0OKM,[uytgh98}TR54", "");
				view.setDm(Double.parseDouble("523.2"));//r.get(0)
				view.setFm(Double.parseDouble("523.2"));//r.get(1)
				rev.setFm(view.getFm()+rev.getFm());
				rev.setDm(view.getDm()+rev.getDm());
			}
			rev.setRpk(Double.parseDouble(df.format(rev.getRpk())));
			rev.setFm(Double.parseDouble(df.format(rev.getFm())));
			rev.setDm(Double.parseDouble(df.format(rev.getDm())));
			rev.setMultichoised("Total");
			sendReviewList.add(rev);
			
			//新建一个存放total合计的对象
			RevVO rev2 = new RevVO();
			for(RevVO view:recieveReviewList){
				if(view.getCons()!=0){
					view.setRpc(view.getRev()/view.getCons());
					view.setWpc(view.getKilo()/view.getCons());
					view.setWpc(Double.parseDouble(df.format(view.getWpc())));
					view.setRpc(Double.parseDouble(df.format(view.getRpc())));
				}
				if(view.getKilo()!=0){
					view.setRpk(view.getRev()/view.getKilo());
					view.setRpk(Double.parseDouble(df.format(view.getRpk())));
				}
				rev2.setCons(view.getCons()+rev2.getCons());
				rev2.setKilo(view.getKilo()+rev2.getKilo());
				rev2.setRev(view.getRev()+rev2.getRev());
				rev2.setRpc(view.getRpc()+rev2.getRpc());
				rev2.setRpk(view.getRpk()+rev2.getRpk());
				rev2.setWpc(view.getWpc()+rev2.getWpc());
				//计算 DM  FM
//				r = cc.getTotalCosts(cus.getAccount(), sdf.format(business.getApplicationDate()), "RP", view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), view.getCOUNTRYNAME(), 
//						view.getCOUNTRYNAME(), view.getPRODUCTNAME(), view.getChargeableWeight(), "3", business.getConsStop()+"", Integer.parseInt(view.getChargeableWeight())>=20?"H":"L", "0OKM,[uytgh98}TR54", "");
				view.setDm(Double.parseDouble("523.2"));//r.get(0)
				view.setFm(Double.parseDouble("523.2"));//r.get(1)
				rev2.setFm(view.getFm()+rev2.getFm());
				rev2.setDm(view.getDm()+rev2.getDm());
			}
			rev2.setRpk(Double.parseDouble(df.format(rev2.getRpk())));
			rev2.setFm(Double.parseDouble(df.format(rev2.getFm())));
			rev2.setDm(Double.parseDouble(df.format(rev2.getDm())));
			rev2.setMultichoised("Total");
			recieveReviewList.add(rev2);
			model.addAttribute("flag",flag);
			model.addAttribute("counter",showList.size());
			model.addAttribute("customer", cus);
			model.addAttribute("business", business);
			model.addAttribute("showList",showList);
			model.addAttribute("sendReviewList", sendReviewList);
			model.addAttribute("recieveReviewList", recieveReviewList);
			createExportExcel(sendReviewList,recieveReviewList,response,showList,cus.getPayment());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ptProcess/PTAnalysing";
	}
	
	/**
	 * adjust commercial调整页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="adjust", method = RequestMethod.POST)
	public String adjust(Model model,@ModelAttribute BusinessVO businessVO) {
		
		List<ZoneGroup> zoneGroupList = new ArrayList<ZoneGroup>();
		List<WeightBand> documentList = new ArrayList<WeightBand>();
		List<WeightBand> ndocumentList = new ArrayList<WeightBand>();
		List<WeightBand> eonomyList = new ArrayList<WeightBand>();
		List<ZoneSummary> zoneSummaryList = new ArrayList<ZoneSummary>();
		List<GEOSummary> geoSummaryList = new ArrayList<GEOSummary>();
		List<Discount> discountList = new ArrayList<Discount>();
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		
		business = businessService.getBusiness(Long.parseLong(businessVO.getId()));
		customer = customerService.getCustomer(business.getCustomerId());
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		
		//计算zonesummary
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(business.getZoneType());
		zoneSummaryList = zoneSummaryService.getAllZoneSummaryByBusinessId(business.getId(),customer.getPayment());
		
		geoSummaryList = geoSummaryService.getAllGeoSummaryByBusinessId(business.getId(),customer.getPayment());
		
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		eonomyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		
		
		model.addAttribute("business", business);
		model.addAttribute("customer", customer);
		model.addAttribute("geoSummaryList", geoSummaryList);
		model.addAttribute("zoneSummaryList", zoneSummaryList);
		model.addAttribute("zoneGroupList", zoneGroupList);
		model.addAttribute("documentList", documentList);
		model.addAttribute("ndocumentList", ndocumentList);
		model.addAttribute("eonomyList", eonomyList);
		
		discountList = discountService.getAllDiscountByBusId(business.getId(),customer.getPayment());
		for (Discount discount:discountList) {
			discountMap.put(discount.getWeightBandId()+"_"+discount.getZoneGroupId(), discount.getDiscount());
		}
		model.addAttribute("discountMap", discountMap);
		
		return "ptProcess/adjust";
	}
	
	@RequestMapping(value="updateConsignment", method = {RequestMethod.POST })
	@ResponseBody 
	public String updateConsignment(@RequestBody String jsonDatas) {
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
				Discount dd = new Discount();
				String name = jsonData.getName();
				String[] discountArr = name.split("_");
				
				dd.setWeightBandId(Long.valueOf(discountArr[1]));
				dd.setZoneGroupId(Long.valueOf(discountArr[2]));
				String value = jsonData.getValue();
				dd.setDiscount(Double.valueOf((value==null||"".equals(value))?"0":value));
				dd.setBusinessId(Long.valueOf(discountArr[3]));
				dd.setPayment(PTPARAMETERS.PAYMENT[0]);
				discountList.add(dd);
			}
			discountService.add(discountList);
		} catch (ParseException e) {
			msg = "parseException";
		}
		return msg;
	}
	
	/**
	 * 公斤_时区_折扣  详细页 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value="tariffPTApprove/{id}", method = RequestMethod.GET)
	public String rateDetail(Model model,@PathVariable("id") Long  busiId) {
		
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
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> recDiscountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		ZoneSummary zoneSummary = new ZoneSummary();
		ZoneSummary recZoneSummary = new ZoneSummary();
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		String flag = "";//标识是否需要两套数据展示
		business = businessService.getBusiness(busiId);//1L为保存后获得的PT业务主表id
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		customer = customerService.getCustomer(business.getCustomerId());//客户信息
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		economyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		flag = customer.getPayment();
		if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
			discountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[0]);
			recDiscountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[1]);
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
			discountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[2]);//都取both
			recDiscountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[2]);
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
			discountList = discountService.getAllDiscountByBusId(busiId,customer.getPayment());
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
		model.addAttribute("discountMap", discountMap);
		model.addAttribute("recDiscountMap", recDiscountMap);
		model.addAttribute("zoneSummary", zoneSummary);
		model.addAttribute("recZoneSummary", recZoneSummary);
	
		return "ptProcess/tariffPTApprove";
	}
	
	/**
	 * 公斤_时区_折扣  详细页 审批过程中查看使用
	 * @param model
	 * @return
	 */
	@RequestMapping(value="tariffPTBillingApprove/{id}", method = RequestMethod.GET)
	public String tariffPTBillingApprove(Model model,@PathVariable("id") Long busiId) {
		
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
		Map<String,Double> discountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		Map<String,Double> recDiscountMap = new HashMap<String,Double>();//形成折扣map 方便查询
		ZoneSummary zoneSummary = new ZoneSummary();
		ZoneSummary recZoneSummary = new ZoneSummary();
		Business business = new Business();
		ZoneType zoneType = new ZoneType();
		Customer customer = new Customer();
		String flag = "";//标识是否需要两套数据展示
		business = businessService.getBusiness(busiId);//1L为保存后获得的PT业务主表id
		zoneType = zoneTypeService.getZoneTypeByZoneType(business.getZoneType());//zonetype类型
		customer = customerService.getCustomer(business.getCustomerId());//客户信息
		zoneGroupList =  zonegroupService.getAllZoneGroupByZoneType(zoneType.getZoneType());
		documentList = weightBandService.getAllWeightBandByProductId(zoneType.getDocument());
		ndocumentList = weightBandService.getAllWeightBandByProductId(zoneType.getNonDocument());
		economyList = weightBandService.getAllWeightBandByProductId(zoneType.getEconomy());
		flag = customer.getPayment();
		if(customer.getPayment().equals(PTPARAMETERS.PAYMENT[2])&&business.getIsFollow().equals("NO")){//如果选择的是both 并且 isfollow为no  此时需要展示两个tab页
			discountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[0]);
			recDiscountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[1]);
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
			discountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[2]);//都取both
			recDiscountList = discountService.getAllDiscountByBusId(busiId,PTPARAMETERS.PAYMENT[2]);
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
			discountList = discountService.getAllDiscountByBusId(busiId,customer.getPayment());
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
		model.addAttribute("discountMap", discountMap);
		model.addAttribute("recDiscountMap", recDiscountMap);
		model.addAttribute("zoneSummary", zoneSummary);
		model.addAttribute("recZoneSummary", recZoneSummary);
	
		return "ptProcess/tariffPTBillingApprove";
	}

    private transient Session session;
	
    /**
     * 群发邮件
     * 
     * @param recipients
     *                收件人们
     * @param subject
     *                主题
     * @param content
     *                内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Object content)
        throws AddressException, MessagingException {
    // 创建mime类型邮件
    final MimeMessage message = new MimeMessage(session);
    // 设置发信人
//    message.setFrom(new InternetAddress(authenticator.getUsername()));
    // 设置收件人们
    final int num = recipients.size();
    InternetAddress[] addresses = new InternetAddress[num];
    for (int i = 0; i < num; i++) {
        addresses[i] = new InternetAddress(recipients.get(i));
    }
    message.setRecipients(RecipientType.TO, addresses);
    // 设置主题
    message.setSubject(subject);
    // 设置邮件内容
    message.setContent(content.toString(), "text/html;charset=utf-8");
    // 发送
    Transport.send(message);
    }

    /**
	 * 导出excel
	 * @param model
	 * @return
	 */
    public void createExportExcel(List<RevVO> sendlist,List<RevVO> recievelist,
			HttpServletResponse response,List<String> showList,String payMent){
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();//创建工作薄
			int showSize = showList.size()-1;
			if(payMent.equals(PTPARAMETERS.PAYMENT[2])){//both  则需要生成两个sheet，否则是一个
				HSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, PTPARAMETERS.PAYMENT[0]);
				HSSFFont font = workbook.createFont();  
				font.setColor(Font.COLOR_RED);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				HSSFCellStyle cellStyle = workbook.createCellStyle();//创建格式
				cellStyle.setFont(font);
				List<String> exportList = new ArrayList<String>();
				for(String str:showList){exportList.add(str); }
				exportList.add("Rev");exportList.add("Cons");exportList.add("Kilo");exportList.add("RPC");
				exportList.add("RPK");exportList.add("WPC");exportList.add("FM");exportList.add("DM");
				HSSFRow row = sheet.createRow((short) 0);					//第一行 表头
				for(short i = 0;i < exportList.size();i++){   
					HSSFCell cell = row.createCell(i);      //创建第1行单元格   
				    cell.setCellValue(exportList.get(i));
				    cell.setCellStyle(cellStyle);
				}
		        //第二行开始是数据
		        for(int i=1;i<sendlist.size()+1;i++){
		        	HSSFRow rowdata = sheet.createRow((short) i);
		        	HashMap<Integer, String> cellMap = new HashMap<Integer, String>();
		        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        	for(int k=0;k<=showSize;k++){
		        		String str = showList.get(k);
		        		if(str.equals("Country")){
		        			cellMap.put(k+1, sendlist.get(i-1).getCOUNTRYNAME()+"");
		        		}if(str.equals("Product")){
		        			cellMap.put(k+1, sendlist.get(i-1).getPRODUCTNAME()+"");
		        		}if(str.equals("PT Zone")){
		        			cellMap.put(k+1, sendlist.get(i-1).getZone()+"");
		        		}if(str.equals("WeightBand")){
		        			cellMap.put(k+1, sendlist.get(i-1).getWEIGHTNAME()+"");
		        		}
		        	}
		        	cellMap.put(showSize+2, sendlist.get(i-1).getRev()+"");cellMap.put(showSize+8, sendlist.get(i-1).getFm()+"");
		        	cellMap.put(showSize+3, sendlist.get(i-1).getCons()+"");cellMap.put(showSize+4, sendlist.get(i-1).getKilo()+"");
		        	cellMap.put(showSize+5, sendlist.get(i-1).getRpc()+"");cellMap.put(showSize+6, sendlist.get(i-1).getRpk()+"");
		        	cellMap.put(showSize+7, sendlist.get(i-1).getWpc()+"");cellMap.put(showSize+9, sendlist.get(i-1).getDm()+"");
		        	for (int j = 0; j < exportList.size(); j++) {
		    			HSSFCell celldata = rowdata.createCell((short) j);      // 在上面行索引0的位置创建单元格
		    			celldata.setCellType(Cell.CELL_TYPE_STRING);     	// 定义单元格为字符串类型
		    			if(i!=sendlist.size()){
		    				celldata.setCellValue(cellMap.get(j+1)+ "");
		    			}else{
		    				if(j<=showSize&&j==0){
		    					celldata.setCellValue("Total");	
		    				}if(j>showSize){
		    					celldata.setCellValue(cellMap.get(j+1)+ "");
		    				}
		    			}
					}
		        }
		        HSSFSheet sheetReceive = workbook.createSheet();
		        workbook.setSheetName(1, PTPARAMETERS.PAYMENT[1]);
				font.setColor(Font.COLOR_RED);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				HSSFRow rowRe = sheetReceive.createRow((short) 0);					//第一行 表头
				for(short i = 0;i < exportList.size();i++){   
					HSSFCell cell = rowRe.createCell(i);      //创建第1行单元格   
				    cell.setCellValue(exportList.get(i));
				    cell.setCellStyle(cellStyle);
				}
		        //第二行开始是数据
		        for(int i=1;i<recievelist.size()+1;i++){
		        	HSSFRow rowdata = sheetReceive.createRow((short) i);
		        	HashMap<Integer, String> cellMap = new HashMap<Integer, String>();
		        	for(int k=0;k<=showSize;k++){
		        		String str = showList.get(k);
		        		if(str.equals("Country")){
		        			cellMap.put(k+1, recievelist.get(i-1).getCOUNTRYNAME()+"");
		        		}if(str.equals("Product")){
		        			cellMap.put(k+1, recievelist.get(i-1).getPRODUCTNAME()+"");
		        		}if(str.equals("PT Zone")){
		        			cellMap.put(k+1, recievelist.get(i-1).getZone()+"");
		        		}if(str.equals("WeightBand")){
		        			cellMap.put(k+1, recievelist.get(i-1).getWEIGHTNAME()+"");
		        		}
		        	}
		        	cellMap.put(showSize+2, recievelist.get(i-1).getRev()+"");cellMap.put(showSize+8, recievelist.get(i-1).getFm()+"");
		        	cellMap.put(showSize+3, recievelist.get(i-1).getCons()+"");cellMap.put(showSize+4, recievelist.get(i-1).getKilo()+"");
		        	cellMap.put(showSize+5, recievelist.get(i-1).getRpc()+"");cellMap.put(showSize+6, recievelist.get(i-1).getRpk()+"");
		        	cellMap.put(showSize+7, recievelist.get(i-1).getWpc()+"");cellMap.put(showSize+9, recievelist.get(i-1).getDm()+"");
		        	for (int j = 0; j < exportList.size(); j++) {
		    			HSSFCell celldata = rowdata.createCell((short) j);      // 在上面行索引0的位置创建单元格
		    			celldata.setCellType(Cell.CELL_TYPE_STRING);     	// 定义单元格为字符串类型
		    			if(i!=recievelist.size()){
		    				celldata.setCellValue(cellMap.get(j+1)+ "");
		    			}else{
		    				if(j<=showSize&&j==0){
		    					celldata.setCellValue("Total");	
		    				}if(j>showSize){
		    					celldata.setCellValue(cellMap.get(j+1)+ "");
		    				}
		    			}
					}
		        }
			}else{
				HSSFSheet sheet = workbook.createSheet();
				workbook.setSheetName(0, payMent );
				HSSFFont font = workbook.createFont();  
				font.setColor(Font.COLOR_RED);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				HSSFCellStyle cellStyle = workbook.createCellStyle();//创建格式
				cellStyle.setFont(font);
				List<String> exportList = new ArrayList<String>();
				for(String str:showList){exportList.add(str); }
				exportList.add("Rev");exportList.add("Cons");exportList.add("Kilo");exportList.add("RPC");
				exportList.add("RPK");exportList.add("WPC");exportList.add("FM");exportList.add("DM");
				HSSFRow row = sheet.createRow((short) 0);					//第一行 表头
				for(short i = 0;i < exportList.size();i++){   
					HSSFCell cell = row.createCell(i);      //创建第1行单元格   
				    cell.setCellValue(exportList.get(i));
				    cell.setCellStyle(cellStyle);
				}
		        //第二行开始是数据
		        for(int i=1;i<sendlist.size()+1;i++){
		        	HSSFRow rowdata = sheet.createRow((short) i);
		        	HashMap<Integer, String> cellMap = new HashMap<Integer, String>();
		        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        	for(int k=0;k<=showSize;k++){
		        		String str = showList.get(k);
		        		if(str.equals("Country")){
		        			cellMap.put(k+1, sendlist.get(i-1).getCOUNTRYNAME()+"");
		        		}if(str.equals("Product")){
		        			cellMap.put(k+1, sendlist.get(i-1).getPRODUCTNAME()+"");
		        		}if(str.equals("PT Zone")){
		        			cellMap.put(k+1, sendlist.get(i-1).getZone()+"");
		        		}if(str.equals("WeightBand")){
		        			cellMap.put(k+1, sendlist.get(i-1).getWEIGHTNAME()+"");
		        		}
		        	}
		        	cellMap.put(showSize+2, sendlist.get(i-1).getRev()+"");cellMap.put(showSize+8, sendlist.get(i-1).getFm()+"");
		        	cellMap.put(showSize+3, sendlist.get(i-1).getCons()+"");cellMap.put(showSize+4, sendlist.get(i-1).getKilo()+"");
		        	cellMap.put(showSize+5, sendlist.get(i-1).getRpc()+"");cellMap.put(showSize+6, sendlist.get(i-1).getRpk()+"");
		        	cellMap.put(showSize+7, sendlist.get(i-1).getWpc()+"");cellMap.put(showSize+9, sendlist.get(i-1).getDm()+"");
		        	for (int j = 0; j < exportList.size(); j++) {
		    			HSSFCell celldata = rowdata.createCell((short) j);      // 在上面行索引0的位置创建单元格
		    			celldata.setCellType(Cell.CELL_TYPE_STRING);     	// 定义单元格为字符串类型
		    			if(i!=sendlist.size()){
		    				celldata.setCellValue(cellMap.get(j+1)+ "");
		    			}else{
		    				if(j<=showSize&&j==0){
		    					celldata.setCellValue("Total");	
		    				}if(j>showSize){
		    					celldata.setCellValue(cellMap.get(j+1)+ "");
		    				}
		    			}
					}
		        }
			}
	    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 表示以附件的形式把文件发送到客户端
			response.setHeader("Content-Disposition", "attachment;filename="
						+ new String(((new Date().getTime())+".xls").getBytes(), "ISO8859-1"));
			// 通过response的输出流把工作薄的流发送浏览器形成文件
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
    
    /**
	 * 文件上传
	 * @param model
	 * @return
	 */
	@RequestMapping(value="uploadFile/{id}", method = RequestMethod.POST)
	public void uploadFile(HttpServletRequest request, HttpServletResponse response,
				Model model,@PathVariable("id") Long  busiId) {
		PrintWriter out = null;
		try {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		//文件保存目录路径
		String savePath = request.getSession().getServletContext().getRealPath("/attached/");
		// 临时文件目录 
		String tempPath = request.getSession().getServletContext().getRealPath("/attached/temp/");
//		String oilFee = request.getSession().getServletContext().getRealPath("/static/images/oilFee.png");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += "/" + ymd + "/";
		//创建文件夹
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		tempPath += "/" + ymd + "/";
		//创建临时文件夹
		File dirTempFile = new File(tempPath);
		if (!dirTempFile.exists()) {
			dirTempFile.mkdirs();
		}
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。   
		factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。   
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		BusinessFile bFile = new BusinessFile();
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			if (!item.isFormField()) {
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String newFileName = getMD5(fileName) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
                    OutputStream os = new FileOutputStream(uploadedFile);
                    InputStream is = item.getInputStream();
                    byte buf[] = new byte[1024];//可以修改 1024 以提高读取速度
                    int length = 0;  
                    while( (length = is.read(buf)) > 0 ){  
                        os.write(buf, 0, length);  
                    }  
                    //关闭流  
                    os.flush();
                    os.close();  
                    is.close();  
                    System.out.println("上传成功！路径："+savePath+"/"+newFileName);
                    bFile.setBusinessId(busiId);
					bFile.setFileName(newFileName);
					bFile.setFilePath(savePath+"/"+newFileName);
					bFile.setUploadDate(new Date());
					String filePathString = ""+examService.getFilePath(busiId, newFileName);
					if(!filePathString.equals(bFile.getFilePath())){
						examService.insertFile(bFile);
					}
                    out.print("1");
				}catch(Exception e){
					e.printStackTrace();
				}
			  }		
			} 
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
    
	// 处理文件上传二   
    @RequestMapping(value = "fileUpload/{id}", method = RequestMethod.POST)   
    public String fileUpload(HttpServletRequest request,@PathVariable("id") Long  busiId)   
            throws IllegalStateException, IOException {   
        // 设置上下方文   
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(   
                request.getSession().getServletContext());   
  
        // 检查form是否有enctype="multipart/form-data"   
        if (multipartResolver.isMultipart(request)) {   
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;   
            Iterator<String> iter = multiRequest.getFileNames();   
            while (iter.hasNext()) {   
                // 由CommonsMultipartFile继承而来,拥有上面的方法.   
                MultipartFile file = multiRequest.getFile(iter.next());   
                if (file != null) {   
                    String fileName = "demoUpload" + file.getOriginalFilename();   
                    String path = "D:/" + fileName;   
  
                    File localFile = new File(path);   
                    file.transferTo(localFile);   
                }   
            }   
        }   
        return "dataSuccess";   
    }   
  
    @RequestMapping(value = "deleteFile", method = RequestMethod.POST)   
    public String deleteFile(Model model,@RequestParam(value = "id", required = false) Long busiId,@RequestParam(value = "fileName", required = false) String fileName){
    	String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		String newFileName = getMD5(fileName) + "." + fileExt;
    	String filePathString = examService.getFilePath(busiId, newFileName);
    	//删除文件
    	FileUtil.delFile(filePathString);
    	examService.deleteFile(busiId, newFileName);
        return "ptProcess/summaryInfoProgress";   
    }   
	
	private String getMD5(String plainText ) { 
		String returnStr = "";
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes()); 
			byte b[] = md.digest(); 
			int i; 
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0) i+= 256; 
			if(i<16) 
			buf.append("0"); 
			buf.append(Integer.toHexString(i)); 
			} 
			returnStr = buf.toString();
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		} 
		return returnStr; 
	} 
	
}
