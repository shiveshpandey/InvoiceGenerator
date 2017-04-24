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
    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAME", "usrname",
                    "pswd");
            Statement stmt;
            stmt = conn.createStatement();
            String query = "INSERT INTO INVOICE_GENERATOR_DB.CUSTOMER_DETAILS (order_id,company_id ,customer_name, customer_address, customer_mobile, customer_email,order_date,order_total_price) VALUES (?, ?, ?, ?,?,?,?,?);";
            ResultSet rs = stmt.executeQuery(query);
            query = "INSERT INTO INVOICE_GENERATOR_DB.ORDER_DETAILS (order_id,product_id ,product_name, product_description, product_quantity, product_tax,product_discount,product_total) VALUES (?, ?, ?, ?,?,?,?,?);";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    public List<InvoiceModel> fetchProductListAndCompanyDetailsFromDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAME", "usrname",
                    "pswd");
            Statement stmt;
            stmt = conn.createStatement();
            String query = "select company_name,company_address,company_mobile,company_vattin,company_cst  from INVOICE_GENERATOR_DB.COMPANY_DETAILS where company_id=1;";
            ResultSet rs = stmt.executeQuery(query);
            query = "select product_name, product_description, product_quantity, product_tax,product_discount,product_total  from INVOICE_GENERATOR_DB.PRODUCT_DETAILS where company_id=1;";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<InvoiceModel>();
    }
}
