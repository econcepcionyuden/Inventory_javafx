package Controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;
import models.Item;
import models.SalesRecord;
import models.Vendor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by jaliya on 1/8/18.
 */
public class GenerateSalesReport {

    private final ObservableList<SalesRecord> saleRecords;
    private final String fromDate;
    private final String toDate;



    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public GenerateSalesReport(String fromDate, String toDate, ObservableList<SalesRecord> saleRecords) {
        this.saleRecords = saleRecords;
        this.fromDate = fromDate;
        this.toDate = toDate;

    }



    public void generateSalesReport() throws IOException {

        try {
            Document document = new Document();
            FileOutputStream fs = new FileOutputStream("SalesReport.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fs);
            document.open();

            Paragraph paragraph = new Paragraph("\t\t\t" + "SALES REPORT\n" + "From  " + fromDate + "  to  " + toDate + "\nABC Holdings (pvt) Ltd");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            Image img = Image.getInstance("logo.jpg");
            img.setAbsolutePosition(450f, 740f);
            img.scaleAbsolute(50f, 50f);
            document.add(img);
            document.add(new Paragraph("****************************************************************************************************************"));
//            int noOfPages = writer.getPageNumber();
//            for(int i = 0; i<noOfPages; i++){
//
//                Rectangle rect = new Rectangle(36, 36, 550, 806);
//                rect.setBorder(Rectangle.BOX);
//                rect.setBorderWidth(2);
//                document.add(rect);
//
//            }

//
//            PdfContentByte cb = writer.getDirectContent();
//            BarcodeEAN codeEAN = new BarcodeEAN();
//            codeEAN.setCodeType(codeEAN.EAN13);
//            codeEAN.setCode(barcode);
//            document.add(codeEAN.createImageWithBarcode(cb, BaseColor.BLACK, BaseColor.DARK_GRAY));
//            addEmptyLine(paragraph, 5);
//            document.add( Chunk.NEWLINE );
//

            float fontSize = 6.7f;
            PdfPTable table = createSalesTable(fontSize);
            document.add(table);

//            document.add(Chunk.NEWLINE);
//            Paragraph paragraph1 = new Paragraph("Sub Total : "+String.valueOf(formatter.format(subTotal))+"\n"+"Vat :  \t"+String.valueOf(formatter.format(vat))+"\n"+"Discount :    "+String.valueOf(formatter.format(discount))+"\n\n"+"Total :     "+String.valueOf(formatter.format(netPayable)));
//            paragraph1.setAlignment(Element.ALIGN_RIGHT);
//            document.add(paragraph1);

//            document.add(Chunk.NEWLINE);
//            document.add(new Paragraph("****************************************************************************************************************"));
//            Paragraph paragraph2 = new Paragraph("Thank you for doing business!!");
//            paragraph2.setAlignment(Element.ALIGN_CENTER);
//            document.add(paragraph2);




            document.close();
        } catch (DocumentException | FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private PdfPTable createSalesTable(float fontSize) {

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(8);
        PdfPCell c1 = new PdfPCell(new Phrase("Transaction\nDate", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Unit Price\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Sub Total\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("VAT\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Discount\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Payable\nRs.", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (SalesRecord i : saleRecords) {

            PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(i.getTransactionDate()), normalFont));
            PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(i.getProductId()), normalFont));
            PdfPCell c4 = new PdfPCell(new Phrase(String.valueOf(i.getUnitPrice()), normalFont));
            PdfPCell c5 = new PdfPCell(new Phrase(String.valueOf(i.getQuantity()), normalFont));
            PdfPCell c6 = new PdfPCell(new Phrase(String.valueOf(i.getSubTotal()), normalFont));
            PdfPCell c7 = new PdfPCell(new Phrase(String.valueOf(i.getVat()), normalFont));
            PdfPCell c8 = new PdfPCell(new Phrase(String.valueOf(i.getDiscount()), normalFont));
            PdfPCell c9 = new PdfPCell(new Phrase(String.valueOf(i.getPayable()), normalFont));

            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);
            table.addCell(c7);
            table.addCell(c8);
            table.addCell(c9);
        }

        return table;
    }

}
