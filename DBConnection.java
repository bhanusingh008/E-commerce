package com.e_commerce.ecommerce;
import java.sql.*;

public class DBConnection {

    private final String dbUrl = "jdbc:mysql://localhost : 3306/ecommerce";
    private final String userName = "root";
    private final String password = "root@sql";

    private Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(dbUrl, userName, password);
            return connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query) {
        try{
            Statement statement = getStatement();
            assert statement != null;
            return statement.executeQuery(query);
        }catch (Exception e){
            System.out.println("Database does not exist");
            e.printStackTrace();
        }
        return null;
    }

    public int updateDataBase(String query){
        try{
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        }catch (Exception e){
            System.out.println("Database does not exist");
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getQueryTable("Select * from customer");
        if(rs != null)
        {
            System.out.println("Connection Successful");
        }
        else{
            System.out.println("Connection Failed");
        }

    }
}
