package com.invoice.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class InvoiceGeneratorWebController {
    @Autowired
    ServletContext context;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorServiceImpl();

    @RequestMapping(value = "/loadProduct", method = RequestMethod.GET)
    public String loadProductList(ModelMap model) {
        try {
            List<InvoiceModel> productList = invoiceGeneratorService.fetchProductListFromDB(1);
            PdfDataCollectionModel pdfDataCollectionModel = invoiceGeneratorService
                    .fetchCompanyDetailsFromDB(1);

            model.addAttribute("productList", productList.toArray());
            model.addAttribute("companyName", pdfDataCollectionModel.getCompanyName());
            model.addAttribute("companyAddress", pdfDataCollectionModel.getCompanyAddress());
            model.addAttribute("companyMobile", pdfDataCollectionModel.getCompanyMobile());
            model.addAttribute("companyVattin", pdfDataCollectionModel.getCompanyVattin());
            model.addAttribute("companyCst", pdfDataCollectionModel.getCompanyCst());
            model.addAttribute("companyId", pdfDataCollectionModel.getCompanyId());
            model.addAttribute("orderId", pdfDataCollectionModel.getOrderId());
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");// new
                                                                      // SimpleDateFormat("dd-MM-yyyy
                                                                      // hh:mm a");
            String orderDate = formatter.format(new Date());

            model.addAttribute("orderDate", orderDate);
            return "inventoryItems";
        } catch (Exception e) {
            return "errorPageDBFetch";
        }
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String loadAddProduct(ModelMap model) {
        model.addAttribute("companyId", 1);
        return "addProduct";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String loadHome(ModelMap model) {
        return "index";
    }

    @RequestMapping(value = "/addNewProduct", method = RequestMethod.POST)
    public String addNewProduct(ModelMap model, @RequestParam String product,
            @RequestParam String description, @RequestParam String unitPrice,
            @RequestParam String quantity, @RequestParam String companyId) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setDescription(description);
        invoiceModel.setProduct(product);
        invoiceModel.setQuantity(Float.parseFloat(quantity));
        invoiceModel.setUnitPrice(Float.parseFloat(unitPrice));
        invoiceModel.setCompanyId(Integer.parseInt(companyId));

        boolean dbOperationStatus = invoiceGeneratorService.addProductToDB(invoiceModel);
        model.addAttribute("companyId", 1);
        if (dbOperationStatus)
            model.addAttribute("msgToUser", "New Product: " + product + " added successfully.");
        else
            model.addAttribute("msgToUser", "Error while adding product: " + product + ".");
        return "addProduct";
    }

    @RequestMapping(value = "/generateInvoice", method = RequestMethod.POST)
    public String generateInvoice(@RequestParam String pdfTextContent,
            @RequestParam String companyName, @RequestParam String companyAddress,
            @RequestParam String companyMobile, @RequestParam String companyVattin,
            @RequestParam String companyCst, @RequestParam String customerName,
            @RequestParam String customerMobile, @RequestParam String customerAddress,
            @RequestParam String customerEmail, @RequestParam String orderId,
            @RequestParam String companyId, @RequestParam String orderDate,
            @RequestParam String tax, @RequestParam String discount)
            throws DocumentException, IOException, ParseException {
        String userInput = URLDecoder.decode(pdfTextContent, "UTF-8");

        PdfDataCollectionModel pdfDataCollection = this.stringToObjectFromUserTextInput(userInput);
        String[] a = { companyName, companyAddress, companyMobile };
        String[] b = { InvoiceWebConstants.footerMsg1, InvoiceWebConstants.footerMsg2 };
        pdfDataCollection.setCompanyId(Integer.parseInt(companyId));
        pdfDataCollection.setCompanyAddress(companyAddress);
        pdfDataCollection.setCompanyCst(companyCst);
        pdfDataCollection.setCompanyMobile(companyMobile);
        pdfDataCollection.setCompanyName(companyName);
        pdfDataCollection.setCompanyVattin(companyVattin);
        pdfDataCollection.setCustomerAddress(customerAddress);
        pdfDataCollection.setCustomerEmail(customerEmail);
        pdfDataCollection.setCustomerMobile(customerMobile);
        pdfDataCollection.setCustomerName(customerName);
        pdfDataCollection.setDiscount(Float.valueOf(discount));
        pdfDataCollection.setTax(Float.valueOf(tax));
        pdfDataCollection.setHeader(a);
        pdfDataCollection.setFooter(b);
        pdfDataCollection.setOrderDate(orderDate);
        pdfDataCollection.setOrderId(Integer.parseInt(orderId));

        if (invoiceGeneratorService.saveInvoiceDetailsToDB(pdfDataCollection)) {
            this.generateInvoicePdf(pdfDataCollection);
            return "inventoryItems";
        } else {
            return "errorPageDBSave";
        }
    }

    private PdfDataCollectionModel stringToObjectFromUserTextInput(String userInput) {
        String[] rows = userInput.split("@@@");
        List<InvoiceModel> productList = new ArrayList<InvoiceModel>();
        InvoiceModel product = null;
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split("##");
            product = new InvoiceModel();
            product.setProduct(cells[1]);
            product.setDescription(cells[2]);
            product.setQuantity(Float.parseFloat(cells[3]));
            product.setUnitPrice(Float.parseFloat(cells[4]));
            product.setTotal(Float.parseFloat(cells[5]));
            product.setProductId(Integer.parseInt(cells[6]));
            productList.add(product);
        }
        PdfDataCollectionModel pdfDataCollectionModel = new PdfDataCollectionModel();
        pdfDataCollectionModel.setInvoiceModel(productList);
        return pdfDataCollectionModel;
    }

    private void generateInvoicePdf(PdfDataCollectionModel pdfDataCollection)
            throws DocumentException, IOException, ParseException {
        List<PdfPTable> pdfPTableList = this.formatPdfContents(pdfDataCollection);
        String fileName = pdfDataCollection.getOrderId() + ".pdf";
        Document document = new Document();
        PdfWriter writer = null;
        File file = new File(fileName);
        FileOutputStream out = new FileOutputStream(file);
        writer = PdfWriter.getInstance(document, out);
        document.setMargins(-30, -30, 2, 2);
        document.open();

        for (int index = 0; index < pdfPTableList.size(); index++)
            document.add(pdfPTableList.get(index));

        document.close();
        writer.close();
        out.close();
        this.downloadPdfFile(request, response, fileName);
    }

    private void downloadPdfFile(HttpServletRequest request, HttpServletResponse response,
            String filename) throws IOException {
        context = request.getServletContext();
        String fullPath = "";
        if (StringUtils.isEmpty(filename)) {
            String appPath = context.getRealPath("/");
            fullPath = appPath + InvoiceWebConstants.filePath;
        } else {
            fullPath = filename;
        }

        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/pdf";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[InvoiceWebConstants.BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

    private List<PdfPTable> formatPdfContents(PdfDataCollectionModel pdfDataCollection) {
        List<PdfPTable> pdfTableList = new ArrayList<PdfPTable>();

        float[] columnWidths = { 2, 5, 5, 2, 3, 3 };
        PdfPTable headerTable = new PdfPTable(5);
        PdfPTable midSectionTable = new PdfPTable(columnWidths);
        PdfPTable footerTable = new PdfPTable(1);

        float amountTotal = 0;

        PdfPCell cell01 = new PdfPCell(new Phrase(" ", InvoiceWebConstants.font1));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("Invoice Receipt/Bill", InvoiceWebConstants.font12));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase(" ", InvoiceWebConstants.font1));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(
                new Phrase(pdfDataCollection.getHeader()[0], InvoiceWebConstants.font1));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("address: " + pdfDataCollection.getHeader()[1],
                InvoiceWebConstants.font5));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("mobile: " + pdfDataCollection.getHeader()[2],
                InvoiceWebConstants.font5));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase(" ", InvoiceWebConstants.font3));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("Order ID: ", InvoiceWebConstants.font1));
        cell01.setBorder(Rectangle.TOP);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase(String.valueOf(pdfDataCollection.getOrderId()),
                InvoiceWebConstants.font1));
        cell01.setBorder(Rectangle.TOP);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("Billing Address", InvoiceWebConstants.font1));
        cell01.setBorder(Rectangle.TOP);
        cell01.setColspan(3);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("Order Date: ", InvoiceWebConstants.font1));
        cell01.disableBorderSide(Rectangle.BOX);
        headerTable.addCell(cell01);
        cell01 = new PdfPCell(
                new Phrase(pdfDataCollection.getOrderDate(), InvoiceWebConstants.font2));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setHorizontalAlignment(Rectangle.LEFT);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(
                new Phrase(pdfDataCollection.getCustomerName(), InvoiceWebConstants.font2));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(3);
        cell01.setHorizontalAlignment(Rectangle.LEFT);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("VAT/TIN: ", InvoiceWebConstants.font1));
        cell01.disableBorderSide(Rectangle.BOX);
        headerTable.addCell(cell01);
        cell01 = new PdfPCell(
                new Phrase(pdfDataCollection.getCompanyVattin(), InvoiceWebConstants.font2));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setHorizontalAlignment(Rectangle.LEFT);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(
                new Phrase(pdfDataCollection.getCustomerAddress(), InvoiceWebConstants.font2));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(3);
        cell01.setHorizontalAlignment(Rectangle.LEFT);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("CST #: ", InvoiceWebConstants.font1));
        cell01.disableBorderSide(Rectangle.BOX);
        headerTable.addCell(cell01);
        cell01 = new PdfPCell(
                new Phrase(pdfDataCollection.getCompanyCst(), InvoiceWebConstants.font2));
        cell01.disableBorderSide(Rectangle.BOX);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase("Mobile-" + pdfDataCollection.getCustomerMobile()
                + ", Email-" + pdfDataCollection.getCustomerEmail(), InvoiceWebConstants.font2));
        cell01.disableBorderSide(Rectangle.BOX);
        cell01.setColspan(3);
        cell01.setHorizontalAlignment(Rectangle.LEFT);
        headerTable.addCell(cell01);

        cell01 = new PdfPCell(new Phrase(" ", InvoiceWebConstants.font3));
        cell01.setBorder(Rectangle.BOTTOM);
        cell01.setColspan(5);
        headerTable.addCell(cell01);

        PdfPCell cell02 = new PdfPCell(
                new Phrase(" ** " + pdfDataCollection.getFooter()[0], InvoiceWebConstants.font5));
        cell02.disableBorderSide(Rectangle.BOX);
        footerTable.addCell(cell02);
        cell02 = new PdfPCell(
                new Phrase(" ** " + pdfDataCollection.getFooter()[1], InvoiceWebConstants.font5));
        cell02.disableBorderSide(Rectangle.BOX);
        footerTable.addCell(cell02);

        PdfPCell cell03 = new PdfPCell(new Phrase("S No.", InvoiceWebConstants.font1));
        cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell03.setVerticalAlignment(Element.ALIGN_CENTER);
        cell03.setBorder(Rectangle.BOX);
        midSectionTable.addCell(cell03);

        cell03 = new PdfPCell(new Phrase("Product", InvoiceWebConstants.font1));
        cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell03.setVerticalAlignment(Element.ALIGN_CENTER);
        cell03.setBorder(Rectangle.BOX);
        midSectionTable.addCell(cell03);

        cell03 = new PdfPCell(new Phrase("Description", InvoiceWebConstants.font1));
        cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell03.setVerticalAlignment(Element.ALIGN_CENTER);
        cell03.setBorder(Rectangle.BOX);
        midSectionTable.addCell(cell03);

        cell03 = new PdfPCell(new Phrase("Qty (A)", InvoiceWebConstants.font1));
        cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell03.setVerticalAlignment(Element.ALIGN_CENTER);
        cell03.setBorder(Rectangle.BOX);
        midSectionTable.addCell(cell03);

        cell03 = new PdfPCell(new Phrase("Unit Price (B)", InvoiceWebConstants.font1));
        cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell03.setVerticalAlignment(Element.ALIGN_CENTER);
        cell03.setBorder(Rectangle.BOX);
        midSectionTable.addCell(cell03);

        cell03 = new PdfPCell(new Phrase("Amount (A*B)", InvoiceWebConstants.font1));
        cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell03.setVerticalAlignment(Element.ALIGN_CENTER);
        cell03.setBorder(Rectangle.BOX);
        midSectionTable.addCell(cell03);

        if (null != pdfDataCollection && null != pdfDataCollection.getInvoiceModel())
            for (int index = 0; index < pdfDataCollection.getInvoiceModel().size(); index++) {

                PdfPCell cell04 = new PdfPCell(
                        new Phrase(Integer.toString(index + 1), InvoiceWebConstants.font3));
                cell04.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
                cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell04.setVerticalAlignment(Element.ALIGN_CENTER);
                midSectionTable.addCell(cell04);

                cell04 = new PdfPCell(
                        new Phrase(pdfDataCollection.getInvoiceModel().get(index).getProduct(),
                                InvoiceWebConstants.font3));
                cell04.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
                cell04.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell04.setVerticalAlignment(Element.ALIGN_CENTER);
                midSectionTable.addCell(cell04);

                cell04 = new PdfPCell(
                        new Phrase(pdfDataCollection.getInvoiceModel().get(index).getDescription(),
                                InvoiceWebConstants.font3));
                cell04.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
                cell04.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell04.setVerticalAlignment(Element.ALIGN_CENTER);
                midSectionTable.addCell(cell04);

                cell04 = new PdfPCell(new Phrase(
                        Float.toString(
                                pdfDataCollection.getInvoiceModel().get(index).getQuantity()),
                        InvoiceWebConstants.font3));
                cell04.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
                cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell04.setVerticalAlignment(Element.ALIGN_CENTER);
                midSectionTable.addCell(cell04);

                cell04 = new PdfPCell(new Phrase(
                        Float.toString(
                                pdfDataCollection.getInvoiceModel().get(index).getUnitPrice()),
                        InvoiceWebConstants.font3));
                cell04.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
                cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell04.setVerticalAlignment(Element.ALIGN_CENTER);
                midSectionTable.addCell(cell04);

                cell04 = new PdfPCell(new Phrase(
                        Float.toString(pdfDataCollection.getInvoiceModel().get(index).getTotal()),
                        InvoiceWebConstants.font3));
                cell04.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
                cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell04.setVerticalAlignment(Element.ALIGN_CENTER);
                midSectionTable.addCell(cell04);

                amountTotal = amountTotal
                        + pdfDataCollection.getInvoiceModel().get(index).getTotal();
            }

        PdfPCell cell05 = new PdfPCell(new Phrase("Sub Total", InvoiceWebConstants.font1));
        cell05.setBorder(Rectangle.BOX);
        cell05.setColspan(5);
        cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell05.setVerticalAlignment(Element.ALIGN_CENTER);
        midSectionTable.addCell(cell05);

        cell05 = new PdfPCell(new Phrase(Float.toString(amountTotal), InvoiceWebConstants.font1));
        cell05.setBorder(Rectangle.BOX);
        cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell05.setVerticalAlignment(Element.ALIGN_CENTER);
        midSectionTable.addCell(cell05);

        cell05 = new PdfPCell(
                new Phrase(
                        "Final Total = { Sub Total } + { " + pdfDataCollection.getTax()
                                + "% Tax i.e. " + amountTotal * pdfDataCollection.getTax() / 100
                                + " } - { Discount i.e. " + pdfDataCollection.getDiscount() + " }",
                        InvoiceWebConstants.font1));
        cell05.setBorder(Rectangle.BOX);
        cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell05.setVerticalAlignment(Element.ALIGN_CENTER);
        cell05.setColspan(5);
        midSectionTable.addCell(cell05);

        cell05 = new PdfPCell(
                new Phrase(
                        Float.toString(
                                amountTotal + (amountTotal * pdfDataCollection.getTax() / 100)
                                        - pdfDataCollection.getDiscount()),
                        InvoiceWebConstants.font1));
        cell05.setBorder(Rectangle.BOX);
        cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell05.setVerticalAlignment(Element.ALIGN_CENTER);
        midSectionTable.addCell(cell05);

        cell05 = new PdfPCell(new Phrase(" ", InvoiceWebConstants.font1));
        cell05.setColspan(6);
        cell05.disableBorderSide(Rectangle.BOX);
        midSectionTable.addCell(cell05);

        pdfTableList.add(headerTable);
        pdfTableList.add(midSectionTable);
        pdfTableList.add(footerTable);
        return pdfTableList;
    }
}