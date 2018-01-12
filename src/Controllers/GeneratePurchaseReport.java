package Controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import models.Purchase;
import models.SalesRecord;
import models.Vendor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by jaliya on 1/8/18.
 */
public class GeneratePurchaseReport {

    private final String fromDate;
    private final String toDate;
    private final ObservableList<Purchase> purchaseRecords;



    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public GeneratePurchaseReport(String fromDate, String toDate, ObservableList<Purchase> purchaseRecords) {
        this.purchaseRecords = purchaseRecords;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


    public void generatePurchaseReport() throws IOException {

        try {
            Document document = new Document();
            FileOutputStream fs = new FileOutputStream("PurchaseReport.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fs);
            document.open();

            Paragraph paragraph = new Paragraph("\t\t\t" + "PURCHASE REPORT\n" + "From  " + fromDate + "  to  " + toDate + "\nABC Holdings (pvt) Ltd");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            Image img = Image.getInstance("logo.jpg");
            img.setAbsolutePosition(450f, 740f);
            img.scaleAbsolute(50f, 50f);
            document.add(img);
            document.add(new Paragraph("****************************************************************************************************************"));

            float fontSize = 6.7f;
            PdfPTable table = createPurchaseTable(fontSize);
            document.add(table);

            document.close();
        } catch (DocumentException | FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private PdfPTable createPurchaseTable(float fontSize) {

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(5);
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

        c1 = new PdfPCell(new Phrase("Supplier", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (Purchase i : purchaseRecords) {

            PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(i.getTransaction_date()), normalFont));
            PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(i.getProductId()), normalFont));
            PdfPCell c4 = new PdfPCell(new Phrase(String.valueOf(i.getAmount()), normalFont));
            PdfPCell c5 = new PdfPCell(new Phrase(String.valueOf(i.getQuantity()), normalFont));
            PdfPCell c6 = new PdfPCell(new Phrase(String.valueOf(i.getVendorId()), normalFont));

            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);

        }
        return table;
    }

}
