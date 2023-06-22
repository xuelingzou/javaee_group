package com.example.auto_warehouse.circuitBreaker;



import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Slf4j
public class DeptController {

    @Autowired
    DeptService deptService;


    @Value("${server.port}")
    private String serverPort;

//    Logger log =  Logger.getLogger(String.valueOf(DeptController.class));
    @RequestMapping(value = "/dept/hystrix/ok/{id}")
    public String deptInfo_Ok(@PathVariable("id") Integer id) {
        String result = deptService.deptInfo_Ok(id);
        System.out.println("端口号：" + serverPort + " result:" + result);
        return result + "，   端口号：" + serverPort;
    }
    // Hystrix 服务超时降级
    @RequestMapping(value = "/dept/hystrix/timeout/{id}")
    public String deptInfo_Timeout(@PathVariable("id") Integer id) {
        String result = deptService.deptInfo_Timeout(id);
        System.out.println("端口号：" + serverPort + " result:" + result);
        return result + "，   端口号：" + serverPort;
    }

    // Hystrix 服务熔断
    @RequestMapping(value = "/dept/hystrix/circuit/{id}")
    public String deptCircuitBreaker(@PathVariable("id") Integer id){
        String result = deptService.deptCircuitBreaker(id);

        System.out.println("result:"+result);
        return result;
    }


    @GetMapping("/getAllProduct")
    @HystrixCommand(fallbackMethod = "getAllProductFallBack",
            commandProperties =
            //规定 5 秒钟以内就不报错，正常运行，超过 5 秒就报错，调用指定的方法
            {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    public String getAllProduct(){
        //使用Ribbon请求第一种方式:  我们把地址换成服务id即可
//        String uri="http://EUREKA-SERVICE.PRODUCER/getProducts";
//        String vos = restTemplate.getForObject(uri, String.class);
       try{
           Thread.sleep(2000L);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }

        String vos = "成功！";
        return vos;
    }

    //此方法的参数和返回值必须和对应处理方法一致，一般返回值都使用String，
    // 这样提示信息比较友好
    public String getAllProductFallBack(){
        return "系统繁忙，请稍后重试";
    }

}
