package com.zhengyuan.liunao.controller.dealcontroller;

import java.util.*;

import com.zhengyuan.liunao.entity.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhengyuan.liunao.service.IncomeService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Sys")
public class FinanceController {

	@Autowired
	IncomeService incomeService;

	
	@RequestMapping("/getGscomp")
	@ResponseBody
	public String getGscomp(HttpSession httpSession, @RequestParam("year") String yearStr) {
		String coid = (String) httpSession.getAttribute( "account");
		int year = Integer.parseInt(yearStr);
		List<Income> incomes = incomeService.selectMonthIncome(coid,year*100);
		Map<String, Double> map = new HashMap<>();

		for(Income income:incomes){
			map.put(String.valueOf(income.getYearMonth()),income.getIncome());
		}


	    double[] scores = new double[12];
		for(int i=0;i<12;i++){
			if(map.get(String.valueOf(year*100+i+1)) !=null){
				scores[i] = map.get(String.valueOf(year*100+i+1));
			}else {
				scores[i] =0;
			}

		}

	    String jsonString = JSON.toJSONString(scores);
	    System.out.println(jsonString);
		return jsonString;
	}



}
