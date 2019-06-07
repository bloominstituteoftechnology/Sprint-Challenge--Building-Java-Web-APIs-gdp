package com.jakeesveld.gdp

import java.util.concurrent.atomic.AtomicLong

data class GDP(val name: String, val gdp: String){
    companion object{
        private val counter = AtomicLong()
    }
    var id: Long = counter.incrementAndGet()
}