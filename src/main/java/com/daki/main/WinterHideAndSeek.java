package com.daki.main;

import com.daki.main.christmas.onParticipantLeave;
import com.daki.main.event.listeners.EventEndEventListener;
import com.daki.main.event.manager.EventManager;
import com.daki.main.event.listeners.EventReloadEventListener;
import com.daki.main.event.listeners.EventStartEventListener;
import com.daki.main.objects.Event;
import com.daki.main.commands.hiders.HidersEffects;
import com.daki.main.commands.hiders.HidersEffectsTabComplete;
import com.daki.main.christmas.seeker.*;
import com.daki.main.christmas.seeker.items.onTryToGetRidOfItem;
import com.daki.main.commands.event.EventAdminCommands;
import com.daki.main.commands.event.EventAdminCommandsTabComplete;
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
        Bukkit.getPluginManager().registerEvents(new onParticipantLeave(), this);

        getCommand("hiders").setExecutor(new HidersEffects());
        getCommand("hiders").setTabCompleter(new HidersEffectsTabComplete());

        getCommand("event").setExecutor(new EventAdminCommands());
        getCommand("event").setTabCompleter(new EventAdminCommandsTabComplete());

        WinterHideAndSeek.getInstance().getLogger().info("WinterHideAndSeek plugin started!");
        WinterHideAndSeek.getInstance().getLogger().info("--------------------------------------------------");

    }
}
