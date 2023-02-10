package win.oreo.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import win.oreo.inventory.Inventory.Enums.ButtonAction;
import win.oreo.inventory.Inventory.Enums.ItemType;

public class oreoItem {
    private ItemStack itemStack;
    private ItemType itemType;
    private int price;
    private ButtonAction action;

    public oreoItem(ItemStack itemStack, ItemType itemType, int price, ButtonAction action) {
        this.itemStack = itemStack;
        this.itemType = itemType;
        this.price = price;
        this.action = action;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getPrice() {
        return price;
    }

    public ButtonAction getAction() {
        return action;
    }
}
