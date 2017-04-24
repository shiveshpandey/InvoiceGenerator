package com.invoice.generator;

import java.util.List;

public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

    InvoiceGeneratorRepository invoiceGeneratorRepository = new InvoiceGeneratorRepoImpl();

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection) {
        return invoiceGeneratorRepository.saveInvoiceDetailsToDB(pdfDataCollection);
    }

    public List<InvoiceModel> fetchProductListFromDB() {
        return invoiceGeneratorRepository.fetchProductListFromDB();
    }

    public PdfDataCollectionModel fetchCompanyDetailsFromDB() {
        return invoiceGeneratorRepository.fetchCompanyDetailsFromDB();
    }

}
