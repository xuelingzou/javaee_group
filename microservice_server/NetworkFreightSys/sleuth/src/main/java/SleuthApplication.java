import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableZipkinServer
public class SleuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SleuthApplication.class,args);
    }
}
