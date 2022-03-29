package com.sd4.controller;

import java.util.List;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/beer")
public class BeerController {


    @Autowired
    BeerRepository beerRepository;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Beer> GetBeers() {
        return beerRepository.findAll(Sort.by("name").ascending());    
    }

    @RequestMapping(value="/{beerId}", method=RequestMethod.GET)
    public Beer GetBeer(@PathVariable(value = "beerId") Long id){
        return beerRepository.findById(id).get();
        }
    
    @RequestMapping(value="/{beerId}", method=RequestMethod.PUT)
    public Beer readBeers(@PathVariable(value = "beerID") Long id, @RequestBody Beer beerDetails) {
        return beerRepository.save(beerDetails);
    }
    
    @RequestMapping(value="/{beerId}", method=RequestMethod.DELETE)
    public void deleteBeers(@PathVariable(value = "beerID") Long id) {
        beerRepository.deleteById(id);
    }
}