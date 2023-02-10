package win.oreo.inventory.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.inventory.Main;
import win.oreo.inventory.util.oreoInventoryUtil;

import java.util.*;

public class oreoInventory implements InventoryHolder {

    private Inventory inv;
    private UUID id;
    private String inventoryName;
    private int inventorySize;
    private HashMap<Integer, oreoItem> inventoryMap; //integer : index | oreoItem : item

    public oreoInventory(UUID id, String inventoryName, int inventorySize, HashMap<Integer, oreoItem> inventoryMap) {
        this.id = id;
        this.inventoryName = inventoryName;
        this.inventorySize = inventorySize;
        this.inventoryMap = inventoryMap;
        this.init();
    }

    private void init() {
        inv = Bukkit.createInventory(this, getInventorySize(), getInventoryName());
        for (int index : inventoryMap.keySet()) {
            oreoItem oreoItem = inventoryMap.get(index);
            inv.setItem(index, oreoItem.getItemStack());
        }
    }

    public UUID getId() {
        return id;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public HashMap<Integer, oreoItem> getInventoryMap() {
        return inventoryMap;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public void setInventoryMap(HashMap<Integer, oreoItem> inventoryMap) {
        this.inventoryMap = inventoryMap;
    }

    public oreoInventory create(String name, int size, HashMap<Integer, oreoItem> map) {
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

    public void setItems(oreoInventory oreoInventory, HashMap<Integer, oreoItem> map) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.setInventoryMap(map);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setItems(UUID id, HashMap<Integer, oreoItem> map) {
        setItems(get(id), map);
    }

    public void setItem(oreoInventory oreoInventory, int index, oreoItem item) {
        oreoInventoryUtil.oreoInventories.remove(oreoInventory);
        oreoInventory.getInventoryMap().put(index, item);
        oreoInventoryUtil.oreoInventories.add(oreoInventory);
    }

    public void setItem(UUID id, int index, oreoItem item) {
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
