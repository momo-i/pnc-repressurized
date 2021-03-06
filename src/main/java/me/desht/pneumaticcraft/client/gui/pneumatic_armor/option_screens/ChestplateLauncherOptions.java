package me.desht.pneumaticcraft.client.gui.pneumatic_armor.option_screens;

import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage;
import me.desht.pneumaticcraft.client.KeyHandler;
import me.desht.pneumaticcraft.client.gui.pneumatic_armor.KeybindingButton;
import me.desht.pneumaticcraft.client.render.pneumatic_armor.upgrade_handler.ChestplateLauncherClientHandler;
import net.minecraft.client.util.InputMappings;

import static me.desht.pneumaticcraft.common.util.PneumaticCraftUtils.xlate;

public class ChestplateLauncherOptions extends IOptionPage.SimpleToggleableOptions<ChestplateLauncherClientHandler> {
    private KeybindingButton changeKeybindingButton;

    public ChestplateLauncherOptions(IGuiScreen screen, ChestplateLauncherClientHandler renderHandler) {
        super(screen, renderHandler);
    }

    @Override
    public void populateGui(IGuiScreen gui) {
        changeKeybindingButton = new KeybindingButton(30, 128, 150, 20, xlate("pneumaticcraft.armor.gui.misc.setKey"), KeyHandler.getInstance().keybindLauncher);
        gui.addWidget(changeKeybindingButton);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return changeKeybindingButton.receiveKey(InputMappings.Type.KEYSYM, keyCode);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        return changeKeybindingButton.receiveKey(InputMappings.Type.MOUSE, button);
    }

    @Override
    public boolean isToggleable() {
        return false;
    }

}
