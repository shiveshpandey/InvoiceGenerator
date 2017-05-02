package com.invoice.generator;

import java.util.List;

public interface InvoiceGeneratorService {

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection);

    public List<InvoiceModel> fetchProductListFromDB(int companyId);

    public boolean addProductToDB(InvoiceModel invoiceModel);

    public PdfDataCollectionModel fetchCompanyDetailsFromDB(int companyId);

}
