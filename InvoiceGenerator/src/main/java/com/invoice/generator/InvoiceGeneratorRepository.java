package com.invoice.generator;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceGeneratorRepository {

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection);

    public List<InvoiceModel> fetchProductListFromDB();

    public PdfDataCollectionModel fetchCompanyDetailsFromDB();

    public boolean addProductToDB(InvoiceModel invoiceModel);
}
