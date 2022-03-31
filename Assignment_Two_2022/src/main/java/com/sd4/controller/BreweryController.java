package com.sd4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beust.jcommander.internal.Nullable;
import com.google.zxing.WriterException;
import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;
import com.sd4.utilities.GeoMapHandler;
import com.sd4.utilities.QRGeneratorHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/brewery")

public class BreweryController {


    @Autowired
    BreweryRepository breweryRepository;
    @RequestMapping(value="/{breweryId}", method=RequestMethod.GET)
    public Brewery GetBrewery(@PathVariable(value = "breweryId") Long id){
        Brewery brewery = breweryRepository.findById(id).get();
        brewery.add(Link.of("/brewery/"+brewery.getID()),
                    Link.of("/brewery/maps/"+brewery.getID(),"Map"),
                    Link.of("/brewery/qr/"+brewery.getID(),"QR"),
                    Link.of("/brewery/"+brewery.getID(),"Delete"));
        return brewery;
    }
    @RequestMapping(value="/", method=RequestMethod.GET)
    public Page<Brewery> GetBreweries(@Nullable Integer page, @Nullable Integer offset) {
            if(page == null){
                page = 0;
            }
            if(offset == null){
                offset = 24;
            }

        return  breweryRepository.findAll(PageRequest.of(page,offset,Sort.by("name").ascending()));    
    }

    @RequestMapping(value="/maps/{breweryId}", method=RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
    public void GetBreweryMap(HttpServletResponse response, @PathVariable(value = "breweryId") Long id) throws IOException {
        Brewery brewery = breweryRepository.findById(id).get();
        GeoMapHandler.GenerateMap(response, brewery.getAddress()+","+brewery.getCity()+","+brewery.getCountry());
    }

    @ResponseBody
    @RequestMapping(value="/qr/{breweryId}", method=RequestMethod.GET,  produces = MediaType.IMAGE_JPEG_VALUE)
    public Object GetBreweryQR(@PathVariable(value = "breweryId") Long id) throws WriterException {
        Brewery brewery = breweryRepository.findById(id).get();
        String vCard = "BEGIN:VCARD\n"
             +"VERSION:3.0"
             +"\nFN:" + brewery.getName()
             +"\nTEL:"+ brewery.getPhone()
             +"\nADR:"+brewery.getAddress()
             +"\nEMAIL:"+brewery.getEmail()
             +"\nURL:"+brewery.getWebsite()
             + "\nEND:VCARD";
        try {
            return QRGeneratorHandler.GenerateQRCodeImage(vCard);
        }
        catch (IOException e) {
            return "Error occured attempting to generate image:\n"+e.getMessage();
        }

    }




    

    
    @RequestMapping(value="/{breweryId}", method=RequestMethod.DELETE)
    public void DeleteBrewery(@PathVariable(value = "breweryID") Long id) {
        breweryRepository.deleteById(id);
    }
}