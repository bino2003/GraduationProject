package com.example.graduationproject.model;

   public class users {
    String name;
    String phone;
    String password;
    String rePassword;
    String location;
    String categorize;

    public users(String name, String phone, String password, String rePassword) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.rePassword = rePassword;
    }

    public users() {

    }

       public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getLocation() {
        return location;
    }

       @Override
       public String toString() {
           return "users{" +
                   "categorize='" + categorize + '\'' +
                   '}';
       }

       public void setLocation(String location) {
        this.location = location;
    }

       public String getCategorize() {
           return categorize;
       }

       public void setCategorize(String categorize) {
           this.categorize = categorize;
       }

       public users(String name, String phone, String password, String rePassword, String location, String categorize) {
           this.name = name;
           this.phone = phone;
           this.password = password;
           this.rePassword = rePassword;
           this.location = location;
           this.categorize = categorize;
       }
   }
