package com.lambdaschool.gdp.controller

import com.lambdaschool.gdp.GDPjavaApplication
import com.lambdaschool.gdp.model.GDP
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import java.util.ArrayList

@RestController
@RequestMapping("/gdp")
class GDPController {

    // localhost:2019/data/gdp/economy
    // - return using the JSON format all of the countries sorted from most to least GDP
    //for sorting you might have to type cast so .sort((c1, c2) -> (int)(c2.getGDP() - c1.getGDP()))
    val economyFromGreater: ResponseEntity<*>
        @GetMapping(value = "/economy")
        get() {
            logger.info("/economy accessed")
            GDPjavaApplication.gdpList.gdpList.sort { e1, e2 -> (e2.getlGDP() - e1.getlGDP()).toInt() }
            return ResponseEntity(GDPjavaApplication.gdpList.gdpList, HttpStatus.OK)
        }

    // localhost:2019/data/gdp/country/stats/median
    // - return using the JSON the country and its GDP with the median GDP.
    // For odd number list, return the the country in the middle.
    // For even number list you may return either one of the countries found in the middle.
    val median: ResponseEntity<*>
        @GetMapping(value = "/country/stats/median", produces = ["application/json"])
        get() {
            logger.info("country/stats/median accessed")
            GDPjavaApplication.gdpList.gdpList.sort { e1, e2 -> (e2.getlGDP() - e1.getlGDP()).toInt() }
            val iNumberofCountries = GDPjavaApplication.gdpList.gdpList.size
            if (iNumberofCountries % 2 == 0) {
                val rtnCountries = ArrayList<GDP>()
                rtnCountries.add(GDPjavaApplication.gdpList.findGDP({ e -> e.getiID() == iNumberofCountries / 2 }))
                rtnCountries.add(GDPjavaApplication.gdpList.findGDP({ e -> e.getiID() == iNumberofCountries / 2 - 1 }))

                return ResponseEntity(rtnCountries, HttpStatus.OK)
            } else {
                val rtnCountry = GDPjavaApplication.gdpList.findGDP({ e -> e.getiID() == iNumberofCountries / 2 })
                return ResponseEntity(rtnCountry, HttpStatus.OK)
            }

        }

    // localhost:2019/data/gdp/economy/table
    // Create server side rendering pages using Thymeleaf to
    //    - display a table list all countries sorted from most to least GDP
    val gdPtable: ModelAndView
        @GetMapping(value = "/economy/table")
        get() {
            logger.info("/economy/table accessed")
            GDPjavaApplication.gdpList.gdpList.sort { e1, e2 -> (e2.getlGDP() - e1.getlGDP()).toInt() }
            val rtnGDP = GDPjavaApplication.gdpList.gdpList
            val mav = ModelAndView()
            mav.viewName = "gdptable"
            mav.addObject("gdpList", rtnGDP)
            return mav

        }

    // localhost:2019/data/gdp/country/stats/total
    // - return the sum of all GDPs using the JSON format with country name being returned as Total/total
    val total: ResponseEntity<*>
        @GetMapping(value = "/country/stats/total", produces = ["application/json"])
        get() {
            logger.info("country/stats/total accessed")
            GDPjavaApplication.gdpList.total()
            val rtnCountry = GDP("Total/total", GDPjavaApplication.gdpList.total().toInt())
            return ResponseEntity(rtnCountry, HttpStatus.OK)
        }

    // localhost:2019/data/dgp/names
    //  - return using the JSON format all of the countries alphabetized by name
    @GetMapping(value = "/name")
    fun getAllGDP(request: HttpServletRequest): ResponseEntity<*> {

        //       Custom logging under each Get endpoint saying the endpoint has been accessed

        //    should only go to console
        //   for example when a client calls /names log should say "/names accessed"
        // in the log entry include any parameters that were sent to the endpoint
        //  in the log entry include the date and timestamp of the access of the endpoint
        //You are not to log access to the server side rendering pages.
        logger.info("/names accessed")
        logger.trace(request.requestURI + " accessed")
        GDPjavaApplication.gdpList.gdpList.sort { e1, e2 -> e1.strName.compareTo(e2.strName, ignoreCase = true) }


        return ResponseEntity(GDPjavaApplication.gdpList.gdpList, HttpStatus.OK)
    }


    // localhost:2019/data/gdp/country/{id} - return using the JSON format a single country and GDP based off of its id number
    @GetMapping(value = "/country/{id}", produces = ["application/json"])
    fun getCountrybyID(request: HttpServletRequest,
                       @PathVariable
                       id: Long): ResponseEntity<*> {
        logger.info("/country/{id} accessed")
        logger.trace(request.requestURI + " accessed")

        val rtnCountry = GDPjavaApplication.gdpList.findGDP({ e -> e.getiID().toLong() == id })
        return ResponseEntity(rtnCountry, HttpStatus.OK)
    }

    //Create the server side html pages
    // localhost:2019/data/gdp/names/{start letter}/{end letter}
    // - display a table listing all countries in alphabetical order
    // that begin with letters between start and end letter inclusive.
    @GetMapping(value = "/names/{cStart}/{cEnd}", produces = ["application/json"])
    fun getFindNameBetween(request: HttpServletRequest, @PathVariable cStart: Char, @PathVariable cEnd: Char): ModelAndView {
        logger.info("/names/{cStart}/{cEnd}")
        logger.trace(request.requestURI + " accessed")
        val rtnGDP = GDPjavaApplication.gdpList.getListNameBetween(cStart, cEnd)
        rtnGDP.sort { e1, e2 -> e1.strName.compareTo(e2.strName, ignoreCase = true) }
        val mav = ModelAndView()
        mav.viewName = "gdptable"
        mav.addObject("gdpList", rtnGDP)
        return mav
    }
    //    /gdp/list/{start gdp}/{end gdp}
    //    - display a table listing all countries order
    //    by GDP from least to greatest where the country's GDP lies
    //    between start gdp and end gdp inclusive.

    @GetMapping(value = "/list/{lLowest}/{lHighest}", produces = ["application/json"])
    fun getFindGDPBetween(request: HttpServletRequest, @PathVariable lLowest: Long, @PathVariable lHighest: Long): ModelAndView {
        logger.info("/list/{lLowest}/{lHighest}")
        logger.trace(request.requestURI + " accessed")
        GDPjavaApplication.gdpList.gdpList.sort { e1, e2 -> e1.strName.compareTo(e2.strName, ignoreCase = true) }

        val rtnGDP = GDPjavaApplication.gdpList.getListGDPBetween(lLowest, lHighest)
        rtnGDP.sort { e1, e2 -> (e1.getlGDP() - e2.getlGDP()).toInt() }

        val mav = ModelAndView()
        mav.viewName = "gdptable"
        mav.addObject("gdpList", rtnGDP)
        return mav
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GDPController::class.java)
    }


}


//Note: put the log files under the directory /tmp/var/logs/lambdajx Feel free to create and necessary subdirectories.
