package com.example.graduationproject.Interface;

import com.example.graduationproject.Model.Product;

public interface ProductsAction {
    void OnDelete(String name,int pos);
    void OnUpdate(Product product);
    public void OnClickItem(Product product);


}
