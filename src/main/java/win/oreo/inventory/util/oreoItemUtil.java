package win.oreo.inventory.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import win.oreo.inventory.Inventory.Enums.ButtonAction;
import win.oreo.inventory.Inventory.Enums.ItemType;
import win.oreo.inventory.Inventory.oreoItem;

import java.util.ArrayList;
import java.util.List;

public class oreoItemUtil {
    public static oreoItem create(Material itemMaterial, String itemName, List<String> lore, ItemType itemType, ButtonAction action) {
        ItemStack item = new ItemStack(itemMaterial, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return new oreoItem(item, itemType, action);
    }
}
