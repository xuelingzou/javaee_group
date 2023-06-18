package com.zhengyuan.liunao.repository;

import cn.hutool.crypto.SecureUtil;
import com.zhengyuan.liunao.entity.Income;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class IncomeMapperTest extends TestCase {

    @Autowired
    IncomeMapper incomeMapper;

    @Test
    public void testUpdateIncome() {
        // 成功更新income对应公司月收入测试用例
        int num = incomeMapper.updateIncome(202304,100,"123456");
        assertEquals(1,num);

        // 失败更新income对应公司月收入测试用例（因为原公司id不存在999）
        int num1 = incomeMapper.updateIncome(202304,100,"999");
        assertEquals(0,num1);
    }

    @Test
    public void testInsertIncome() {
        // 添加income月收入记录测试用例
        Income income = new Income("123456",202304,250);
        int num = incomeMapper.insertIncome(income);
        assertEquals(1,num);

        Income income1 = new Income("123456",202303,300);
        int num1 = incomeMapper.insertIncome(income1);
        assertEquals(1,num1);
    }

    @Test
    public void testFindByYearMonth() {
        // 成功根据年月查找income对应公司月收入测试用例
        List<Income> list = incomeMapper.findByYearMonth("123456",202304);
        assertEquals(1,list.size());

        // 失败根据年月查找income对应公司月收入测试用例（因为原公司id不存在999）
        List<Income> list1 = incomeMapper.findByYearMonth("999",202304);
        assertEquals(0,list1.size());
    }

    @Test
    public void testSelectMonthIncome() {
        // 成功根据年查找income对应公司月收入列表测试用例
        List<Income> list = incomeMapper.selectMonthIncome("123456",2023*100);
        assertEquals(2,list.size());

        // 失败根据年查找income对应公司月收入列表测试用例（因为原公司id不存在999）
        List<Income> list1 = incomeMapper.selectMonthIncome("999",2023*100);
        assertEquals(0,list1.size());
    }
}