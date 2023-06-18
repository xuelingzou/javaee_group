package com.zhengyuan.liunao.repository;

import cn.hutool.crypto.SecureUtil;
import com.zhengyuan.liunao.entity.Admin;
import com.zhengyuan.liunao.entity.Client;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientMapperTest extends TestCase {
    @Autowired
    ClientMapper clientMapper;

    @Test
    public void testFindClient() {
        // 成功找到客户的测试用例
        List<Client> list = clientMapper.findClient("20301155", SecureUtil.md5("20301155"));
        assertEquals(1,list.size());

        // 未找到客户的测试用例（密码psw错误）
        List<Client> list1 = clientMapper.findClient("20301155",SecureUtil.md5("admin123"));
        assertEquals(0,list1.size());

        // 未找到客户的测试用例（用户名ceid错误）
        List<Client> list2 = clientMapper.findClient("admin123",SecureUtil.md5("20301155"));
        assertEquals(0,list2.size());
    }

    @Test
    public void testAddClient() {
        // 注册客户测试用例
        Map<String,String> map = new HashMap<>();
        map.put("ceid","20301155");
        map.put("ceName","雪玲");
        map.put("psw", SecureUtil.md5("20301155"));
        map.put("phone","18999169606");
        int num = clientMapper.addClient(map);
        assertEquals(1,num);

        Map<String,String> map1 = new HashMap<>();
        map1.put("ceid","20301154");
        map1.put("ceName","小猪");
        map1.put("psw", SecureUtil.md5("20301154"));
        map1.put("phone","13999928456");
        int num1 = clientMapper.addClient(map1);
        assertEquals(1,num1);
    }

    @Test
    public void testFindAllClient() {
        // 返回全部客户信息测试用例
        Map<String, Object> map = new HashMap<>();
        map.put("pagesize", 2);
        map.put("start",0);
        List<Client> list = clientMapper.findAllClient(map);
        assertEquals(1,list.size());
        assertEquals("20301155",list.get(0).getCeid());
        assertEquals(SecureUtil.md5("20301155"),list.get(0).getPsw());
        assertEquals("雪玲",list.get(0).getCeName());
        assertEquals("18999169606",list.get(0).getPhone());
    }

    @Test
    public void testFindClientByName() {
        // 成功根据客户姓名返回客户信息测试用例
        List<Client> list = clientMapper.findClientByName("雪玲",0,2);
        assertEquals(1,list.size());
        assertEquals("20301155",list.get(0).getCeid());
        assertEquals(SecureUtil.md5("20301155"),list.get(0).getPsw());
        assertEquals("18999169606",list.get(0).getPhone());

        // 未成功根据客户姓名返回客户信息测试用例（输入客户姓名不正确）
        List<Client> list1 = clientMapper.findClientByName("雪玲123",0,5);
        assertEquals(0,list1.size());
    }

    @Test
    public void testClientCount() {
        // 返回client表中客户数量测试用例
        int num = clientMapper.ClientCount();
        assertEquals(1,num);
    }

    @Test
    public void testFindClientByNum() {
        // 成功根据客户id返回客户信息测试用例
        List<Client> list = clientMapper.findClientByNum("20301155");
        assertEquals(1,list.size());
        assertEquals("雪玲",list.get(0).getCeName());
        assertEquals(SecureUtil.md5("20301155"),list.get(0).getPsw());
        assertEquals("18999169606",list.get(0).getPhone());

        // 未成功根据客户id返回客户信息测试用例（输入客户id不正确）
        List<Client> list1 = clientMapper.findClientByNum("20300001");
        assertEquals(0,list1.size());
    }

    @Test
    public void testUpdateClient() {
        // 成功修改客户信息测试用例
        Map<String,String> map = new HashMap<>();
        map.put("oldNum","20301155");
        map.put("ceName","雪玲sherlock");
        map.put("psw", SecureUtil.md5("123456"));
        map.put("phone","13579220022");
        int num = clientMapper.updateClient(map);
        assertEquals(1,num);

        // 失败修改客户信息测试用例（因为原客户ceid不存在20301001）
        Map<String,String> map1 = new HashMap<>();
        map1.put("oldNum","20301001");
        map1.put("ceName","雪玲123");
        map1.put("psw", SecureUtil.md5("123456"));
        map1.put("phone","13579220022");
        int num1 = clientMapper.updateClient(map1);
        assertEquals(0,num1);
    }

    @Test
    public void testDeleteByForeach(){
        // 根据客户id的list删除所选全部客户信息测试用例
        List<String> list = new ArrayList<>();
        list.add("20301155");
        list.add("20301154");
        list.add("20301153");
        int num = clientMapper.deleteByForeach(list);
        assertEquals(2,num);
    }

    @Test
    public void testDeleteClient(){
        // 根据客户id删除一条客户信息测试用例
        int num = clientMapper.deleteClient("20301154");
        assertEquals(1,num);

        // 根据客户id删除一条客户信息失败测试用例（输入的客户id：20301001在数据库中不存在）
        int num1 = clientMapper.deleteClient("20301001");
        assertEquals(0,num1);

    }
}