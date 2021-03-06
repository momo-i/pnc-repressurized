package me.desht.pneumaticcraft.common.item;

import me.desht.pneumaticcraft.common.core.ModContainers;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;

public class ItemLogisticsFrameRequester extends ItemLogisticsFrame {
    @Override
    protected ContainerType<?> getContainerType() {
        return ModContainers.LOGISTICS_FRAME_REQUESTER.get();
    }

    @Override
    public int getTintColor(ItemStack stack, int tintIndex) {
        return 0xFF0000FF;
    }
}
