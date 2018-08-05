package mintfrost.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    @RequestMapping("/test")
    public String test(@RequestParam("user") String user) {
        return "Testing 1,2,3...<br/>Hello " + user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
