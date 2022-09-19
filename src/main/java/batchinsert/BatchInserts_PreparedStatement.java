package batchinsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class BatchInserts_PreparedStatement {
    public static void main(String args[])throws Exception {
        //Getting the connection
        String mysqlUrl = "jdbc:mysql://localhost/testDB";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
        System.out.println("Connection established......");
        //Creating a Statement object
        Statement stmt = con.createStatement();
        //Setting auto-commit false
        con.setAutoCommit(false);
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO Sales VALUES (?, ?, ?, ?, ?)");
        pstmt.setString(1, "KeyBoard");
        pstmt.setString(2, "Amith");
        pstmt.setString(3, "January");
        pstmt.setInt(4, 1000);
        pstmt.setString(5, "Hyderabad");
        pstmt.addBatch();
        pstmt.setString(1, "Earphones");
        pstmt.setString(2, "Sumith");
        pstmt.setString(3, "March");
        pstmt.setInt(4, 500);
        pstmt.setString(5, "Vishakhapatnam");
        pstmt.addBatch();
        pstmt.setString(1, "Mouse");
        pstmt.setString(2, "Sudha");
        pstmt.setString(3, "September");
        pstmt.setInt(4, 500);
        pstmt.setString(5, "Vishakhapatnam");
        pstmt.addBatch();
        pstmt.addBatch();
        //Executing the batch
        stmt.executeBatch();
        //Saving the changes
        con.commit();
        System.out.println("Records inserted......");
    }
}
