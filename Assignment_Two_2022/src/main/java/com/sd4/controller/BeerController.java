package com.sd4.controller;

import java.util.List;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;
import com.sd4.utilities.ContentHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/beer")
public class BeerController {


    @Autowired
    BeerRepository beerRepository;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Beer> GetAllBeers() {
        return beerRepository.findAll(Sort.by("name").ascending());    
    }

    @RequestMapping(value="/{beerId}", method=RequestMethod.GET)
    public Beer GetBeer(@PathVariable(value = "beerId") Long id){
        Beer beer = beerRepository.findById(id).get();
        beer.link = Link.of("/beer/"+beer.getID());
        return beer;
    }
    
    @RequestMapping(value="/{beerId}", method=RequestMethod.POST)
    public String AddBeer(@PathVariable(value = "beerId") @RequestBody Beer beer){
        beer.link = Link.of("/beer/"+beer.getID());
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
    public void DeleteBeers(@PathVariable(value = "beerId") Long id) {
        beerRepository.deleteById(id);
    }

    @RequestMapping(value="/image/{beerId}", method=RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public Object GetImage(@PathVariable(value ="beerId") Long id, @RequestParam String size){
        if(size.equals("large") || size.equals("thumbs") ){
            return ContentHandler.GetImage("/"+size+"/"+id);
        }
        else{
            return "The size paramater can only be large or thumbs";
        }
    }
    @RequestMapping(value="/images/", method=RequestMethod.GET )
    public Object GetImages(){
            return ContentHandler.GetCompressedFolder("images");
    }
}