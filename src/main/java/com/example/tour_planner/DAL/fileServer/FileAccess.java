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


public class FileAccess {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private String filePath;

    public FileAccess() {
        //this.filePath = filePath;
    }

//    private List<File> GetFileInfos(String startFolder, TourTypes searchType) {
//        File dir = new File(startFolder);
//        return Arrays.asList(Objects.requireNonNull(dir.listFiles(new FileExtensionFilter(searchType.toString() + ".txt"))));
//    }
//
//    private String GetFullPath(String fileName) {
//        return Paths.get(filePath, fileName).toString();
//    }
//
//    public List<File> SearchFiles(String searchTerm, TourTypes searchType) throws IOException {
//
//        List<File> fileList = GetFileInfos(filePath, searchType);
//        List<File> queryMatchingFiles = new ArrayList<File>();
//
//        for (File file : fileList) {
//            try (Stream<String> streamOfLines = Files.lines(file.toPath())) {
//                Optional<String> line = streamOfLines.filter(l ->
//                                l.contains(searchTerm))
//                        .findFirst();
//                if (line.isPresent()) {
//                    queryMatchingFiles.add(file);
//                }
//            }
//        }
//
//        return queryMatchingFiles;
//    }
//
//    public int CreateTourFile(String name, String annotation, String url, LocalDateTime creationTime) throws IOException {
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        int id = UUID.randomUUID().hashCode();
//
//        String fileName = id + "_tour.txt";
//        String path = GetFullPath(fileName);
//        FileWriter file = new FileWriter(path);
//
//        BufferedWriter writer = new BufferedWriter(file);
//        writer.write(String.valueOf(id));
//        writer.newLine();
//        writer.write(name);
//        writer.newLine();
//        writer.write(annotation);
//        writer.newLine();
//        writer.write(url);
//        writer.newLine();
//        writer.write(creationTime.format(formatter));
//        writer.close();
//
//        return id;
//    }
//
//    public int CreateNewMediaLogFile(String logText, int mediaItemId) throws IOException {
//
//        int id = UUID.randomUUID().hashCode();
//
//        String fileName = id + "_mediaLog.txt";
//        String path = GetFullPath(fileName);
//        FileWriter file = new FileWriter(path);
//
//        BufferedWriter writer = new BufferedWriter(file);
//        writer.write(String.valueOf(id));
//        writer.newLine();
//        writer.write(logText);
//        writer.newLine();
//        writer.write(String.valueOf(mediaItemId));
//        writer.close();
//
//        return id;
//    }
//
//    public List<File> GetAllFiles(TourTypes searchType) {
//        return GetFileInfos(filePath, searchType);
//    }
//
//    public static class FileExtensionFilter implements FilenameFilter {
//
//        private String extension;
//
//        public FileExtensionFilter(String extension) {
//            this.extension = extension.toLowerCase();
//        }
//
//        @Override
//        public boolean accept(File dir, String name) {
//            return name.toLowerCase().endsWith(extension);
//        }
//    }

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
            Tour tour= new Tour(1,(String) tourObj.get("Name"),"",(String)tourObj.get("From"),(String)tourObj.get("To"),node.get("route").get("formattedTime").getTextValue(),node.get("route").get("distance").getDoubleValue(),(String)tourObj.get("Description"));
            DAL.getInstance().tourDao().create(tour);
            return tour;
            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (IOException | ParseException e) {
            logger.fatal(e.toString());
        } catch (IOException | ParseException | SQLException | java.text.ParseException e) {
            logger.error(e.toString());
        }
        return null;
    }



    public void exportTour(Tour tour) throws JSONException {
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
            FileWriter file = new FileWriter("src/main/java/com/example/tour_planner/DAL/fileServer/exportedData/"+tour.getName()+".json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.fatal(e.toString());
        }
        logger.debug("JSON file created: "+jsonObject);
    }

}
