package com.lambdaschool.gdp


import com.lambdaschool.gdp.model.GDP

interface CheckGDP {
    fun test(d: GDP): Boolean
    companion object
    {
        inline operator fun invoke(crossinline op: (gdp: GDP) -> Boolean) =
                object : CheckGDP
                {
                    override fun test(gdp: GDP): Boolean = op(gdp)
                }
    }
}
