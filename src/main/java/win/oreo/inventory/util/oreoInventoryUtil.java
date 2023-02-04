package win.oreo.inventory.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.inventory.Inventory.Enums.ButtonAction;
import win.oreo.inventory.Inventory.oreoInvItem;
import win.oreo.inventory.Inventory.oreoInventory;
import win.oreo.inventory.Inventory.Enums.ItemType;
import win.oreo.inventory.Main;

import java.util.*;

public class oreoInventoryUtil implements Listener {
    public Main plugin;

    public static Set<oreoInventory> oreoInventories = new HashSet<>();

    public void initialize() {
        plugin = JavaPlugin.getPlugin(Main.class);
        for (String idSting : plugin.ymlManager.getConfig().getConfigurationSection("inventory.").getKeys(false)) {
            UUID id = UUID.fromString(idSting);
            String name = plugin.ymlManager.getConfig().getString("inventory." + idSting + ".name");
            int size = plugin.ymlManager.getConfig().getInt("inventory." + idSting + ".size");
            HashMap<Integer, oreoInvItem> map = new HashMap<>();
            for (String index : plugin.ymlManager.getConfig().getConfigurationSection("inventory." + idSting + ".item.").getKeys(false)) {
                ItemStack itemStack = plugin.ymlManager.getConfig().getItemStack("inventory." + idSting + ".item." + index + ".itemStack");
                ItemType itemType = ItemType.valueOf(plugin.ymlManager.getConfig().getString("inventory." + idSting + ".item." + index + ".itemType"));
                oreoInvItem item;
                ButtonAction action = ButtonAction.valueOf(plugin.ymlManager.getConfig().getString("inventory." + idSting + ".item." + index + ".action"));
                if (itemType.equals(ItemType.DEAL)) {
                    int price = plugin.ymlManager.getConfig().getInt("inventory." + idSting + ".item." + index + ".price");
                    item = new oreoInvItem(itemStack, itemType, price, action);
                }
                else {
                    item = new oreoInvItem(itemStack, itemType, 0, action);
                }
                map.put(Integer.valueOf(index), item);
            }
            oreoInventory oreoInventory = new oreoInventory(id, name, size, map);
            oreoInventories.add(oreoInventory);
            Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "LOADED INVENTORY (ID : " + oreoInventory.getId().toString() + " NAME : " + oreoInventory.getInventoryName() + " SIZE : " + oreoInventory.getInventorySize() + " MAP : " + oreoInventory.getInventoryMap() + ")");
        }
    }

    public void save() {
        plugin = JavaPlugin.getPlugin(Main.class);
        for (oreoInventory oreoInventory : oreoInventories) {
            plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".name", oreoInventory.getInventoryName());
            plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".size", oreoInventory.getInventorySize());
            for (int index : oreoInventory.getInventoryMap().keySet()) {
                plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".item." + index + ".itemStack", oreoInventory.getInventoryMap().get(index).getItemStack());
                plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".item." + index + ".itemType", oreoInventory.getInventoryMap().get(index).getItemType().toString());
                if (oreoInventory.getInventoryMap().get(index).getItemType().equals(ItemType.DEAL)) {
                    plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".item." + index + ".price", oreoInventory.getInventoryMap().get(index).getPrice());
                }
                plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".item." + index + ".action", oreoInventory.getInventoryMap().get(index).getAction().toString());
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "SAVED INVENTORY (ID : " + oreoInventory.getId().toString() + " NAME : " + oreoInventory.getInventoryName() + " SIZE : " + oreoInventory.getInventorySize() + " MAP : " + oreoInventory.getInventoryMap() + ")");
        }
        plugin.ymlManager.saveConfig();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }
        for (oreoInventory inventory : oreoInventories) {
            if (inventory.getInventory().equals(e.getClickedInventory())) {
                e.setCancelled(true);
            } else if (inventory.getInventory().equals(e.getInventory())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        for (oreoInventory inventory : oreoInventories) {
            if (inventory.getInventory().equals(e.getInventory())) {
                if (e.getInventory().getSize() > Collections.min(e.getRawSlots())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
