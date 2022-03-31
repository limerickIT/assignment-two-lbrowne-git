package com.sd4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.beust.jcommander.internal.Nullable;
import com.lowagie.text.DocumentException;
import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;
import com.sd4.utilities.ContentHandler;
import com.sd4.utilities.PDFGeneratorHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.rowset.SqlRowSet;


@RestController
@RequestMapping("/beer")
public class BeerController {


    @Autowired
    BeerRepository beerRepository;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public Page <Beer> GetAllBeers(@Nullable Integer page, @Nullable Integer offset) {

        if(page == null){
            page = 0;
        }
        if(offset == null){
            offset = 24;
        }

        Page<Beer> beers= beerRepository.findAll(PageRequest.of(page,offset,Sort.by("name").ascending()));    
        for (Beer beer : beers) {
            beer.add(Link.of("/beer/"),
                     Link.of("/beer/"+beer.getID(),"View Beer"));
        }
        return beers;
    }

    @RequestMapping(value="/{beerId}", method=RequestMethod.GET)
    public Beer GetBeer(@PathVariable(value = "beerId") Long id){
        Beer beer = beerRepository.findById(id).get();
        beer.add(Link.of("/beer/"+beer.getID()),
                Link.of("/brewery/"+beer.getBreweryID(),"Brewery"),
                Link.of("/beer/"+beer.getID(),"Delete"),
                Link.of("/beer/"+beer.getID(),"Update"),
                Link.of("/beer/pdf/"+beer.getID(),"PDF"),
                Link.of("/beers/image/"+beer.getID(),"Image"));
        return beer;
    }
    
    @RequestMapping(value="/{beerId}", method=RequestMethod.POST)
    public String AddBeer(@PathVariable(value = "beerId") @RequestBody Beer beer){
        beer.add(Link.of("/beer/"+beer.getID()),
                Link.of("Brewery","/brewery/"+beer.getBreweryID()),
                Link.of("Image","beers/image/"+beer.getID()));
        if(!beerRepository.save(beer).equals(null)){
            return "Beer has been added to db";
        }
        else{
            return "beer has not been added";
        }

    }
    @RequestMapping(value="/{beerId}", method=RequestMethod.PUT)
    public Beer PutBeer(@PathVariable(value = "beerId") Long id, @RequestBody Beer beerDetails) {
        return beerRepository.save(beerDetails);
    }
    
    @RequestMapping(value="/{beerId}", method=RequestMethod.DELETE)
    public String DeleteBeers(@PathVariable(value = "beerId") Long id) {
        try{
            beerRepository.deleteById(id);
            return "beer "+id +" has been successfully delted"; 
        }
        catch(Exception e){
            return "Error occured deleting beer";
        }
    }

    @RequestMapping(value="/image/{beerId}", method=RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public Object GetImage(@PathVariable(value ="beerId") Long id, @RequestParam String size){
        if(size.equals("large") || size.equals("thumbs") ){
            return ContentHandler.GetImage(size+"/"+id);
        }
        else{
            return "The size paramater can only be large or thumbs";
        }
    }
    @RequestMapping(value="/images/", method=RequestMethod.GET,  produces="application/zip" )
    public Object GetImages() throws IOException{
            return ContentHandler.GetCompressedFolder("");
    }

    @RequestMapping(value="/pdf/{beerId}", method=RequestMethod.GET,  produces="application/pdf" )
    public void GetPDF(HttpServletResponse response, @PathVariable(value ="beerId") Long id) throws IOException, DocumentException{
    
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=pdf_beer.pdf";
            response.setHeader(headerKey, headerValue);
            PDFGeneratorHandler pHandler = new PDFGeneratorHandler();
            pHandler.CreateBeerPDF(response, id);
        }


}