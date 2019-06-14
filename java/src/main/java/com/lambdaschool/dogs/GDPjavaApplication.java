package com.lambdaschool.dogs;


import com.lambdaschool.dogs.model.GDPList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableScheduling
@EnableWebMvc
@SpringBootApplication
public class GDPjavaApplication
{

    public static GDPList gdpList;
    public static void main(String[] args)
    {
        gdpList = new GDPList();

        ApplicationContext ctx = SpringApplication.run(GDPjavaApplication.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}

