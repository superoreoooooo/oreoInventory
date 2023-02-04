package win.oreo.inventory.util;

import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.inventory.Inventory.oreoInvItem;
import win.oreo.inventory.Inventory.oreoInventory;
import win.oreo.inventory.Main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class oreoInv {
    public oreoInventory create(String name, int size, HashMap<Integer, oreoInvItem> map) {
        oreoInventory inventory = new oreoInventory(UUID.randomUUID(), name, size, map);
        oreoInventoryUtil.oreoInventories.add(inventory);
        return inventory;
    }

    public oreoInventory create(String name, int size) {
        oreoInventory inventory = new oreoInventory(UUID.randomUUID(), name, size, new HashMap<>());
        oreoInventoryUtil.oreoInventories.add(inventory);
        return inventory;
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
        setName(get(id), name);
    }

    public void setSize(oreoInventory oreoInventory, int size){
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.setInventorySize(size);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setSize(UUID id, int size) {
        setSize(get(id), size);
    }

    public void setItems(oreoInventory oreoInventory, HashMap<Integer, oreoInvItem> map) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.setInventoryMap(map);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setItems(UUID id, HashMap<Integer, oreoInvItem> map) {
        setItems(get(id), map);
    }

    public void setItem(oreoInventory oreoInventory, int index, oreoInvItem item) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.getInventoryMap().put(index, item);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setItem(UUID id, int index, oreoInvItem item) {
        setItem(get(id), index, item);
    }

    public Set<oreoInventory> get(String name) {
        Set<oreoInventory> set = new HashSet<>();
        for (oreoInventory oreoInventory : oreoInventoryUtil.oreoInventories) {
            if (oreoInventory.getInventoryName().equals(name)) {
                set.add(oreoInventory);
            }
        }
        return set;
    }

    public oreoInventory get(UUID uuid) {
        for (oreoInventory oreoInventory : oreoInventoryUtil.oreoInventories) {
            if (oreoInventory.getId().equals(uuid)) return oreoInventory;
        }
        return null;
    }

    public void remove(UUID uuid) {
        Set<UUID> set = new HashSet<>();
        oreoInventoryUtil.oreoInventories.forEach(oreoInventory -> set.add(oreoInventory.getId()));

        if (set.contains(uuid)) {
            Main plugin = JavaPlugin.getPlugin(Main.class);
            plugin.ymlManager.getConfig().set("inventory." + uuid.toString(), null);
            plugin.ymlManager.saveConfig();
            oreoInventoryUtil.oreoInventories.remove(get(uuid));
        }
    }
}
