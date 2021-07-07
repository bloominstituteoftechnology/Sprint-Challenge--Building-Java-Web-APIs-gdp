package com.lambdaschool.gdp.model

import java.util.concurrent.atomic.AtomicInteger

class GDP {
    private var iID: Int = 0
    var strName: String? = null
    private var lGDP: Long = 0

    constructor(strName: String, iGDP: Int) {
        this.iID = iCounter.incrementAndGet()
        this.strName = strName
        this.lGDP = iGDP.toLong()
    }

    constructor(strName: String, lGDP: Long) {
        this.iID = iCounter.incrementAndGet()
        this.strName = strName
        this.lGDP = lGDP
    }


    constructor(strName: String, strGDP: String) {
        this.iID = iCounter.incrementAndGet()
        this.strName = strName
        this.lGDP = Integer.parseInt(strGDP).toLong()
    }

    fun getiID(): Int {
        return iID
    }

    fun getlGDP(): Long {
        return lGDP
    }

    fun setlGDP(lGDP: Int) {
        this.lGDP = lGDP.toLong()
    }

    companion object {
        private val iCounter = AtomicInteger()
    }
}