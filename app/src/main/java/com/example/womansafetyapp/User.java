package com.example.womansafetyapp;

public class User {

   public String Name,Username,Mail,Address,Phone,Contact;
   public boolean loginState=false;

   public User(){

   }

   public User(String Name,String Username,String Mail,String Address,String Phone,String Contact){
       this.Name=Name;
       this.Username=Username;
       this.Mail=Mail;
       this.Address=Address;
       this.Phone=Phone;
       this.Contact=Contact;

   }


}
