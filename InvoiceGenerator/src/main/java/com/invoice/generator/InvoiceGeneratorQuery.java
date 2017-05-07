package com.invoice.generator;

public class InvoiceGeneratorQuery {
    public static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/MYSQL";
    public static String DB_USER = "root";
    public static String DB_PASSWRD = "root";
    public static String DB_DRIVER_PKG_PATH = "com.mysql.jdbc.Driver";

    public String insertQueryCustomerDetails(PdfDataCollectionModel pdfDataCollection,
            String totalOrderPrice) {
        return "INSERT INTO INVOICE_GENERATOR_SCHEMA.CUSTOMER_DETAILS"
                + " (order_id,company_id ,customer_name, customer_address, customer_mobile, customer_email,order_date,product_tax,product_discount,order_total_price)"
                + " VALUES ('" + pdfDataCollection.getOrderId() + "', '"
                + pdfDataCollection.getCompanyId() + "' ,'" + pdfDataCollection.getCustomerName()
                + "', '" + pdfDataCollection.getCustomerAddress() + "','"
                + pdfDataCollection.getCustomerMobile() + "','"
                + pdfDataCollection.getCustomerEmail() + "','" + pdfDataCollection.getOrderDate()
                + "','" + pdfDataCollection.getTax() + "','" + pdfDataCollection.getDiscount()
                + "','" + totalOrderPrice + "');";
    }

    public String insertQueryOrderDetails(int orderId, InvoiceModel invoiceModel) {
        return "INSERT INTO INVOICE_GENERATOR_SCHEMA.ORDER_DETAILS "
                + "(order_id,product_id ,product_name, product_description, product_quantity,product_total)"
                + " VALUES (" + orderId + ", " + invoiceModel.getProductId() + ", '"
                + invoiceModel.getProduct() + "', '" + invoiceModel.getDescription() + "','"
                + invoiceModel.getQuantity() + "','" + invoiceModel.getTotal() + "');";
    }

    public String selectQueryProductDetails(int companyId) {

        return "select product_id,product_name, product_description, product_quantity, product_total,product_unit_price  from INVOICE_GENERATOR_SCHEMA.PRODUCT_DETAILS where company_id ="
                + companyId + ";";
    }

    public String selectQueryCompanyDetails(int companyId) {
        return "select company_id,company_name,company_address,company_mobile,company_vattin,company_cst  from INVOICE_GENERATOR_SCHEMA.COMPANY_DETAILS where company_id ="
                + companyId + ";";
    }

    public String insertQueryAddProductToDB(InvoiceModel invoiceModel) {
        return "INSERT INTO INVOICE_GENERATOR_SCHEMA.Product_DETAILS "
                + "(company_id,product_id ,product_name, product_description, product_quantity,product_unit_price)"
                + " VALUES ('" + invoiceModel.getCompanyId() + "','" + invoiceModel.getProductId()
                + "', '" + invoiceModel.getProduct() + "', '" + invoiceModel.getDescription()
                + "','" + invoiceModel.getQuantity() + "','" + invoiceModel.getUnitPrice() + "');";
    }

    public String getNextOrderIdSequence() {
        return "SELECT MAX(order_id) FROM INVOICE_GENERATOR_SCHEMA.ORDER_DETAILS;";
    }

    public String getNextProductIdSequence() {
        return "SELECT MAX(product_id) FROM INVOICE_GENERATOR_SCHEMA.Product_DETAILS;";
    }
}
