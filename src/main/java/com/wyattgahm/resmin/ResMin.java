package com.wyattgahm.resmin;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;

public class ResMin extends PluginBase implements Listener{

	private int minSize;
	
	@Override
	public void onLoad() {
		this.getLogger().info(TextFormat.WHITE + "ResMin Loaded");
	}

	@Override
	public void onEnable() {
		minSize = this.getConfig().getInt("MinCount", 0);
		this.getLogger().info(TextFormat.DARK_GREEN + "MinRes Enabled");
		
		this.getServer().getPluginManager().registerEvents(this, this);
			
		//if(this.getConfig().getInt("MinCount", 0) == 0) {
			this.saveDefaultConfig();
		//}
		this.getLogger().info(TextFormat.DARK_GREEN + "=====Testing ResMin=====");
		test();
		this.getLogger().info(TextFormat.DARK_GREEN + "=====Test Complete!=====");
	}

	@Override
	public void onDisable() {
		this.getLogger().info(TextFormat.DARK_RED + "MinRes Disabled");
	}

	public void test() {
		CuboidArea resArea = new CuboidArea(new Location(0,0,0),new Location(10,10,10));
		ResidenceCreationEvent resCreate = new ResidenceCreationEvent(null, "ass-poop", new ClaimedResidence("nether"), resArea);
		this.getLogger().info("the size of the test object is " + resCreate.getPhysicalArea().getSize());
		onResCreate(resCreate);
		this.getLogger().info("The status of the event is: " + !resCreate.isCancelled());
	}
	
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onResCreate(ResidenceCreationEvent event) {
		if(event.getPhysicalArea().getSize() < minSize) {
			this.getLogger().info("the size was " + event.getPhysicalArea().getSize()+", which was too small! ("+ minSize+")");
			event.setCancelled(true);
			event.getPlayer().sendMessage(TextFormat.DARK_RED+"Unable to claim! Reason: Too small");
		}else {
			this.getLogger().info("the size is " + event.getPhysicalArea().getSize()+", which is acceptable! ("+minSize+")");
		}
	}
}
