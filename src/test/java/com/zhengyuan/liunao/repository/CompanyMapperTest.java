package com.zhengyuan.liunao.repository;

import cn.hutool.crypto.SecureUtil;
import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.entity.Company;
import com.zhengyuan.liunao.entity.Company;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyMapperTest extends TestCase {

    @Autowired
    CompanyMapper companyMapper;

    @Test
    public void testAddCompany() {
        // 注册承运商公司测试用例
        Map<String,String> map = new HashMap<>();
        map.put("coid","123456");
        map.put("coName","百度");
        map.put("psw", SecureUtil.md5("123456"));
        map.put("phone","13899828966");
        int num = companyMapper.addCompany(map);
        assertEquals(1,num);

        Map<String,String> map1 = new HashMap<>();
        map1.put("coid","456789");
        map1.put("coName","bilibili");
        map1.put("psw", SecureUtil.md5("456789"));
        map1.put("phone","6652322");
        int num1 = companyMapper.addCompany(map1);
        assertEquals(1,num1);
    }

    @Test
    public void testFindCompany() {
        // 成功找到承运商的测试用例
        List<Company> list = companyMapper.findCompany("123456", SecureUtil.md5("123456"));
        assertEquals(1,list.size());

        // 未找到承运商的测试用例（密码psw错误）
        List<Company> list1 = companyMapper.findCompany("123456",SecureUtil.md5("admin123"));
        assertEquals(0,list1.size());

        // 未找到承运商的测试用例（用户名coid错误）
        List<Company> list2 = companyMapper.findCompany("admin123",SecureUtil.md5("123456"));
        assertEquals(0,list2.size());
    }

    @Test
    public void testFindAllCompany() {
        // 返回全部承运商信息测试用例，按页面显示记录数分页
        Map<String, Object> map = new HashMap<>();
        map.put("pagesize", 2);
        map.put("start",0);
        List<Company> list = companyMapper.findAllCompany(map);
        assertEquals(2,list.size());
        assertEquals("123456",list.get(0).getCoid());
        assertEquals(SecureUtil.md5("123456"),list.get(0).getPsw());
        assertEquals("百度",list.get(0).getCoName());
        assertEquals("13899828966",list.get(0).getPhone());
    }

    @Test
    public void testFindAll() {
        // 返回全部承运商信息测试用例
        List<Company> list = companyMapper.findAll();
        assertEquals(2,list.size());
        assertEquals("123456",list.get(0).getCoid());
        assertEquals(SecureUtil.md5("123456"),list.get(0).getPsw());
        assertEquals("百度",list.get(0).getCoName());
        assertEquals("13899828966",list.get(0).getPhone());
    }

    @Test
    public void testFindCompanyByName() {
        // 成功根据承运商姓名返回承运商信息测试用例
        List<Company> list = companyMapper.findCompanyByName("百度",0,2);
        assertEquals(1,list.size());
        assertEquals("123456",list.get(0).getCoid());
        assertEquals(SecureUtil.md5("123456"),list.get(0).getPsw());
        assertEquals("13899828966",list.get(0).getPhone());

        // 未成功根据承运商姓名返回承运商信息测试用例（输入承运商姓名不正确）
        List<Company> list1 = companyMapper.findCompanyByName("雪玲123",0,5);
        assertEquals(0,list1.size());
    }

    @Test
    public void testCompanyCount() {
        // 返回Company表中承运商数量测试用例
        int num = companyMapper.companyCount();
        assertEquals(2,num);
    }

    @Test
    public void testDeleteByForeach() {
        // 根据承运商id的list删除所选全部承运商信息测试用例
        List<String> list = new ArrayList<>();
        list.add("123456");
        list.add("456789");
        list.add("20301153");
        int num = companyMapper.deleteByForeach(list);
        assertEquals(2,num);
    }

    @Test
    public void testDeleteCompany() {
        // 根据承运商id删除一条承运商信息测试用例
        int num = companyMapper.deleteCompany("123456");
        assertEquals(1,num);

        // 根据承运商id删除一条承运商信息失败测试用例（输入的承运商id：20301001在数据库中不存在）
        int num1 = companyMapper.deleteCompany("20301001");
        assertEquals(0,num1);
    }

    @Test
    public void testGetCompanyByNum() {
        // 成功根据承运商id返回承运商信息测试用例
        List<Company> list = companyMapper.getCompanyByNum("456789");
        assertEquals(1,list.size());
        assertEquals("bilibili",list.get(0).getCoName());
        assertEquals(SecureUtil.md5("456789"),list.get(0).getPsw());
        assertEquals("6652322",list.get(0).getPhone());

        // 未成功根据承运商id返回承运商信息测试用例（输入承运商id不正确）
        List<Company> list1 = companyMapper.getCompanyByNum("20300001");
        assertEquals(0,list1.size());
    }

    @Test
    public void testUpdateCompany() {
        // 成功修改承运商信息测试用例
        Map<String,String> map = new HashMap<>();
        map.put("oldNum","123456");
        map.put("coName","百度公司");
        map.put("psw", SecureUtil.md5("020604"));
        map.put("phone","13579220022");
        int num = companyMapper.updateCompany(map);
        assertEquals(1,num);

        // 失败修改承运商信息测试用例（因为原承运商coid不存在20301001）
        Map<String,String> map1 = new HashMap<>();
        map1.put("oldNum","20301001");
        map1.put("coName","雪玲123");
        map1.put("psw", SecureUtil.md5("123456"));
        map1.put("phone","13579220022");
        int num1 = companyMapper.updateCompany(map1);
        assertEquals(0,num1);
    }
}