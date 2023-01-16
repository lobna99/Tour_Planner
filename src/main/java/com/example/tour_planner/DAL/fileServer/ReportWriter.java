package com.example.tour_planner.DAL.fileServer;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ReportWriter implements IReportWriter {

    private Properties appProps = new Properties();

    public ReportWriter(){
        try {
            appProps.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("base_directory.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTourReport(Tour tour) throws IOException, SQLException {

    PdfWriter writer = new PdfWriter(appProps.getProperty("tour_reports_location")+tour.getName()+"_report.pdf");
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);
    java.util.List<TourLog> logs = DAL.getInstance().tourLogDao().getAll(tour.getName());


    Paragraph loremIpsumHeader = new Paragraph(tour.getName()+" Report")
            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
            .setFontSize(14)
            .setBold()
            .setFontColor(ColorConstants.BLACK);
        document.add(loremIpsumHeader);
        document.add(new Paragraph(tour.getContent()));

    Paragraph listHeader = new Paragraph("Tour Information")
            .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD))
            .setFontSize(14)
            .setBold()
            .setFontColor(ColorConstants.BLUE);
    List list = new List()
            .setSymbolIndent(12)
            .setListSymbol("\u2022")
            .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        list.add(new ListItem("From: "+tour.getFrom()))
            .add(new ListItem("To: "+tour.getTo()))
            .add(new ListItem("Transport type: "+tour.getTransport_type()))
            .add(new ListItem("Distance: "+tour.getDistance()))
            .add(new ListItem("Estimated time: "+tour.getDuration()));

        document.add(listHeader);
        document.add(list);

    Paragraph tableHeader = new Paragraph("Logs")
            .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
            .setFontSize(18)
            .setBold()
            .setFontColor(ColorConstants.GREEN);
        document.add(tableHeader);
    Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
        table.addHeaderCell(getHeaderCell("Comment"));
        table.addHeaderCell(getHeaderCell("Time"));
        table.addHeaderCell(getHeaderCell("Difficulty"));
        table.addHeaderCell(getHeaderCell("Rating"));

        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        for (TourLog x : logs){
            table.addCell(x.getComment());
            table.addCell(x.getTotal_time());
            table.addCell(String.valueOf(x.getDifficultly()));
            table.addCell(String.valueOf(x.getRating()));
        }
        document.add(table);

        document.add(new AreaBreak());

    Paragraph imageHeader = new Paragraph("Map")
            .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
            .setFontSize(18)
            .setBold()
            .setFontColor(ColorConstants.GREEN);
        document.add(imageHeader);
    ImageData imageData = ImageDataFactory.create(appProps.getProperty("map_location")+tour.getName()+"_map.jpg");
        document.add(new Image(imageData));
        document.close();

    }

    @Override
    public void createSummaryReport(String tourname, java.util.List<String> Data) throws IOException {
        PdfWriter writer = new PdfWriter(appProps.getProperty("summarize_reports_location")+tourname+"_summarize_report.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph loremIpsumHeader = new Paragraph(tourname+" Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(loremIpsumHeader);
        document.add(new Paragraph(tourname));
            Paragraph listHeader = new Paragraph("Tour Information")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        list.add(new ListItem("Average Rating: "+Data.get(0)))
                .add(new ListItem("Average Difficulty: "+Data.get(1)))
                .add(new ListItem("Average Time: "+Data.get(2)));
        document.add(listHeader);
        document.add(list);
        document.close();
    }
    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }
}
