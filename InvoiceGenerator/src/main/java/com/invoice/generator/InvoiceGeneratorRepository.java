package com.invoice.generator;

import java.util.List;

public interface InvoiceGeneratorRepository {

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection);

    public List<InvoiceModel> fetchProductListFromDB();
}
