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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAME", "usrname", "pswd");
			Statement stmt;
			stmt = conn.createStatement();
			String query = "select columnname from tablename ;";
			ResultSet rs = stmt.executeQuery(query);
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBNAME", "usrname", "pswd");
			Statement stmt;
			stmt = conn.createStatement();
			String query = "select columnname from tablename ;";
			ResultSet rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<InvoiceModel>();
	}
}
