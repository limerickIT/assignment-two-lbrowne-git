package com.sd4.utilities;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class GeoMapHandler {

    private final static String APIKey ="AIzaSyDfCEKfnlzeYt-r2FAIAFeiCybD1B-5DTA"; 
    private final static String base_url = "https://maps.googleapis.com/maps/api/staticmap?";
    private final static int zoom = 14;

    /** Generates google map using api stored in class
     * 
     * @param address
     * @return  GoogleMap Json
     * @throws IOException
     * 
     */

    public static void GenerateMap(HttpServletResponse response, String address) throws IOException{
     
        String url = base_url + "center=" + address + "&zoom=" + zoom + "&size=500x500&key=" + APIKey;
        System.out.println(url);
        response.sendRedirect(url);

/* Initial attempt using GeoAPI strugglged to implement Static Map API 
     GeoApiContext context = new GeoApiContext.Builder()
                                    .apiKey(APIKey)
                                    .build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,address).await();
        } 
        catch (Exception e) {
            return "<b>Error</b> Attempting to locate Map Location\n"+e.getMessage();

    }*/


    }
}
