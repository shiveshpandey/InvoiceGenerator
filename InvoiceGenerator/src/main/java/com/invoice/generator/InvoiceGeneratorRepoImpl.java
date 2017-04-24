package com.invoice.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGeneratorRepoImpl implements InvoiceGeneratorRepository {

    public boolean saveInvoiceDetailsToDB(PdfDataCollectionModel pdfDataCollection) {
        try {
            Class.forName(InvoiceGeneratorQuery.DB_DRIVER_PKG_PATH);
            Connection conn;
            conn = DriverManager.getConnection(InvoiceGeneratorQuery.DB_CONNECTION_URL,
                    InvoiceGeneratorQuery.DB_USER, InvoiceGeneratorQuery.DB_PASSWRD);
            Statement stmt;
            stmt = conn.createStatement();
            String query = null;
            float totalOrderPrice = 0;
            for (int count = 0; count < pdfDataCollection.getInvoiceModel().size(); count++) {
                query = new InvoiceGeneratorQuery().insertQueryOrderDetails(
                        pdfDataCollection.getOrderId(),
                        pdfDataCollection.getInvoiceModel().get(count));
                stmt.executeUpdate(query);
                totalOrderPrice = totalOrderPrice
                        + Float.valueOf(pdfDataCollection.getInvoiceModel().get(count).getTotal());
            }
            query = new InvoiceGeneratorQuery().insertQueryCustomerDetails(pdfDataCollection,
                    Float.toString(totalOrderPrice));
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

    public List<InvoiceModel> fetchProductListFromDB() {
        List<InvoiceModel> invoiceList = new ArrayList<InvoiceModel>();
        try {
            Class.forName(InvoiceGeneratorQuery.DB_DRIVER_PKG_PATH);
            Connection conn;
            conn = DriverManager.getConnection(InvoiceGeneratorQuery.DB_CONNECTION_URL,
                    InvoiceGeneratorQuery.DB_USER, InvoiceGeneratorQuery.DB_PASSWRD);
            Statement stmt;
            stmt = conn.createStatement();
            String query = new InvoiceGeneratorQuery().selectQueryProductDetails();
            ResultSet rs = stmt.executeQuery(query);

            InvoiceModel invoice = null;
            while (rs.next()) {
                invoice = new InvoiceModel();
                invoice.setProductId(rs.getString("product_id"));
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

    public PdfDataCollectionModel fetchCompanyDetailsFromDB() {
        PdfDataCollectionModel PdfDataCollectionModel = null;
        try {
            Class.forName(InvoiceGeneratorQuery.DB_DRIVER_PKG_PATH);
            Connection conn;
            conn = DriverManager.getConnection(InvoiceGeneratorQuery.DB_CONNECTION_URL,
                    InvoiceGeneratorQuery.DB_USER, InvoiceGeneratorQuery.DB_PASSWRD);
            Statement stmt;
            stmt = conn.createStatement();
            String query = new InvoiceGeneratorQuery().selectQueryCompanyDetails();
            ResultSet rs = stmt.executeQuery(query);
            PdfDataCollectionModel = new PdfDataCollectionModel();

            PdfDataCollectionModel.setCompanyId(rs.getString("company_id"));
            PdfDataCollectionModel.setCompanyName(rs.getString("company_name"));
            PdfDataCollectionModel.setCompanyAddress(rs.getString("company_address"));
            PdfDataCollectionModel.setCompanyMobile(rs.getString("company_mobile"));
            PdfDataCollectionModel.setCompanyVattin(rs.getString("company_vattin"));
            PdfDataCollectionModel.setCompanyCst(rs.getString("company_cst"));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return PdfDataCollectionModel;
    }
}
