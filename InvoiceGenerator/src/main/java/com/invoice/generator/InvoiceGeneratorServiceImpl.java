package com.invoice.generator;

import java.util.List;

public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

    InvoiceGeneratorRepository invoiceGeneratorRepository = new InvoiceGeneratorRepoImpl();

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection) {
        return invoiceGeneratorRepository.saveInvoiceDetailsToDB(pdfDataCollection);
    }

    public List<InvoiceModel> fetchProductListFromDB(int companyId) {
        return invoiceGeneratorRepository.fetchProductListFromDB(companyId);
    }

    public PdfDataCollectionModel fetchCompanyDetailsFromDB(int companyId) {
        return invoiceGeneratorRepository.fetchCompanyDetailsFromDB(companyId);
    }

    public boolean addProductToDB(InvoiceModel invoiceModel) {
        return invoiceGeneratorRepository.addProductToDB(invoiceModel);
    }

}
