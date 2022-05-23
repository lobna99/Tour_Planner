package com.example.tour_planner.DAL.fileServer;

import com.example.tour_planner.model.Tour;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;

public class ReportWriter implements IReportWriter {

    @Override
    public void createTourReport(Tour tour) throws IOException {

    PdfWriter writer = new PdfWriter("src/main/java/com/example/tour_planner/dal/fileServer/reports/tour_reports/"+tour.getName()+"_report.pdf");
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

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
            .add(new ListItem("Estimated time: "+tour.getDuration()))
            .add(new ListItem("Date: "+tour.getDate()));
        document.add(listHeader);
        document.add(list);

    Paragraph tableHeader = new Paragraph("Logs")
            .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
            .setFontSize(18)
            .setBold()
            .setFontColor(ColorConstants.GREEN);
        document.add(tableHeader);
    Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
        table.addHeaderCell(getHeaderCell("Ipsum 1"));
        table.addHeaderCell(getHeaderCell("Ipsum 2"));
        table.addHeaderCell(getHeaderCell("Ipsum 3"));
        table.addHeaderCell(getHeaderCell("Ipsum 4"));
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
        table.addCell("lorem 1");
        table.addCell("lorem 2");
        table.addCell("lorem 3");
        table.addCell("lorem 4");
        document.add(table);

        document.add(new AreaBreak());

    Paragraph imageHeader = new Paragraph("Map")
            .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
            .setFontSize(18)
            .setBold()
            .setFontColor(ColorConstants.GREEN);
        document.add(imageHeader);
    ImageData imageData = ImageDataFactory.create("src/main/resources/com/example/tour_planner/maps/"+tour.getName()+"_map.jpg");
        document.add(new Image(imageData));
        document.close();
    }

    @Override
    public void createSummaryReport(String tourname, java.util.List<String> Data) {

    }
    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
    }
}
