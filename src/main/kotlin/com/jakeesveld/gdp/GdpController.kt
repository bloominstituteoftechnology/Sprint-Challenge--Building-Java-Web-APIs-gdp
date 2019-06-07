package com.jakeesveld.gdp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping(value = ["/gdp"])
class GdpController{

    val logger: Logger = LoggerFactory.getLogger(GdpController::class.java)

    @GetMapping(value = ["/names"])
    fun getGdpNamesAlphabetized(): ResponseEntity<Any>{
        logger.info("/names accessed")
        return ResponseEntity(GdpApplication.getOurGdpList().gdpList.sortedBy { x -> x.name }, HttpStatus.OK)
    }

    @GetMapping(value = ["/economy"])
    fun getGdpByEconomy(): ResponseEntity<Any>{
        logger.info("/economy accessed")
        return ResponseEntity(GdpApplication.getOurGdpList().gdpList.sortedByDescending { x -> x.gdp.toLong() }, HttpStatus.OK)
    }

    @GetMapping(value = ["/country/{id}"])
    fun getGdpById(@PathVariable id: Long):ResponseEntity<Any>{
        logger.info("/country/$id accessed")
        val found = GdpApplication.getOurGdpList().gdpList.filter { x -> x.id == id }
        if(found.isEmpty()){
            throw ResourceNotFoundException(message = "Count not find country with id $id", cause = null)
        }
        return ResponseEntity(found, HttpStatus.OK)
    }

    @GetMapping(value = ["/country/stats/median"])
    fun getMedianGdp(): ResponseEntity<Any>{
        logger.info("/country/stats/median accessed")
        return ResponseEntity(GdpApplication.getOurGdpList().gdpList.sortedBy
        { x -> x.gdp.toLong() }.get(GdpApplication.getOurGdpList().gdpList.size / 2), HttpStatus.OK)
    }

    @GetMapping(value = ["/economy/table"])
    fun getEconomyTable(): ModelAndView{
        val mav = ModelAndView()
        mav.viewName = "gdps"
        mav.addObject("countryList" , GdpApplication.getOurGdpList().gdpList.sortedByDescending { x -> x.gdp.toLong() })

        return mav
    }

    @GetMapping(value = ["/total"])
    fun getTotalGDP(): ResponseEntity<Any>{
        logger.info("/total accessed")
        return ResponseEntity(GDP("total", GdpApplication.getOurGdpList().getTotal().toString()), HttpStatus.OK)
    }

    @GetMapping(value = ["/names/{start}/{end}"])
    fun getTableStartEndLetter(@PathVariable start: Char, @PathVariable end: Char): ResponseEntity<Any>{
        return ResponseEntity(GdpApplication.getOurGdpList().gdpList.filter
        { x -> x.name.toCharArray()[0] in start..end }.sortedBy { x -> x.name }, HttpStatus.OK)
    }


}