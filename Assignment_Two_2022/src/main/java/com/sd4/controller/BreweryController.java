package com.sd4.controller;

import java.util.List;

import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/Brewery")

public class BreweryController {


    @Autowired
    BreweryRepository breweryRepository;

    @RequestMapping(value="/brewery", method=RequestMethod.GET)
    public List<Brewery> readBrewerys() {
        return breweryRepository.findAll(Sort.by("name").ascending());    
    }

    @RequestMapping(value="/{breweryId}", method=RequestMethod.GET)
    public Brewery GetBeer(@PathVariable(value = "breweryId") Long id){
        return breweryRepository.findById(id).get();
        }
    
    @RequestMapping(value="/{breweryId}", method=RequestMethod.PUT)
    public Brewery readBrewerys(@PathVariable(value = "breweryID") Long id, @RequestBody Brewery breweryDetails) {
        return breweryRepository.save(breweryDetails);
    }
    
    @RequestMapping(value="/brewerys/{breweryId}", method=RequestMethod.DELETE)
    public void deleteBrewerys(@PathVariable(value = "breweryID") Long id) {
        breweryRepository.deleteById(id);
    }
}