package com.invoice.generator;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface InvoiceGeneratorService {

	public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection);

	public List<InvoiceModel> fetchProductListAndCompanyDetailsFromDB();

}
