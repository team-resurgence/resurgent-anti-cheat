package com.mcresurgence;

import com.mcresurgence.logging.ACModLogger;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ResurgentAntiCheatMod.MODID, name = ResurgentAntiCheatMod.NAME, version = ResurgentAntiCheatMod.VERSION, acceptableRemoteVersions = "*", serverSideOnly = true)
public class ResurgentAntiCheatMod {
    public static final String MODID = "resurgent-anti-cheat";
    public static final String NAME = "Resurgent AntiCheat";
    public static final String VERSION = "0.1.0";

    public static ACModLogger modLogger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = new ACModLogger(event.getModLog(), "[Resurgent AntiCheat] ");

        MinecraftForge.EVENT_BUS.register(new ChunkLoadHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Any additional initialization logic
    }
}
