package com.lambdaschool.gdp.controller;

import com.lambdaschool.gdp.GDPjavaApplication;
import com.lambdaschool.gdp.model.GDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/gdp")
public class GDPController
{
    private static final Logger logger = LoggerFactory.getLogger(GDPController.class);
    // localhost:2019/data/dgp/names
    //  - return using the JSON format all of the countries alphabetized by name
    @GetMapping(value = "/name")
    public ResponseEntity<?> getAllGDP(HttpServletRequest request)
    {

 //       Custom logging under each Get endpoint saying the endpoint has been accessed

   //    should only go to console
     //   for example when a client calls /names log should say "/names accessed"
       // in the log entry include any parameters that were sent to the endpoint
      //  in the log entry include the date and timestamp of the access of the endpoint
        //You are not to log access to the server side rendering pages.
        logger.info("/names accessed");
        logger.trace(request.getRequestURI() + " accessed");
        GDPjavaApplication.gdpList.gdpList.sort((e1, e2) -> e1.getStrName().compareToIgnoreCase(e2.getStrName()));


        return new ResponseEntity<>(GDPjavaApplication.gdpList.gdpList, HttpStatus.OK);
    }

    // localhost:2019/data/gdp/economy
    // - return using the JSON format all of the countries sorted from most to least GDP
    //for sorting you might have to type cast so .sort((c1, c2) -> (int)(c2.getGDP() - c1.getGDP()))
    @GetMapping(value = "/economy")
    public ResponseEntity<?> getEconomyFromGreater()
    {
        logger.info("/economy accessed");
        GDPjavaApplication.gdpList.gdpList.sort((e1, e2) -> (int)(e2.getlGDP() - e1.getlGDP()));
        return new ResponseEntity<>(GDPjavaApplication.gdpList.gdpList, HttpStatus.OK);
    }


    // localhost:2019/data/gdp/country/{id} - return using the JSON format a single country and GDP based off of its id number
    @GetMapping(value = "/country/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> getCountrybyID(HttpServletRequest request,
            @PathVariable
                    long id)
    {
        logger.info("/country/{id} accessed");
        logger.trace(request.getRequestURI() + " accessed");

        GDP rtnCountry  = GDPjavaApplication.gdpList.findGDP(e -> (e.getiID() == id));
        return new ResponseEntity<>(rtnCountry , HttpStatus.OK);
    }

    // localhost:2019/data/gdp/country/stats/median
    // - return using the JSON the country and its GDP with the median GDP.
    // For odd number list, return the the country in the middle.
    // For even number list you may return either one of the countries found in the middle.
   @GetMapping(value = "/country/stats/median",
           produces = {"application/json"})
   public ResponseEntity<?> getMedian() {
       logger.info("country/stats/median accessed");
       GDPjavaApplication.gdpList.gdpList.sort((e1, e2) -> (int)(e2.getlGDP() - e1.getlGDP()));
       int iNumberofCountries=GDPjavaApplication.gdpList.gdpList.size();
       if(iNumberofCountries%2==0){
           ArrayList<GDP> rtnCountries  = new ArrayList<>();
           rtnCountries.add(GDPjavaApplication.gdpList.findGDP(e -> (e.getiID() == iNumberofCountries/2)));
           rtnCountries.add(GDPjavaApplication.gdpList.findGDP(e -> (e.getiID() == iNumberofCountries/2-1)));

           return new ResponseEntity<>(rtnCountries , HttpStatus.OK);
       }else{
           GDP rtnCountry  = GDPjavaApplication.gdpList.findGDP(e -> (e.getiID() == iNumberofCountries/2));
           return new ResponseEntity<>(rtnCountry , HttpStatus.OK);
       }

   }

    // localhost:2019/data/gdp/economy/table
    // Create server side rendering pages using Thymeleaf to
    //    - display a table list all countries sorted from most to least GDP
    @GetMapping(value = "/economy/table")
    public ModelAndView getGDPtable () {
        logger.info("/economy/table accessed");
        GDPjavaApplication.gdpList.gdpList.sort((e1, e2) -> (int) (e2.getlGDP() - e1.getlGDP()));
        ArrayList<GDP> rtnGDP = GDPjavaApplication.gdpList.gdpList;
        ModelAndView mav = new ModelAndView();
        mav.setViewName("gdptable");
        mav.addObject("gdpList", rtnGDP);
        return mav;

    }

    // localhost:2019/data/gdp/country/stats/total
    // - return the sum of all GDPs using the JSON format with country name being returned as Total/total
    @GetMapping(value = "/country/stats/total",
            produces = {"application/json"})
    public ResponseEntity<?> getTotal() {
        logger.info("country/stats/total accessed");
        GDPjavaApplication.gdpList.total();
            GDP rtnCountry  = new GDP("Total/total", (int) GDPjavaApplication.gdpList.total());
            return new ResponseEntity<>(rtnCountry , HttpStatus.OK);
        }

    //Create the server side html pages
    // localhost:2019/data/gdp/names/{start letter}/{end letter}
    // - display a table listing all countries in alphabetical order
    // that begin with letters between start and end letter inclusive.
    @GetMapping(value = "/names/{cStart}/{cEnd}",
            produces = {"application/json"})
    public ModelAndView getFindNameBetween(HttpServletRequest request
            , @PathVariable  char cStart
            , @PathVariable char cEnd)
    {
        logger.info("/names/{cStart}/{cEnd}");
        logger.trace(request.getRequestURI() + " accessed");
        ArrayList<GDP> rtnGDP = GDPjavaApplication.gdpList.getListNameBetween(cStart,cEnd);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("gdptable");
        mav.addObject("gdpList", rtnGDP);
        return mav;
    }
    //    /gdp/list/{start gdp}/{end gdp}
    //    - display a table listing all countries order
    //    by GDP from least to greatest where the country's GDP lies
    //    between start gdp and end gdp inclusive.





}




//Note: put the log files under the directory /tmp/var/logs/lambdajx Feel free to create and necessary subdirectories.
