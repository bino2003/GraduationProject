package com.example.graduationproject.Model;

   public class users {
    String name;
    String phone;
    String password;
    String rePassword;
    String location;
       String latitude;
       String longitude;
    String categorize;

       public users(String name, String phone, String password, String rePassword, String location, String latitude, String longitude, String categorize) {
           this.name = name;
           this.phone = phone;
           this.password = password;
           this.rePassword = rePassword;
           this.location = location;
           this.latitude = latitude;
           this.longitude = longitude;
           this.categorize = categorize;
       }

       public String getLatitude() {
           return latitude;
       }

       public void setLatitude(String latitude) {
           this.latitude = latitude;
       }

       public String getLongitude() {
           return longitude;
       }

       public void setLongitude(String longitude) {
           this.longitude = longitude;
       }



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
                   "name='" + name + '\'' +
                   ", phone='" + phone + '\'' +
                   ", password='" + password + '\'' +
                   ", rePassword='" + rePassword + '\'' +
                   ", location='" + location + '\'' +
                   ", latitude='" + latitude + '\'' +
                   ", longitude='" + longitude + '\'' +
                   ", categorize='" + categorize + '\'' +
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
