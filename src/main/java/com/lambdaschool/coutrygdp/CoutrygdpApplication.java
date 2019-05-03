package com.lambdaschool.coutrygdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class CoutrygdpApplication {
    public static gdpList ourGDPList;

    public static void main(String[] args) {
        ourGDPList = new gdpList();
        SpringApplication.run(CoutrygdpApplication.class, args);

    }
}



