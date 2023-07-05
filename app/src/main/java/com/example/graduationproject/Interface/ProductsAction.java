package com.example.graduationproject.Interface;

import com.example.graduationproject.Model.Product;
import com.example.graduationproject.Model.Product2;

public interface ProductsAction {
    void OnDelete(String name,int pos);
    void OnUpdate(Product2 product);
    public void OnClickItem(Product2 product);


}
