package com.example.auto_warehouse.circuitBreaker;

public interface DeptService {

        // hystrix 熔断器示例 ok
        public String deptInfo_Ok(Integer id);

        //hystrix 熔断器超时案例
        public String deptInfo_Timeout(Integer id);

        // Hystrix 熔断机制案例
        public String deptCircuitBreaker(Integer id);
}
