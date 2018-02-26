package com.simplevoting.menuvoting.to;

import java.time.LocalDate;

public class VoteTo {
    private LocalDate date;
    private int userId;
    private int menu_id;

    public VoteTo() {
    }

    public VoteTo(LocalDate date, int user_id, int menu_id) {
        this.date = date;
        this.userId = user_id;
        this.menu_id = menu_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    @Override
    public String toString() {
        return String.format("VoteTo{date: %s, userID: %s, menuID: %s}", date, userId, menu_id);
    }
}
