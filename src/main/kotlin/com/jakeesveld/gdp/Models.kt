package com.jakeesveld.gdp

import java.sql.Timestamp
import java.util.concurrent.atomic.AtomicLong

data class GDP(val name: String, val gdp: String){
    companion object{
        private val counter = AtomicLong()
    }
    var id: Long = counter.incrementAndGet()
}

class ErrorDetail(val title: String,
                  val status: Int,
                  val detail: String,
                  val timestamp: Timestamp,
                  val developerMessage: String)
