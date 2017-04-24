package com.invoice.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGeneratorRepoImpl implements InvoiceGeneratorRepository {

    @SuppressWarnings("unused")
    // CREATE TABLE invoice_generator_schema.product_details (company_id varchar(255),product_id
    // varchar(255),product_name varchar(255), product_description varchar(255), product_quantity
    // varchar(255), product_tax varchar(255),product_discount varchar(255),product_total
    // varchar(255));

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MYSQL", "root", "root");
            Statement stmt;
            stmt = conn.createStatement();
            String query = "INSERT INTO INVOICE_GENERATOR_SCHEMA.CUSTOMER_DETAILS (order_id,company_id ,customer_name, customer_address, customer_mobile, customer_email,order_date,order_total_price) VALUES ('12345', '12345' ,'12345', '12345','12345','12345','12345','12345');";
            ResultSet rs = stmt.executeQuery(query);
            query = "INSERT INTO INVOICE_GENERATOR_SCHEMA.ORDER_DETAILS (order_id,product_id ,product_name, product_description, product_quantity, product_tax,product_discount,product_total) VALUES ('12345', '12345', '12345', '12345','12345','12345','12345','12345');";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<InvoiceModel> fetchProductListAndCompanyDetailsFromDB() {
        List<InvoiceModel> invoiceList = new ArrayList<InvoiceModel>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MYSQL", "root", "root");
            Statement stmt;
            stmt = conn.createStatement();
            String query = "select company_name,company_address,company_mobile,company_vattin,company_cst  from INVOICE_GENERATOR_SCHEMA.COMPANY_DETAILS where company_id like '%company_1%';";
            ResultSet rs = stmt.executeQuery(query);

            query = "select product_name, product_description, product_quantity, product_tax,product_discount,product_total  from INVOICE_GENERATOR_SCHEMA.PRODUCT_DETAILS where company_id like '%company_1%';";
            rs = stmt.executeQuery(query);

            InvoiceModel invoice = null;
            while (rs.next()) {
                invoice = new InvoiceModel();
                invoice.setProduct(rs.getString("product_name"));
                invoice.setDescription(rs.getString("product_description"));
                invoice.setQuantity(Float.parseFloat(rs.getString("product_quantity")));
                invoice.setTax(Float.parseFloat(rs.getString("product_tax")));
                invoice.setDiscount(Float.parseFloat(rs.getString("product_discount")));
                invoice.setTotal(Float.parseFloat(rs.getString("product_total")));
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return invoiceList;
    }
}
