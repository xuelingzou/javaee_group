package com.example.auto_warehouse.mapper;

import cn.hutool.crypto.SecureUtil;
import com.example.auto_warehouse.bean.Admin;
import com.example.auto_warehouse.mapper.AdminMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminMapperTest extends TestCase {

    @Autowired
    AdminMapper adminMapper;

    @Test
    public void testFindAdmin() {
        // 成功找到admin的测试用例
        List<Admin> list = adminMapper.findAdmin("admin", SecureUtil.md5("admin"));
        assertEquals(1,list.size());

        // 未找到admin的测试用例（密码psw错误）
        List<Admin> list1 = adminMapper.findAdmin("admin",SecureUtil.md5("admin123"));
        assertEquals(0,list1.size());

        // 未找到admin的测试用例（用户名account错误）
        List<Admin> list2 = adminMapper.findAdmin("admin123",SecureUtil.md5("admin"));
        assertEquals(0,list2.size());
    }
}