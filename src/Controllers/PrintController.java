package Controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Item;

public class PrintController {

    private final ObservableList<Item> items;
    private final String barcode;
    private final double subTotal;
    private final double netPayable;
    private final double vat;
    private final double discount;

    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public PrintController(ObservableList<Item> items, String barcode,double subTotal, double netPayable, double vat, double discount) {
        this.items = items;
        this.barcode = barcode;
        this.subTotal = subTotal;
        this.netPayable = netPayable;
        this.vat = vat;
        this.discount = discount;

    }


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);

    public void generateReport() {

        try {
            Document document = new Document();
            FileOutputStream fs = new FileOutputStream("Receipt.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fs);
            document.open();

            Paragraph paragraph = new Paragraph("\t\t\t"+"CASH RECEIPT");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("****************************************************************************************************************"));
            document.add(new Paragraph("Date : "+date));


            PdfContentByte cb = writer.getDirectContent();
            BarcodeEAN codeEAN = new BarcodeEAN();
            codeEAN.setCodeType(codeEAN.EAN13);
            codeEAN.setCode(barcode);
            document.add(codeEAN.createImageWithBarcode(cb, BaseColor.BLACK, BaseColor.DARK_GRAY));
            addEmptyLine(paragraph, 5);
            document.add( Chunk.NEWLINE );

            PdfPTable table = createTable();
            document.add(table);

            document.add(Chunk.NEWLINE);
            Paragraph paragraph1 = new Paragraph("Sub Total : "+String.valueOf(formatter.format(subTotal))+"\n"+"Vat :  \t"+String.valueOf(formatter.format(vat))+"\n"+"Discount :    "+String.valueOf(formatter.format(discount))+"\n\n"+"Total :     "+String.valueOf(formatter.format(netPayable)));
            paragraph1.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph1);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("****************************************************************************************************************"));
            Paragraph paragraph2 = new Paragraph("Thank you for doing business!!");
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph2);

            document.close();
        } catch (DocumentException | FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private PdfPTable createTable() {

        PdfPTable table = new PdfPTable(4);
        PdfPCell c1 = new PdfPCell(new Phrase("Item"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (Item i : items) {
            table.addCell(i.getItemName());
            table.addCell(String.valueOf(formatter.format(i.getUnitPrice())));
            table.addCell(String.valueOf(i.getQuantity()));
            table.addCell(String.valueOf(formatter.format(i.getTotal())));
        }

        return table;
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}