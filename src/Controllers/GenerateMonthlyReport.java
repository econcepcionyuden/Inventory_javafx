package Controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.scenario.effect.ImageData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import models.MonthlyRecord;
import util.ProductDAO;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jaliya on 1/9/18.
 */
public class GenerateMonthlyReport {

    private final ObservableList<MonthlyRecord> monthlyRecords;
    private final String fromMonth;
    private final String toMonth;
    private final String fromYear;
    private final String toYear;


    String[] monthName = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public GenerateMonthlyReport(String fromMonth, String toMonth, ObservableList<MonthlyRecord> monthlyRecords, String fromYear, String toYear) {
        this.monthlyRecords = monthlyRecords;
        this.fromMonth = fromMonth;
        this.toMonth = toMonth;
        this.fromYear = fromYear;
        this.toYear = toYear;

    }


    public static void savePieChartToFile(ObservableList<PieChart.Data> pieChartData, String path) throws IOException {

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("CURRENT INVENTORY STATUS");
        Scene sceneForChart = new Scene(pieChart, 800, 600);
        WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void saveProfitLineChartToFile(ObservableList<MonthlyRecord> monthlyRecords, String path) throws IOException {

        CategoryAxis monthAxis = new CategoryAxis();
        monthAxis.setLabel("Month");

        NumberAxis profitAxis = new NumberAxis();
        profitAxis.setLabel("Profit (Rs)");

        LineChart profitLineChart = new LineChart(monthAxis, profitAxis);
        XYChart.Series series1 = new XYChart.Series();
        profitLineChart.setTitle("PROFIT CHART");
        int[] profitValues = new int[monthlyRecords.size()];
        int[] monthNo = new int[monthlyRecords.size()];

        for (int i = 0; i < monthlyRecords.size(); i++) {

            profitValues[i] = Integer.parseInt(monthlyRecords.get(i).getProfit());
            monthNo[i] = Integer.parseInt(monthlyRecords.get(i).getMonth());
            series1.getData().add(new XYChart.Data(monthName[monthNo[i] - 1], profitValues[i]));
        }

        profitLineChart.getData().addAll(series1);
        profitLineChart.setLegendVisible(false);

        Scene sceneForChart = new Scene(profitLineChart, 800, 600);
        WritableImage image = profitLineChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveTurnoverLineChartToFile(ObservableList<MonthlyRecord> monthlyRecords, String path) throws IOException {

        CategoryAxis monthAxis = new CategoryAxis();
        monthAxis.setLabel("Month");

        NumberAxis turnoverAxis = new NumberAxis();
        turnoverAxis.setLabel("Turnover Value");

        LineChart turnoverLineChart = new LineChart(monthAxis, turnoverAxis);
        XYChart.Series series2 = new XYChart.Series();
        turnoverLineChart.setTitle("TURNOVER VALUE CHART");

        float[] turnoverValues = new float[monthlyRecords.size()];
        int[] monthNo = new int[monthlyRecords.size()];

        for (int i = 0; i < monthlyRecords.size(); i++) {

            turnoverValues[i] = Float.parseFloat(monthlyRecords.get(i).getInventoryTurnover());
            monthNo[i] = Integer.parseInt(monthlyRecords.get(i).getMonth());
            series2.getData().add(new XYChart.Data(monthName[monthNo[i] - 1], turnoverValues[i]));
        }

        turnoverLineChart.getData().addAll(series2);
        turnoverLineChart.setLegendVisible(false);

        Scene sceneForChart = new Scene(turnoverLineChart, 800, 600);
        WritableImage image = turnoverLineChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void saveExpensesBarChartToFile(ObservableList<MonthlyRecord> monthlyRecords, String path) throws IOException {

        CategoryAxis monthAxis = new CategoryAxis();
        monthAxis.setLabel("Month");

        NumberAxis turnoverAxis = new NumberAxis();
        turnoverAxis.setLabel("Expense (Rs)");

        BarChart expenseBarChart = new BarChart(monthAxis, turnoverAxis);
        expenseBarChart.setTitle("EXPENSES CHART");
        XYChart.Series series2 = new XYChart.Series();

        float[] expenseValues = new float[monthlyRecords.size()];
        int[] monthNo = new int[monthlyRecords.size()];

        for (int i = 0; i < monthlyRecords.size(); i++) {

            expenseValues[i] = Float.parseFloat(monthlyRecords.get(i).getExpense());
            monthNo[i] = Integer.parseInt(monthlyRecords.get(i).getMonth());
            series2.getData().add(new XYChart.Data(monthName[monthNo[i] - 1], expenseValues[i]));
        }

        expenseBarChart.getData().addAll(series2);
        expenseBarChart.setLegendVisible(false);

        Scene sceneForChart = new Scene(expenseBarChart, 800, 600);
        WritableImage image = expenseBarChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveIncomeBarChartToFile(ObservableList<MonthlyRecord> monthlyRecords, String path) throws IOException {

        CategoryAxis monthAxis = new CategoryAxis();
        monthAxis.setLabel("Month");

        NumberAxis incomeAxis = new NumberAxis();
        incomeAxis.setLabel("Income (Rs)");

        BarChart incomeBarChart = new BarChart(monthAxis, incomeAxis);
        incomeBarChart.setTitle("INCOME CHART");
        XYChart.Series series3 = new XYChart.Series();

        float[] incomeValues = new float[monthlyRecords.size()];
        int[] monthNo = new int[monthlyRecords.size()];

        for (int i = 0; i < monthlyRecords.size(); i++) {

            incomeValues[i] = Float.parseFloat(monthlyRecords.get(i).getIncome());
            monthNo[i] = Integer.parseInt(monthlyRecords.get(i).getMonth());
            series3.getData().add(new XYChart.Data(monthName[monthNo[i] - 1], incomeValues[i]));
        }

        incomeBarChart.getData().addAll(series3);
        incomeBarChart.setLegendVisible(false);

        Scene sceneForChart = new Scene(incomeBarChart, 800, 600);
        WritableImage image = incomeBarChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void footer(){



    }


    public void generateMonthlyReport() throws IOException, SQLException, ClassNotFoundException {

        try {
            Document document = new Document();
            FileOutputStream fs = new FileOutputStream("MonthlyReport.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fs);
            document.open();

            Paragraph paragraph = new Paragraph("\t\t\t" + "MONTHLY REPORT\n" + "From  " + monthName[Integer.parseInt(fromMonth) - 1] + " (" + fromYear + ") to  " + monthName[Integer.parseInt(toMonth) - 1] + " (" + toYear + ") \nABC Holdings (pvt) Ltd");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            Image img = Image.getInstance("logo.jpg");
            img.setAbsolutePosition(450f, 740f);
            img.scaleAbsolute(50f, 50f);
            document.add(img);
            document.add(new Paragraph("****************************************************************************************************************"));

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            float fontSize = 6.7f;
            PdfPTable table = createMonthlyRecordsTable(fontSize);
            document.add(table);

            saveProfitLineChartToFile(monthlyRecords, "profitLineChart.png");
            saveTurnoverLineChartToFile(monthlyRecords, "turnLineChart.png");
            saveExpensesBarChartToFile(monthlyRecords, "expenseBarChart.png");
            saveIncomeBarChartToFile(monthlyRecords, "incomeBarChart.png");

//            PdfPTable graphTable = new PdfPTable(2);
//            PdfPCell cell = new PdfPCell();
//            cell.setColspan(2);
//            table.addCell(String.valueOf(pieChart()));
//            table.addCell(String.valueOf(profitChart(document)));
//            table.addCell(String.valueOf(turnoverChart(document)));
//            table.addCell(String.valueOf(expenseChart(document)));
//            table.addCell(String.valueOf(incomeChart(document)));

            pieChart();
            document.add(Chunk.NEWLINE);
            Image pieChartImg = Image.getInstance("inventoryPieChart.png");
            pieChartImg.scaleAbsolute(250f, 200f);
            pieChartImg.setAlignment(Element.ALIGN_CENTER);
            document.add(pieChartImg);
            document.add(Chunk.NEWLINE);
            expenseChart(document);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            incomeChart(document);
            document.add(Chunk.NEWLINE);
            profitChart(document);
            document.add(Chunk.NEWLINE);
            turnoverChart(document);
            document.add(Chunk.NEWLINE);




            document.close();
        } catch (DocumentException | FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }



    public PieChart pieChart() throws SQLException, ClassNotFoundException, IOException {
        PieChart pieChart = new PieChart();
        int goodProductNo = ProductDAO.productCount();
        int expiredProductNo = ProductDAO.expiredProductCount();
        int damagedProductNo = ProductDAO.damagedProductCount();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("GOOD", goodProductNo),
                new PieChart.Data("EXPIRED", expiredProductNo),
                new PieChart.Data("DAMAGED", damagedProductNo));
        pieChart.setData(pieChartData);
        pieChart.setAnimated(false);
        savePieChartToFile(pieChart.getData(), "inventoryPieChart.png");

        return pieChart;
    }


    public Image profitChart(Document document) throws SQLException, ClassNotFoundException, IOException, DocumentException {
        document.add(Chunk.NEWLINE);
        Image profitLineChartImg = Image.getInstance("profitLineChart.png");
        profitLineChartImg.scaleAbsolute(250f, 200f);
        profitLineChartImg.setAlignment(Element.ALIGN_CENTER);
        document.add(profitLineChartImg);

        return profitLineChartImg;
    }

    public Image turnoverChart(Document document) throws SQLException, ClassNotFoundException, IOException, DocumentException {

        document.add(Chunk.NEWLINE);
        Image turnLineChartImg = Image.getInstance("turnLineChart.png");
       // turnLineChartImg.setAbsolutePosition(350f, 20f);
        turnLineChartImg.scaleAbsolute(250f, 200f);
        turnLineChartImg.setAlignment(Element.ALIGN_CENTER);
        document.add(turnLineChartImg);
        return turnLineChartImg;
    }

    public Image expenseChart(Document document) throws SQLException, ClassNotFoundException, IOException, DocumentException {

        document.add(Chunk.NEWLINE);
        Image expenseChartImg = Image.getInstance("expenseBarChart.png");
       // expenseChartImg.setAbsolutePosition(50f, 500f);
        expenseChartImg.scaleAbsolute(250f, 200f);
        expenseChartImg.setAlignment(Element.ALIGN_CENTER);
        document.add(expenseChartImg);

        return expenseChartImg;
    }

    public Image incomeChart(Document document) throws SQLException, ClassNotFoundException, IOException, DocumentException {


        document.add(Chunk.NEWLINE);
        Image incomeChartImg = Image.getInstance("incomeBarChart.png");
       // incomeChartImg.setAbsolutePosition(50f, 20f);
        incomeChartImg.scaleAbsolute(250f, 200f);
        incomeChartImg.setAlignment(Element.ALIGN_CENTER);
        document.add(incomeChartImg);

        return incomeChartImg;
    }


    private PdfPTable createMonthlyRecordsTable(float fontSize) {

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(7);
        PdfPCell c1 = new PdfPCell(new Phrase("Year", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Month", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Expense\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Income\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Profit\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Inventory\nTurnover", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Status\nRatio", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (MonthlyRecord i : monthlyRecords) {

            PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(i.getYear()), normalFont));
            PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(monthName[Integer.parseInt(i.getMonth()) - 1]), normalFont));
            PdfPCell c4 = new PdfPCell(new Phrase(String.valueOf(i.getExpense()), normalFont));
            PdfPCell c5 = new PdfPCell(new Phrase(String.valueOf(i.getIncome()), normalFont));
            PdfPCell c6 = new PdfPCell(new Phrase(String.valueOf(i.getProfit()), normalFont));
            PdfPCell c7 = new PdfPCell(new Phrase(String.valueOf(i.getInventoryTurnover()), normalFont));
            PdfPCell c8 = new PdfPCell(new Phrase(String.valueOf(i.getInventoryStatusRatio()), normalFont));

            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);
            table.addCell(c7);
            table.addCell(c8);

        }

        return table;
    }

}
