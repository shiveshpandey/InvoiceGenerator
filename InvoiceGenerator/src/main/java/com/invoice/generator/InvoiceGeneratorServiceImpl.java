package com.invoice.generator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {
	@Autowired
	InvoiceGeneratorRepository invoiceGeneratorRepository;

	public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection) {
		return invoiceGeneratorRepository.saveInvoiceDetailsToDB(pdfDataCollection);
	}

	public List<InvoiceModel> fetchProductListAndCompanyDetailsFromDB() {
		return invoiceGeneratorRepository.fetchProductListAndCompanyDetailsFromDB();
	}

}
