package com.zhengyuan.liunao.tools;

import java.util.HashMap;
import java.util.List;

public class Layui extends HashMap<String, Object> {
//	public static Layui data(Integer code,String msg,Integer count, List<?> data) {
	public static Layui data(String code,Integer count, List<?> data) {
		Layui r = new Layui();
		r.put("code", 0);
		r.put("msg", code);
		r.put("count", count);
		r.put("data", data);
		return r;
	}
}
