package com.zhengyuan.liunao.controller.dealcontroller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginResgisterControllerTest extends TestCase {
//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void before() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }

    @Autowired
    LoginResgisterController loginResgisterController;

    @Test
    public void testGetInfo() {
//        // 构建HTTP GET请求
//        MvcResult mvcResult = mockMvc.perform(get("/user?username=test")).andExpect(status().isOk()).andReturn();
//
//        // 获取HTTP响应的状态码
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//
//        // 获取HTTP响应的内容
//        String result = mvcResult.getResponse().getContentAsString();
//        System.out.println(result);
        HttpSession httpSession = null;
        HttpSession httpSession1 = null;
        HttpSession httpSession2 = null;
        String result = loginResgisterController.getInfo("admin","admin","0",httpSession);
        assertEquals("true",result);

        String result1 = loginResgisterController.getInfo("20301155","123456","1",httpSession1);
        assertEquals("true",result1);

    }

    @Test
    public void testRegisterCompanyDeal() {
    }

    @Test
    public void testRegisterClientDeal() {
    }
}