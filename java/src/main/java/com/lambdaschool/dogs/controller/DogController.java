package com.lambdaschool.dogs.controller;

import com.lambdaschool.dogs.GDPjavaApplication;
import com.lambdaschool.dogs.model.GDP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/countries")
public class DogController
{
    // localhost:2019/data/gdp
    @GetMapping(value = "/gdp")
    public ResponseEntity<?> getAllGDP()
    {
        GDPjavaApplication.gdpList.gdpList.sort((e1, e2) -> e1.getStrName().compareToIgnoreCase(e2.getStrName()));

        return new ResponseEntity<>(GDPjavaApplication.gdpList.gdpList, HttpStatus.OK);
    }

    // localhost:2019/data/gdp/{id}
    @GetMapping(value = "/gdp/{id}")
    public ResponseEntity<?> getGDPbyID(@PathVariable int id)
    {
        GDP rtnGDP = GDPjavaApplication.gdpList.findGDP(d -> (d.getiID() == id));
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }

    // localhost:2019/gdp/gdp/{gdp}
    @GetMapping(value = "/gdp/{gdp}")
    public ResponseEntity<?> getDogBreeds (@PathVariable long lGDP)
    {
      //  int iGDP=Integer.parseInt(strGDP);
        ArrayList<GDP> rtnGDP = GDPjavaApplication.gdpList.findListOfGDP(d -> d.getlGDP()==lGDP);
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }
}
