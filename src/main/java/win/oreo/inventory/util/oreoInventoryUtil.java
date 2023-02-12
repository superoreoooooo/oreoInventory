package win.oreo.inventory.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.inventory.Inventory.Enums.ButtonAction;
import win.oreo.inventory.Inventory.oreoItem;
import win.oreo.inventory.Inventory.oreoInventory;
import win.oreo.inventory.Inventory.Enums.ItemType;
import win.oreo.inventory.Main;
import win.oreo.inventory.listener.oreoInventoryClickEvent;

import java.util.*;

import static win.oreo.inventory.Main.prefix;

public class oreoInventoryUtil implements Listener {
    public Main plugin;
    public static Set<oreoInventory> oreoInventories = new HashSet<>();

    public void initialize() {
        plugin = JavaPlugin.getPlugin(Main.class);
        for (String idSting : plugin.ymlManager.getConfig().getConfigurationSection("inventory.").getKeys(false)) {
            UUID id = UUID.fromString(idSting);
            String name = plugin.ymlManager.getConfig().getString("inventory." + idSting + ".name");
            int size = plugin.ymlManager.getConfig().getInt("inventory." + idSting + ".size");
            HashMap<Integer, oreoItem> map = new HashMap<>();
            for (String index : plugin.ymlManager.getConfig().getConfigurationSection("inventory." + idSting + ".item.").getKeys(false)) {
                ItemStack itemStack = plugin.ymlManager.getConfig().getItemStack("inventory." + idSting + ".item." + index + ".itemStack");
                ItemType itemType = ItemType.valueOf(plugin.ymlManager.getConfig().getString("inventory." + idSting + ".item." + index + ".itemType"));
                ButtonAction action = ButtonAction.valueOf(plugin.ymlManager.getConfig().getString("inventory." + idSting + ".item." + index + ".action"));
                map.put(Integer.valueOf(index), new oreoItem(itemStack, itemType, action));
            }
            oreoInventory oreoInventory = new oreoInventory(id, name, size, map);
            oreoInventories.add(oreoInventory);
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.LIGHT_PURPLE + "INVENTORY LOADED" + ChatColor.WHITE + " (ID : " + oreoInventory.getId().toString() + " NAME : " + oreoInventory.getInventoryName() + " SIZE : " + oreoInventory.getInventorySize() + " MAP : " + oreoInventory.getInventoryMap() + ")");
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
                plugin.ymlManager.getConfig().set("inventory." + oreoInventory.getId().toString() + ".item." + index + ".action", oreoInventory.getInventoryMap().get(index).getAction().toString());
            }
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.LIGHT_PURPLE + "INVENTORY SAVED" + ChatColor.WHITE + " (ID : " + oreoInventory.getId().toString() + " NAME : " + oreoInventory.getInventoryName() + " SIZE : " + oreoInventory.getInventorySize() + " MAP : " + oreoInventory.getInventoryMap() + ")");
        }
        plugin.ymlManager.saveConfig();
    }

    public oreoInventory create(String name, int size, HashMap<Integer, oreoItem> map) {
        return create(new oreoInventory(UUID.randomUUID(), name, size, map));
    }

    public oreoInventory create(String name, int size) {
        return create(new oreoInventory(UUID.randomUUID(), name, size, new HashMap<>()));
    }

    public oreoInventory create(oreoInventory oreoInventory) {
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
        return oreoInventory;
    }

    public void setName(oreoInventory oreoInventory, String name) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.setInventoryName(name);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setName(UUID id, String name) {
        setName(getByUUID(id), name);
    }

    public void setSize(oreoInventory oreoInventory, int size){
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.setInventorySize(size);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setSize(UUID id, int size) {
        setSize(getByUUID(id), size);
    }

    public void setItems(oreoInventory oreoInventory, HashMap<Integer, oreoItem> map) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.setInventoryMap(map);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setItems(UUID id, HashMap<Integer, oreoItem> map) {
        setItems(getByUUID(id), map);
    }

    public void setItem(oreoInventory oreoInventory, int index, oreoItem item) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.getInventoryMap().put(index, item);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setItem(UUID id, int index, oreoItem item) {
        setItem(getByUUID(id), index, item);
    }

    public Set<oreoInventory> getByName(String name) {
        Set<oreoInventory> set = new HashSet<>();
        for (oreoInventory oreoInventory : oreoInventoryUtil.oreoInventories) {
            if (oreoInventory.getInventoryName().equals(name)) {
                set.add(oreoInventory);
            }
        }
        return set;
    }

    public oreoInventory getByUUID(UUID uuid) {
        for (oreoInventory oreoInventory : oreoInventoryUtil.oreoInventories) {
            if (oreoInventory.getId().equals(uuid)) return oreoInventory;
        }
        return null;
    }

    public void remove(UUID uuid) {
        oreoInventory inv = getByUUID(uuid);
        if (inv == null) return;

        plugin = JavaPlugin.getPlugin(Main.class);
        plugin.ymlManager.getConfig().set("inventory." + uuid.toString(), null);
        plugin.ymlManager.saveConfig();

        oreoInventories.remove(inv);
    }

    public void clear() {
        oreoInventories.clear();
        save();
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            for (oreoInventory inventory : oreoInventories) {
                if (inventory.getInventory().equals(e.getClickedInventory()) || inventory.getInventory().equals(e.getInventory())) {
                    e.setCancelled(true);
                    oreoItem clickedItem = inventory.getInventoryMap().get(e.getSlot());
                    if (clickedItem != null && !clickedItem.getItemType().equals(ItemType.UI)) {
                        Bukkit.getPluginManager().callEvent(new oreoInventoryClickEvent((Player) e.getWhoClicked(), inventory, clickedItem));
                    }
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
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
