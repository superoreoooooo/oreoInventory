package win.oreo.inventory.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import win.oreo.inventory.Inventory.Enums.ButtonAction;
import win.oreo.inventory.Inventory.Enums.ItemType;
import win.oreo.inventory.Inventory.oreoInvItem;

import java.util.ArrayList;
import java.util.List;

public class oreoItem {
    public static oreoInvItem create(Material itemMaterial, String itemName, String lore, ItemType itemType, int price, ButtonAction action) {
        ItemStack item = new ItemStack(itemMaterial, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        List<String> lores = new ArrayList<>();
        lores.add(lore);
        meta.setLore(lores);
        item.setItemMeta(meta);

        return new oreoInvItem(item, itemType, price, action);
    }
}
