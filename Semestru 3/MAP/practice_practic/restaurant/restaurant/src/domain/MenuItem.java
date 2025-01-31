package domain;

public class MenuItem {
    private int ID;
    private String Category;
    private String Item;
    private float Price;
    private String Currency;

    public MenuItem(int ID, String Category, String Item, float Price, String Currency) {
        this.ID = ID;
        this.Category = Category;
        this.Item = Item;
        this.Price = Price;
        this.Currency = Currency;
    }

    public int getID() {
        return ID;
    }

    public String getCategory() {
        return Category;
    }

    public String getItem() {
        return Item;
    }

    public float getPrice() {
        return Price;
    }

    public String getString() {
        return Currency;
    }
}

