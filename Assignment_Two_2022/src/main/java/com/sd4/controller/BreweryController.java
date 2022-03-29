package com.sd4.controller;

import java.util.List;

import com.google.zxing.WriterException;
import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;
import com.sd4.utilities.QRGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/brewery")

public class BreweryController {


    @Autowired
    BreweryRepository breweryRepository;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Brewery> readBrewerys() {
        return breweryRepository.findAll(Sort.by("name").ascending());    
    }

    @RequestMapping(value="/maps/{breweryId}", method=RequestMethod.GET)
    public Brewery readBreweryMap(@PathVariable(value = "breweryId") Long id) {
        System.out.println("hello");
        Brewery brewery = breweryRepository.findById(id).get();

        return brewery;
    }

    @ResponseBody
    @RequestMapping(value="/qr/{breweryId}", method=RequestMethod.GET)
    public Object readBreweryQR(@PathVariable(value = "breweryId") Long id) throws WriterException {
        System.out.println("hello");
        Brewery brewery = breweryRepository.findById(id).get();
        String vCard = "BEGIN:VCARD";
        vCard += "FN:" + brewery.getName();
        vCard += "TEL:"+ brewery.getCode();
        vCard += "END:VCARD";

        return QRGenerator.GenerateQRCodeImage(vCard);

    }



    @RequestMapping(value="/{breweryId}", method=RequestMethod.GET)
    public Brewery GetBeer(@PathVariable(value = "breweryId") Long id){
        return breweryRepository.findById(id).get();
    }
    
    @RequestMapping(value="/{breweryId}", method=RequestMethod.PUT)
    public Brewery readBrewerys(@PathVariable(value = "breweryID") Long id, @RequestBody Brewery breweryDetails) {
        return breweryRepository.save(breweryDetails);
    }
    
    @RequestMapping(value="/{breweryId}", method=RequestMethod.DELETE)
    public void deleteBrewerys(@PathVariable(value = "breweryID") Long id) {
        breweryRepository.deleteById(id);
    }
}