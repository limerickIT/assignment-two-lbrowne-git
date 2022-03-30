package com.sd4.controller;

import java.io.IOException;
import java.util.List;

import com.google.zxing.WriterException;
import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;
import com.sd4.utilities.GeoMap;
import com.sd4.utilities.QRGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

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
    public Object readBreweryMap(@PathVariable(value = "breweryId") Long id) {
        
        
        Brewery brewery = breweryRepository.findById(id).get();
        
        return GeoMap.GenerateMap(brewery.getAddress());
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
        try {
            QRGenerator.GenerateQRCodeImage(vCard);
            return QRGenerator.GetQRCodeImage(vCard);

        } catch (IOException e) {
            return "Error occured attempting to generate image:\n"+e.getMessage();
        }

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