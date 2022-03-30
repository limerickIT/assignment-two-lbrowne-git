package com.sd4.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class GeoMapHandler {

    public final static String APIKey ="AIzaSyDfCEKfnlzeYt-r2FAIAFeiCybD1B-5DTA"; 
    

    /** Generates google map using api stored in class
     * 
     * @param address
     * @return  GoogleMap Json
     * 
     */

    public static Object GenerateMap(String address){
        GeoApiContext context = new GeoApiContext.Builder()
                                    .apiKey(APIKey)
                                    .build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,address).await();
        } 
        catch (Exception e) {
            return "<b>Error</b> Attempting to locate Map Location\n"+e.getMessage();

        }
        context.shutdown();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(results[0].addressComponents);

    }
}
