package io.sporkpgm.util;

import io.sporkpgm.Spork;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class EventUtils {

	private static Spork plugin = Spork.get();
	private static PluginManager pm = plugin.getServer().getPluginManager();
	
	public static void registerListener(Listener listener) {
		pm.registerEvents(listener, plugin);
	}
	
	public static void unregisterListener(Listener listener) {
		HandlerList.unregisterAll(listener);
	}

}
