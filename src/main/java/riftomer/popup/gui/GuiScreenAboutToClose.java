package riftomer.popup.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riftomer.popup.Popup;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiScreenAboutToClose extends GuiScreen {
    private final GuiScreen parent;
    private GuiButton close;
    private GuiButton cancel;
    private final String text;
    private final String cl;
    private final String ca;


    public GuiScreenAboutToClose(GuiScreen parent, String text, String cl, String ca) {
        this.parent = parent;
        this.text = text;
        this.cl = cl;
        this.ca = ca;
    }

    @Override
    public void initGui() {
        super.initGui();
        initButtons();
        moveButtons();
    }

    private void initButtons(){
        close = new GuiButton(0,0,0,fontRenderer.getStringWidth(cl) + 9,20,cl);
        cancel = new GuiButton(1,0,0,fontRenderer.getStringWidth(ca) + 9,20,ca);
        buttonList.add(close);
        buttonList.add(cancel);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(parent != null){
            parent.drawScreen(-1,-1, partialTicks);
        }
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        drawRect(0,0, resolution.getScaledWidth(), resolution.getScaledHeight(), 0xAA000000);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        System.nanoTime();
        drawMainElements(mouseX, mouseY, partialTicks, resolution);
        GlStateManager.popMatrix();
    }

    private void drawMainElements(int mouseX, int mouseY, float partialTicks, ScaledResolution resolution){
        int hw = resolution.getScaledWidth() / 2, hh = resolution.getScaledHeight() / 2;
        int bneg = - Math.max(close.width + 4, fontRenderer.getStringWidth(text)/2 + 2);
        int bpos = Math.max(cancel.width + 4, fontRenderer.getStringWidth(text)/2 + 2);
        drawEdgedRect(bneg + hw,hh - 22, bpos + hw, hh + 22, 0xFFC6C6C6, 0xFF000000);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRenderer, text, hw, hh - 20, 0xFFFFFF);

    }

    private void drawEdgedRect(int left, int top, int right, int bottom, int color, int edgeColor){
        drawRect(left, top, right, bottom, color);
        drawVerticalLine(left,top,bottom,edgeColor);
        drawVerticalLine(right,top,bottom,edgeColor);
        drawHorizontalLine(left, right, top, edgeColor);
        drawHorizontalLine(left, right, bottom, edgeColor);
    }

    private void moveButtons(){
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        close.x = resolution.getScaledWidth() / 2 - close.width - 2;
        close.y = resolution.getScaledHeight() / 2;
        cancel.x = resolution.getScaledWidth() / 2 + 2;
        cancel.y = resolution.getScaledHeight() / 2;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        if(parent != null){
            parent.onResize(mcIn, w, h);
        }
        moveButtons();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == close.id) {
            Minecraft.getMinecraft().shutdown();
            Popup.INSTANCE.isRunning = false;
        } else if (button.id == cancel.id) {
            Minecraft.getMinecraft().displayGuiScreen(parent);
        }
    }
}
