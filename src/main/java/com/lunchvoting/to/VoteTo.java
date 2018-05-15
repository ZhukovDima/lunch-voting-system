package com.lunchvoting.to;

public class VoteTo {

    private int menuId;

    public VoteTo() {
    }

    public VoteTo(int menuId) {
        this.menuId = menuId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "menuId=" + menuId +
                '}';
    }
}
