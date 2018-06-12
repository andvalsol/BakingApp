package com.example.luthiers.bakingapp.repository.network;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipeNetworkDataSource {
    
    public static String setupHttpConnection() throws IOException {
        URL recipeRequestUrl = buildUrl();
        
        //Setup the httpUrlConnection instance
        HttpURLConnection urlConnection = (HttpURLConnection) recipeRequestUrl.openConnection();
        
        try {
            //Open a connection using the received url
            HttpURLConnection con = (HttpURLConnection) recipeRequestUrl.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        
            //print in String
            return response.toString();
        } finally {
            //Since we can get an error, disconnect the urlConnection instance in a finally block, so we can ensure its closure
            urlConnection.disconnect();
        }
    }
    
    private static URL buildUrl() {
        //The url we use is: https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
        String requestUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        
        Uri buildUri = Uri.parse(requestUrl)
                .buildUpon()
                .build();
        
        try {
            return new URL(buildUri.toString());
            
        } catch (MalformedURLException e) {
            return null;
        }
    }
}