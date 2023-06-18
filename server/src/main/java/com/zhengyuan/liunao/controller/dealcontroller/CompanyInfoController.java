package com.zhengyuan.liunao.controller.dealcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpStatus;
import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.zhengyuan.liunao.service.CompanyService;
import com.zhengyuan.liunao.tools.Layui;

import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/Sys")
@Api("CompanyInfoDeal相关api")
@Slf4j
public class CompanyInfoController {
	@Autowired
	CompanyService companyService;

//	获取承运商列表
	@GetMapping(value = "/v1/companies")
	@ResponseBody
	public String getCompanyInfo(@RequestBody Map<String,String> map1) {
		int lim = Integer.parseInt(map1.get("limit"));
		int start = (Integer.parseInt(map1.get("page")) - 1) * lim;
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("pagesize", lim);
		List<Company> allCompany = companyService.findAllCompany(map);
		int total = companyService.companyCount();
		Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total, allCompany);
		return JSON.toJSONString(l);
		//return new JsonResult<>(HttpStatus.HTTP_OK,allCompany);
	}

	/*获取承运商列表（除了密码）*/
	@GetMapping(value = "/v1/companies/simple")
	@ResponseBody
	public String getCompanySimpleInfo(@RequestParam("limit") String limit, @RequestParam("page") String page) {
//		int lim = Integer.parseInt(map1.get("limit"));
//		int start = (Integer.parseInt(map1.get("page")) - 1) * lim;
		int lim = Integer.parseInt(limit);
		int start = (Integer.parseInt(page) - 1) * lim;
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("pagesize", lim);
		List<Company> allCompany = companyService.findAllCompany(map);
		List<Company> company = new ArrayList<>();
		for(int i = 0; i< allCompany.size(); i++) {
			String coid = allCompany.get(i).getCoid();
			String coName = allCompany.get(i).getCoName();
			String phone = allCompany.get(i).getPhone();
			company.add(new Company(coid,coName,phone));
		}
		int total = companyService.companyCount();
		System.out.println(total);
		Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total, company);
		return JSON.toJSONString(l);
		//return new JsonResult<>(HttpStatus.HTTP_OK,company);
	}


	@ApiOperation("根据名称获取承运商公司的信息")
	
	@ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对") })

	@GetMapping("/v1/companies/coName/{coName}")
	@ResponseBody
	public String getCompanyByName(@PathVariable("coName") String coName, @RequestParam("limit") String limit, @RequestParam("page") String page) {
//		int lim = Integer.parseInt(map1.get("limit"));
//		int start = (Integer.parseInt(map1.get("page")) - 1) * lim;
		int lim = Integer.parseInt(limit);
		int start = (Integer.parseInt(page) - 1) * lim;
		if (coName.equals("")) {
			Map<String, Object> map = new HashMap<>();
			map.put("start", start);
			map.put("pagesize", lim);
			List<Company> companyList = companyService.findAllCompany(map);
			int total = companyService.companyCount();
			Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total, companyList);
			return JSON.toJSONString(l);
			//return new JsonResult<>(HttpStatus.HTTP_OK,companyList);
		} else {
			List<Company> companyList = companyService.findCompanyByName(coName, start, lim);
			int total = companyList.size();
			Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total, companyList);
			System.out.println("承运商信息："+JSON.toJSONString(l));
			return JSON.toJSONString(l);
			//return new JsonResult<>(HttpStatus.HTTP_OK,companyList);
		}
	}

	/*删除多个承运商*/
	@DeleteMapping("/v1/companies")
	@ResponseBody
	public JsonResult<Object> deleteCompanys(@RequestParam("nums") String datas) {
		//String datas = map1.get("nums").toString();
		System.out.println(datas);
		String[] str = datas.split(",");
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			data.add(str[i]);
		}

		System.out.println(data.toString());
		if (companyService.deleteByForeach(data) > 0) {
			return new JsonResult<>(HttpStatus.HTTP_NO_CONTENT,"success");
		} else {
			return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"fail");
		}
	}

	/*删除单个承运商*/
	@DeleteMapping("/v1/companies/{coid}")
	@ResponseBody
	public JsonResult<Object> deleteCompany(@PathVariable("coid") String coid) {
		if (companyService.deleteCompany(coid) > 0) {
			return new JsonResult<>(HttpStatus.HTTP_NO_CONTENT,"success");
		} else {
			return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"fail");
		}
	}

	/*使用coid获取对应承运商信息*/
	@GetMapping("/v1/companies/coid/{coid}")
	@ResponseBody
	public String getCompanyByNum(@PathVariable("coid") String coid) {
		String CompanyNo = coid.toString();
		List<Company> companyList = new ArrayList<>();
		companyList = companyService.getCompanyByNum(CompanyNo);
		int total = companyList.size();
		Layui l = Layui.data(String.valueOf(HttpStatus.HTTP_OK),total, companyList);
		System.out.println(coid);
		System.out.println("getCompanyByNum---->" + JSON.toJSONString(l));
		return JSON.toJSONString(l);
		//return new JsonResult<>(HttpStatus.HTTP_OK,companyList);
	}

	/*更新承运商信息*/
	@PutMapping("/v1/companies")
	@ResponseBody
//	public JsonResult<Object> updateCompany(@RequestParam("coid")String coid, @RequestParam("coName")String coName, @RequestParam("psw")String psw, @RequestParam("phone")String phone, @RequestParam("oldNum")String oldNum) {
	public JsonResult<Object> updateCompany(@RequestBody Map<String,String> map) {//		System.out.println("Company psw:"+map.get("psw"));
//		map.put("psw", SecureUtil.md5(map.get("psw").toString()));
//		Map<String, String> map = new HashMap<>();
//		if(!coid.equals("")){
//			map.put("coid",coid);
//		}
//		if(!coName.equals("")){
//			map.put("coName",coName);
//		}
//		if(!psw.equals("")){
//			map.put("psw", SecureUtil.md5(psw.toString()));
//		}
//		if(!phone.equals("")){
//			map.put("phone",phone);
//		}
//		if(oldNum.equals("")){
//			return new JsonResult<>(HttpStatus.HTTP_BAD_REQUEST,"参数oldNum不合法");
//		}
//		map.put("oldNum",oldNum);



		if(companyService.updateCompany(map)>0){
			return new JsonResult<>(HttpStatus.HTTP_OK,"success");
		}else{
			return new JsonResult<>(HttpStatus.HTTP_INTERNAL_ERROR,"fail");
		}
	}


}
