package com.invoice.generator;

import java.util.List;

public class PdfDataCollectionModel {
    private String orderId;
    private String companyId;
    private String companyName;
    private String companyAddress;
    private String companyMobile;
    private String companyVattin;
    private String companyCst;
    private String customerName;
    private String customerAddress;
    private String customerMobile;
    private String customerEmail;
    private String orderDate;
    private String[] header;
    private String[] footer;
    List<InvoiceModel> invoiceModel;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public String[] getFooter() {
        return footer;
    }

    public void setFooter(String[] b) {
        this.footer = b;
    }

    public List<InvoiceModel> getInvoiceModel() {
        return invoiceModel;
    }

    public void setInvoiceModel(List<InvoiceModel> invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getCompanyVattin() {
        return companyVattin;
    }

    public void setCompanyVattin(String companyVattin) {
        this.companyVattin = companyVattin;
    }

    public String getCompanyCst() {
        return companyCst;
    }

    public void setCompanyCst(String companyCst) {
        this.companyCst = companyCst;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}
