package com.jakeesveld.gdp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class GdpApplication {


    companion object {
        private lateinit var ourGdpList: GdpList


        @JvmStatic
        fun main(args: Array<String>) {
            ourGdpList = GdpList()
            val ctx = SpringApplication.run(GdpApplication::class.java, *args)

            val dispatcherServlet = ctx.getBean("dispatcherServlet") as DispatcherServlet
            dispatcherServlet.setThrowExceptionIfNoHandlerFound(true)
        }

        fun getOurGdpList() : GdpList = ourGdpList


    }
}
