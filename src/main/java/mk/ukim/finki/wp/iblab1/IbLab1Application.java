package mk.ukim.finki.wp.iblab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class IbLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(IbLab1Application.class, args);
    }

}
