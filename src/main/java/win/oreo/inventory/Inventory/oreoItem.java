package win.oreo.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import win.oreo.inventory.Inventory.Enums.ButtonAction;
import win.oreo.inventory.Inventory.Enums.ItemType;

public class oreoItem {
    private ItemStack itemStack;
    private ItemType itemType;
    private ButtonAction action;

    public oreoItem(ItemStack itemStack, ItemType itemType, ButtonAction action) {
        this.itemStack = itemStack;
        this.itemType = itemType;
        this.action = action;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public ButtonAction getAction() {
        return action;
    }
}
