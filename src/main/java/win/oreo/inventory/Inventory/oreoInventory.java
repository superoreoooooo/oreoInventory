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
}
