package win.oreo.inventory.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import win.oreo.inventory.Inventory.oreoInventory;
import win.oreo.inventory.Inventory.oreoItem;

import javax.annotation.Nullable;

public class oreoInventoryClickEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private oreoInventory inventory;
    private oreoItem clickedItem;

    private Player player;

    public oreoInventoryClickEvent(Player player, oreoInventory inventory, oreoItem item) {
        this.player = player;
        this.isCancelled = false;
        this.inventory = inventory;
        this.clickedItem = item;
    }

    public oreoInventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public oreoItem getClickedItem() {
        return clickedItem;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
