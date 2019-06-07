package com.jakeesveld.gdp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GdpApplication {

    companion object{
        private lateinit var gdpList: MutableList<GDP>
    }

    fun main(args: Array<String>) {
        gdpList = GdpList().gdpList
        runApplication<GdpApplication>(*args)
    }
}
