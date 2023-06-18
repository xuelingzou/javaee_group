package com.zhengyuan.liunao.controller.dealcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.hutool.http.HttpStatus;
import com.zhengyuan.liunao.entity.Client;
import com.zhengyuan.liunao.entity.Order;
import com.zhengyuan.liunao.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import com.zhengyuan.liunao.entity.Admin;
import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.service.AdminService;
import com.zhengyuan.liunao.service.CompanyService;
import com.zhengyuan.liunao.service.ClientService;

import cn.hutool.crypto.SecureUtil;

@Controller
@RequestMapping("/Sys")
public class LoginResgisterController {

	@Autowired
	AdminService adminService;

	@Autowired
	CompanyService companyService;

	@Autowired
	ClientService clientService;
	
	/*登录*/
    @ResponseBody
	@PostMapping(value = "/v1/users/login")
	public String getInfo(@RequestBody Map<String,String> map, HttpSession httpSession) {
		String identify = map.get("identify");
		String num = map.get("num");
		String psw = map.get("psw");
		String dataJson = "fail";
		if (Integer.parseInt(identify) == 0) { //管理员
			List<Admin> adminList = new ArrayList<>();
			adminList = adminService.findAdmin(num, SecureUtil.md5(psw));
			if (adminList.size() > 0) {
				String account = adminList.get(0).getAccount();
				String name = adminList.get(0).getName();
				httpSession.setAttribute("account", account);
				httpSession.setAttribute("name", name);
				httpSession.setAttribute("photo", "admin.png");
				httpSession.setAttribute("role", "admin");
				dataJson = JSON.toJSONString(adminList);
				return dataJson;
			}
		} else if (Integer.parseInt(identify) == 2) { //客户
			List<Client> clientList = new ArrayList<>();
			clientList = clientService.findClient(num, SecureUtil.md5(psw));
			if (clientList.size() > 0) {
//				使用httpSession来记录当前在线用户，之后从controller参数中获取HttpSession 对象，使用get方法获取属性值
				String name = clientList.get(0).getCeName();
				String account = clientList.get(0).getCeid();
				httpSession.setAttribute("account", account);
				httpSession.setAttribute("name", name);
				httpSession.setAttribute("role", "client");
				dataJson = JSON.toJSONString(clientList);

				return dataJson;
			}
		} else if (Integer.parseInt(identify) == 1) { //承运商
			List<Company> companyList = new ArrayList<>();
			companyList = companyService.findCompany(num, SecureUtil.md5(psw));
			if (companyList.size() > 0) {
				String name = companyList.get(0).getCoName();
				String account = companyList.get(0).getCoid();
				httpSession.setAttribute("account", account);
				httpSession.setAttribute("name", name);
				httpSession.setAttribute("role", "company");
				dataJson = JSON.toJSONString(companyList);

				return dataJson;
			}
		}
		return "fail";
	}
	
	/*承运商注册*/
	@PostMapping(value = "/v1/companies")
	@ResponseBody
	public JsonResult<Company> registerCompanyDeal(@RequestBody Map<String,String> map) {
		System.out.println("company psw:"+map.get("psw"));
		map.put("psw", SecureUtil.md5(map.get("psw")));
		if (companyService.addCompany(map) > 0) {
			return new JsonResult<>(HttpStatus.HTTP_CREATED,"success");
		}
		return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"failure");
	}


	/*客户注册*/
	@PostMapping(value = "/v1/clients")
	@ResponseBody
	public JsonResult<Client> registerClientDeal(@RequestBody Map<String,String> map) {
		System.out.println("client psw:"+map.get("psw"));
		map.put("psw", SecureUtil.md5(map.get("psw")));
		
		if (clientService.addClient(map) > 0) {
			return new JsonResult<>(HttpStatus.HTTP_CREATED,"success");
		}
		return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"failure");
	}

}
