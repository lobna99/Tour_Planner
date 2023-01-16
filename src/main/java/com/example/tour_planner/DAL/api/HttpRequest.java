package com.example.tour_planner.DAL.api;

import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpRequest {

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public static String getResponse(String url) throws IOException{

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        logger.debug("\nSending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        logger.debug(response.toString());

        return String.valueOf(response);

    }

    public static JsonNode getJsonnode(String content) {
        ObjectMapper mapper = new ObjectMapper();
        if (!Objects.equals(content, "")) {
            try {
                JsonNode node = mapper.readTree(content);
                if(!(node.get("route").get("distance").getDoubleValue() ==0))
                    return mapper.readTree(content);
                else return null;
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return null;
    }

    public static void saveImg(String url, String name) throws IOException {

        Image image = null;
        URL obj = new URL(url);
        image = ImageIO.read(obj);
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/example/tour_planner/maps/"+name+"_map.jpg");
        ImageIO.write((RenderedImage) image, "jpg", fos);
    }
}
