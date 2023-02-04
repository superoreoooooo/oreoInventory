package win.oreo.inventory.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class oreoInventory implements InventoryHolder {

    private Inventory inv;
    private UUID id;
    private String inventoryName;
    private int inventorySize;
    private HashMap<Integer, oreoInvItem> inventoryMap; //integer : index | oreoItem : item

    public oreoInventory(UUID id, String inventoryName, int inventorySize, HashMap<Integer, oreoInvItem> inventoryMap) {
        this.id = id;
        this.inventoryName = inventoryName;
        this.inventorySize = inventorySize;
        this.inventoryMap = inventoryMap;
        this.init();
    }

    private void init() {
        inv = Bukkit.createInventory(this, getInventorySize(), getInventoryName());
        for (int index : inventoryMap.keySet()) {
            oreoInvItem oreoInvItem = inventoryMap.get(index);
            switch (oreoInvItem.getItemType()) {
                case DEAL:
                    ItemMeta meta = oreoInvItem.getItemStack().getItemMeta();
                    List<String> lore;
                    if (meta.hasLore()) {
                        lore = meta.getLore();
                        for (String str : lore) {
                            if (!str.contains("원")) {
                                lore.add(oreoInvItem.getPrice() + "원");
                            }
                        }
                    } else {
                        lore = new ArrayList<>();
                        lore.add(oreoInvItem.getPrice() + "원");
                    }
                    meta.setLore(lore);
                    oreoInvItem.getItemStack().setItemMeta(meta);
                    break;
                case BUTTON:
                    ItemMeta bMeta = oreoInvItem.getItemStack().getItemMeta();
                    List<String> bLore;
                    if (oreoInvItem.getItemStack().getItemMeta().hasLore()) {
                        bLore = oreoInvItem.getItemStack().getLore();
                        for (String str : bLore) {
                            if (!str.equals("버튼")) {
                                bLore.add("버튼");
                            }
                        }
                    } else {
                        bLore = new ArrayList<>();
                        bLore.add("버튼");
                    }
                    bMeta.setLore(bLore);
                    oreoInvItem.getItemStack().setItemMeta(bMeta);
                    break;
            }
            inv.setItem(index, oreoInvItem.getItemStack());
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

    public HashMap<Integer, oreoInvItem> getInventoryMap() {
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

    public void setInventoryMap(HashMap<Integer, oreoInvItem> inventoryMap) {
        this.inventoryMap = inventoryMap;
    }
}
