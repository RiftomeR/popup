package riftomer.popup;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import riftomer.popup.gui.GuiScreenAboutToClose;

@Mod(modid = "popup", clientSideOnly = true)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = "popup")
public class Popup {
    @Mod.Instance("popup")
    public static Popup INSTANCE;

    public boolean isRunning = true;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        boolean isRunningMC = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "running", "field_71425_J");
        if((!isRunningMC) && INSTANCE.isRunning){
            ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), true, "running", "field_71425_J");
            if(!(Minecraft.getMinecraft().currentScreen instanceof GuiScreenAboutToClose)){
                Minecraft.getMinecraft().displayGuiScreen(new GuiScreenAboutToClose(Minecraft.getMinecraft().currentScreen,MessageList.getRandomMSG(), MessageList.getRandomYesMSG(), MessageList.getRandomNoMSG()));
            }
        }
    }

    @Mod.EventHandler
    public static void onPostInit(FMLPostInitializationEvent event){
        MessageList.init();
    }
}
