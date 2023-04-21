package com.e_commerce.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(customer customer, Product product){
        String groupOrderId = "select max(group_order_id) +1 as id from orders";
        DBConnection connection = new DBConnection();
        try{
            ResultSet rs = connection.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder = "insert into orders(group_order_id, customer_id, product_id) values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return connection.updateDataBase(placeOrder) != 0; // updates the dataBase and returns the number of rows effected.
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static int placeMultipleOrders(customer customer, ObservableList<Product> prodList){
        String groupOrderId = "select max(group_order_id) +1 as id from orders";
        DBConnection connection = new DBConnection();
        try{
            ResultSet rs = connection.getQueryTable(groupOrderId);
            int count = 0;
            if(rs.next()){
                for(Product product : prodList){
                    String placeOrder = "insert into orders(group_order_id, customer_id, product_id) values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count += connection.updateDataBase(placeOrder);
                }
                return count;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
