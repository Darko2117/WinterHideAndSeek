package com.daki.main;

import com.daki.main.Event.EventEndEventListener;
import com.daki.main.Event.EventManager;
import com.daki.main.Event.EventReloadEventListener;
import com.daki.main.Event.EventStartEventListener;
import com.daki.main.Objects.Event;
import com.daki.main.christmas.hider.HidersEffects;
import com.daki.main.christmas.hider.HidersEffectsTabComplete;
import com.daki.main.christmas.hider.onPlayerDisconnect;
import com.daki.main.christmas.seeker.*;
import com.daki.main.christmas.seeker.items.onTryToGetRidOfItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WinterHideAndSeek extends JavaPlugin {

    public static WinterHideAndSeek instance;

    public static WinterHideAndSeek getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        EventManager.setExistingEvent(new Event());

        WinterHideAndSeek.getInstance().getLogger().info("--------------------------------------------------");

        Bukkit.getPluginManager().registerEvents(new onSnowballHit(), this);
        Bukkit.getPluginManager().registerEvents(new EventStartEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new EventEndEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new EventReloadEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new onAbilityUse(), this);
        Bukkit.getPluginManager().registerEvents(new onTryToGetRidOfItem(), this);
        Bukkit.getPluginManager().registerEvents(new onSnowballThrow(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerDisconnect(), this);

        getCommand("hiders").setExecutor(new HidersEffects());
        getCommand("hiders").setTabCompleter(new HidersEffectsTabComplete());

        getCommand("seekers").setExecutor(new Seeker());
        getCommand("seekers").setTabCompleter(new SeekersTabComplete());

        getCommand("event").setExecutor(new EventAdminCommands());
        getCommand("event").setTabCompleter(new EventAdminCommandsTabComplete());

        WinterHideAndSeek.getInstance().getLogger().info("WinterHideAndSeek plugin started!");
        WinterHideAndSeek.getInstance().getLogger().info("--------------------------------------------------");

    }
}
