package com.e_commerce.ecommerce;

import java.sql.ResultSet;

public class Login {
    public customer customerLogin(String username, String password){
        String loginQuery = "Select * from customer where email = '"+username+"' And password = '"+password+"'";
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getQueryTable(loginQuery);

        try{
            if(rs.next())
                return new customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("mobile"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        Login login = new Login();
//        customer customer = login.customerLogin("bhxnusingh0605@gmail.com","passkey");
//        if(customer == null)
//            System.out.println("Invalid password or email");
//        else{
//            System.out.println("Welcome : "+customer.getName());
//        }
//    }
}
