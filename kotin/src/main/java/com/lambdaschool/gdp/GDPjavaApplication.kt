package com.lambdaschool.gdp


import com.lambdaschool.gdp.model.GDPList
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableScheduling
@EnableWebMvc
@SpringBootApplication
object GDPjavaApplication {

    lateinit var gdpList: GDPList
    @JvmStatic
    fun main(args: Array<String>) {
        gdpList = GDPList()

        val ctx = SpringApplication.run(GDPjavaApplication::class.java, *args)
        val dispatcherServlet = ctx.getBean("dispatcherServlet") as DispatcherServlet
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true)
    }
}

