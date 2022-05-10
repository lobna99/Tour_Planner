package com.example.tour_planner.dal.fileServer;

import com.example.tour_planner.model.TourTypes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class FileAccess {
    private String filePath;

    public FileAccess(String filePath) {
        this.filePath = filePath;
    }

    private List<File> GetFileInfos(String startFolder, TourTypes searchType) {
        File dir = new File(startFolder);
        return Arrays.asList(Objects.requireNonNull(dir.listFiles(new FileExtensionFilter(searchType.toString() + ".txt"))));
    }

    private String GetFullPath(String fileName) {
        return Paths.get(filePath, fileName).toString();
    }

    public List<File> SearchFiles(String searchTerm, TourTypes searchType) throws IOException {

        List<File> fileList = GetFileInfos(filePath, searchType);
        List<File> queryMatchingFiles = new ArrayList<File>();

        for (File file : fileList) {
            try (Stream<String> streamOfLines = Files.lines(file.toPath())) {
                Optional<String> line = streamOfLines.filter(l ->
                                l.contains(searchTerm))
                        .findFirst();
                if (line.isPresent()) {
                    queryMatchingFiles.add(file);
                }
            }
        }

        return queryMatchingFiles;
    }

    public int CreateNewMediaItemFile(String name, String annotation, String url, LocalDateTime creationTime) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        int id = UUID.randomUUID().hashCode();

        String fileName = id + "_mediaItem.txt";
        String path = GetFullPath(fileName);
        FileWriter file = new FileWriter(path);

        BufferedWriter writer = new BufferedWriter(file);
        writer.write(String.valueOf(id));
        writer.newLine();
        writer.write(name);
        writer.newLine();
        writer.write(annotation);
        writer.newLine();
        writer.write(url);
        writer.newLine();
        writer.write(creationTime.format(formatter));
        writer.close();

        return id;
    }

    public int CreateNewMediaLogFile(String logText, int mediaItemId) throws IOException {

        int id = UUID.randomUUID().hashCode();

        String fileName = id + "_mediaLog.txt";
        String path = GetFullPath(fileName);
        FileWriter file = new FileWriter(path);

        BufferedWriter writer = new BufferedWriter(file);
        writer.write(String.valueOf(id));
        writer.newLine();
        writer.write(logText);
        writer.newLine();
        writer.write(String.valueOf(mediaItemId));
        writer.close();

        return id;
    }

    public List<File> GetAllFiles(TourTypes searchType) {
        return GetFileInfos(filePath, searchType);
    }

    public static class FileExtensionFilter implements FilenameFilter {

        private String extension;

        public FileExtensionFilter(String extension) {
            this.extension = extension.toLowerCase();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(extension);
        }
    }
}
