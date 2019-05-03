package com.lambdaschool.coutrygdp.controller;

import com.lambdaschool.coutrygdp.CoutrygdpApplication;
import com.lambdaschool.coutrygdp.exceptions.NotFound;
import com.lambdaschool.coutrygdp.model.GDP;
//import com.lambdaschool.projectrestdogs.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class gdpController {
    private static final Logger logger = LoggerFactory.getLogger(GDP.class);

    @GetMapping(value = "/gdp")
    public ResponseEntity<?> getAllGDPs()
    {
        logger.info("all the damn gdps your beautiful heart could imagine");
        return new ResponseEntity<>(CoutrygdpApplication.ourGDPList.gdpList, HttpStatus.OK);
    }

    @GetMapping(value = "/gdp/{id}")
    public ResponseEntity<?> getGDPDetail(@PathVariable long id)
    {
        GDP rtnGDP;
        logger.info("you're looking at gdp - " + id + " you filthy animal");

        if ((CoutrygdpApplication.ourGDPList.findGDP(e -> (e.getId()) == id)) == null) {

            throw new NotFound(id + "ain't no gdp silly");

        } else {

            rtnGDP = CoutrygdpApplication.ourGDPList.findGDP(e -> (e.getId() == id));
        }
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }


    // localhost:2019/dog/table
    @GetMapping(value = "/")
    public ModelAndView displayDogTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("gdps");
        mav.addObject("gdpList", CoutrygdpApplication.ourGDPList.gdpList);
        return mav;
    }

}
