package com.example.tour_planner.DAL.fileServer;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.DAL.api.HttpRequest;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.Tour;
import org.codehaus.jackson.JsonNode;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;


public class ImportExport implements IImportExport {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private String filePath;

    public ImportExport() {
        //this.filePath = filePath;
    }
    @Override
    public Tour importTour(String path){

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject tourObj = (JSONObject) obj;
            logger.debug((String) tourObj.get("Name"));
            JsonNode node = HttpRequest.getJsonnode(HttpRequest.getResponse("https://www.mapquestapi.com/directions/v2/route?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&from=" + tourObj.get("From") + "&to=" + tourObj.get("To") + ""));
            assert node != null;
            HttpRequest.saveImg("https://www.mapquestapi.com/staticmap/v5/map?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&size=650,650&defaultMarker=none&zoom=8&session=" + node.get("route").get("sessionId").getTextValue(),(String) tourObj.get("Name"));
            Tour tour= new Tour((String) tourObj.get("Transport"),(String) tourObj.get("Name"),(String)tourObj.get("From"),(String)tourObj.get("To"),node.get("route").get("formattedTime").getTextValue(),node.get("route").get("distance").getDoubleValue(),(String)tourObj.get("Description"));
            DAL.getInstance().tourDao().create(tour);
            return tour;

        } catch (IOException | ParseException | SQLException | java.text.ParseException e) {
            logger.error(e.toString());
        }
        return null;
    }


    @Override
    public void exportTour(Tour tour) throws JSONException {
        Properties appProps = new Properties();
        try {
            appProps.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("base_directory.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("Name",tour.getName());
        jsonObject.put("From", tour.getFrom());
        jsonObject.put("To", tour.getTo());
        jsonObject.put("Distance", tour.getDistance());
        jsonObject.put("Duration", tour.getDuration());
        jsonObject.put("Transportation_Type", tour.getTransport_type());
        jsonObject.put("Content", tour.getContent());
        try {
            FileWriter file = new FileWriter(appProps.getProperty("exported_tours_location")+tour.getName()+".json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
        logger.debug("JSON file created: "+jsonObject);
    }

}
