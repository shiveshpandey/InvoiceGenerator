package com.invoice.generator;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceGeneratorRepository {

	public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection);

	public List<InvoiceModel> fetchProductListAndCompanyDetailsFromDB();
}
