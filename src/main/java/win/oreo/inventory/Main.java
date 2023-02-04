package win.oreo.inventory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import win.oreo.inventory.util.oreoInventoryUtil;
import win.oreo.inventory.util.YmlManager;

public final class Main extends JavaPlugin {

    public YmlManager ymlManager;
    public oreoInventoryUtil oreoInventoryUtil;

    public Main() {
        this.oreoInventoryUtil = new oreoInventoryUtil();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new oreoInventoryUtil(), this);
        this.ymlManager = new YmlManager(this);
        oreoInventoryUtil.initialize();

        Bukkit.getConsoleSender().sendMessage("oreoInventory On!");
    }

    @Override
    public void onDisable() {
        oreoInventoryUtil.save();

        Bukkit.getConsoleSender().sendMessage("oreoInventory Off!");
    }
}
