package com.simplevoting.menuvoting.service;

public interface MenuListService {

    void addDish(int menu_id, int dish_id, double price);

    void removeDish(int menu_id, int dish_id);

}
