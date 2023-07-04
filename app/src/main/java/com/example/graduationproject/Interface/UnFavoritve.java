package com.example.graduationproject.Interface;

import com.example.graduationproject.Model.Favorite2;
import com.example.graduationproject.Model.Favorites;

public interface UnFavoritve {
    public void OnDelete(Favorite2 favorites, int post);
    public void OnClickItem(String id,String id_product);

    void OnDelete(int pos, String product_id);
}
