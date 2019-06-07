package com.jakeesveld.gdp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/gdp"])
class GdpController{

    @GetMapping(value = ["/names"])
    fun getGdpNamesAlphabetized(): ResponseEntity<Any>{
        return ResponseEntity(GdpApplication.gdpList.sortBy { x -> x.name }, HttpStatus.OK)
    }

    @GetMapping(value = ["/economy"])
    fun getGdpByEconomy(): ResponseEntity<Any>{
        return ResponseEntity(GdpApplication.gdpList.sortBy { x -> x.gdp }, HttpStatus.OK)
    }

    @GetMapping(value = ["/country/{id}"])
    fun getGdpById(@PathVariable id: Long):ResponseEntity<Any>{
        return ResponseEntity(GdpApplication.gdpList.filter { x -> x.id == id }, HttpStatus.OK)
    }

    @GetMapping(value = ["/country/stats/median"])
    fun getMedianGdp(): ResponseEntity<Any>{
        val tempList = GdpApplication.gdpList.sortedBy { x -> x.gdp }
        return ResponseEntity(tempList[tempList.size / 2], HttpStatus.OK)
    }



}