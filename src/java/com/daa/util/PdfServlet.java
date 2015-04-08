/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daa.util;

import com.daa.ctrl.BestellController;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Schulung_IBB
 */
//@WebServlet(name = "RechnungPdf", urlPatterns = "/pdfdokumente/PizzaRechnung.pdf")
public class PdfServlet extends HttpServlet {
// Name der Instanz der ManagedBean muss im Namen mit der Class-Library die injiziert wird übereinstimmen

    @Inject
    private BestellController bestellController;

    /**
     *
     * @param req
     * @param resp
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/pdf");
            //benötigter Zugriff auf die im Sessionscope
            //abgelegte Bean
//            HttpSession sess = req.getSession();
//            BestellController bestellung = (BestellController) sess.getAttribute("bestellController");
            Document document = new Document();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, bos);
            document.open();
            document.add(new Paragraph("Bestellbestätigung"));

            document.add(new Paragraph(bestellController.getCurrentKunde().getVorname() + " "
                    + bestellController.getCurrentKunde().getNachname()));
            document.add(new Paragraph(bestellController.getCurrentKunde().getStrasse() + " "
                    + bestellController.getCurrentKunde().getHausnr()));
            document.add(new Paragraph(bestellController.getCurrentKunde().getPlz() + " "
                    + bestellController.getCurrentKunde().getOrt()));
            document.add(new Paragraph());
            // style the table with different column sizes
            float[] relativeWidth = {0.1f, 0.4f, 0.1f, 0.2f, 0.2f};
            PdfPTable table = new PdfPTable(relativeWidth);
            Integer amount = 0;
            Double price = 0.0;
            Double totalPrice = 0.0;
            // the header of the table
            table.addCell("Pos.");
            table.addCell("Bezeichnung");
            table.addCell("Anzahl");
            table.addCell("Einzelpreis");
            table.addCell("Gesamtpreis");
            // write the order list through the for loop
            for (int i = 0; i < bestellController.getBestellGerichte().size(); i++) {
                document.add(new Paragraph());
                table.addCell((i + 1) + ". ");
                table.addCell(bestellController.getBestellGerichte().get(i).getBezeichnung());
                table.addCell(bestellController.getBestellGerichte().get(i).getAmount().toString());
                price = bestellController.getBestellGerichte().get(i).getPreis();
                amount = bestellController.getBestellGerichte().get(i).getAmount();
                totalPrice = price * amount;
                table.addCell(price.toString() + " €");
                table.addCell(totalPrice.toString() + " €");
            }
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("Summe: ");
            table.addCell(String.valueOf(bestellController.getCurrent().getTotalPay()) + " €");
            document.add(table);
            document.close();
            OutputStream os = resp.getOutputStream();
            bos.writeTo(os);
            os.flush();
            os.close();
            for (PrintService s : PrintServiceLookup.lookupPrintServices(null, null)) {
                System.out.println(s.getName());
            }

        } catch (DocumentException ex) {
            Logger.getLogger(PdfServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }
}
