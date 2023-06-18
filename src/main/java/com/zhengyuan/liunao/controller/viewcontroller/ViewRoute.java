package com.zhengyuan.liunao.controller.viewcontroller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Sys")
public class ViewRoute {

	/*登录相关*/
	@RequestMapping("/loginView")
	public String loginView(HttpSession httpSession) {
		
		return "login";
	}
	
	
	@RequestMapping("/loginOut")
	public String loginOut(HttpSession httpSession) {
		httpSession.removeAttribute("name");
		httpSession.removeAttribute("account");
		httpSession.removeAttribute("photo");
		return "redirect:/Sys/loginView";
	}

	/*管理员主页*/
	@RequestMapping("/adminIndex")
	public String adminIndex(HttpSession s) {
		String perssion = s.getAttribute("role").toString();
		if(perssion.equals("admin")) {
			return "adminIndex";
		}else {
			return "redirect:/Sys/loginView";
		}
	}

	/*管理员查看订单*/
	@RequestMapping("/adminOrderSearch")
	public String adminOrderSearch() {
		return "adminOrderSearch";
	}

	/*管理员查看客户信息*/
	@RequestMapping("/clientInfo")
	public String stuInfo() {
		return "clientInfo";
	}

	/*管理员查询承运商信息*/
	@RequestMapping("/companyInfo")
	public String teacherInfo() {
		return "companyInfo";
	}


	/*客户主页*/
	@RequestMapping("/clientIndex")
	public String stuIndex() {
		return "clientIndex";
	}
	
	/*新增客户*/
	@RequestMapping("/clientAdd")
	public String StuRegister() {
		return "clientAdd";
	}

	/*客户信息修改*/
	@RequestMapping("/clientModi")
	public ModelAndView stuModi(ModelAndView mav, @RequestParam("num") String num) {
		mav.addObject("num", num);
		mav.setViewName("clientModi");
		return mav;
	}

	/*客户发起新订单*/
	@RequestMapping("/clientNewOrder")
	public String clientNewOrder() {
		return "clientNewOrder";
	}


	/*客户已发订单查询*/
	@RequestMapping("/clientOrderSearch")
	public String stuScore() {
		return "clientOrderSearch";
	}

	/*承运商主页*/
	@RequestMapping("/companyIndex")
	public String teacherIndex() {
		return "companyIndex";
	}

	/*新增承运商*/
	@RequestMapping("/companyAdd")
	public String teaAdd() {
		return "companyAdd";
	}

	/*承运商信息修改*/
	@RequestMapping("/companyModi")
	public ModelAndView teaModi(ModelAndView mav, @RequestParam("num") String num) {
		mav.addObject("num", num);
		mav.setViewName("companyModi");
		return mav;
	}

	/*承运商查看待接订单*/
	@RequestMapping("/comOrderSearch")
	public String comOrderSearch() {
		return "comOrderSearch";
	}

	/*承运商管理已接订单*/
	@RequestMapping("/comOrderManage")
	public String comOrderManage() {
		return "comOrderManage";
	}

	/*承运商财务分析*/
	@RequestMapping("/incomeBar")
	public String parseClaComp() {
		return "incomeBar";
	}

	
}
