package com.example.tour_planner.dal.http;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpRequest {

    public String getResponse(String url) throws IOException{

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(responseCode);

        return String.valueOf(response);

    }

    public JsonNode getJsonnode(String content) {
        ObjectMapper mapper = new ObjectMapper();
        if (!Objects.equals(content, "")) {
            try {
                return mapper.readTree(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveImg(String url,String name) throws IOException {

        Image image = null;
        URL obj = new URL(url);
        image = ImageIO.read(obj);
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/example/tour_planner/maps/"+name+"_map.jpg");
        ImageIO.write((RenderedImage) image, "jpg", fos);
    }
}
