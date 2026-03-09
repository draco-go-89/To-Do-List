package com.lubolabao.todolist;

public class Skin {
    private int id;
    private String name;
    private int drawableId;
    private int price;
    private String rarity;
    private boolean isUnlocked;

    public Skin(int id, String name, int drawableId, int price, String rarity, boolean isUnlocked) {
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
        this.price = price;
        this.rarity = rarity;
        this.isUnlocked = isUnlocked;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDrawableId() { return drawableId; }
    public int getPrice() { return price; }
    public String getRarity() { return rarity; }
    public boolean isUnlocked() { return isUnlocked; }
    public void setUnlocked(boolean unlocked) { isUnlocked = unlocked; }
}
