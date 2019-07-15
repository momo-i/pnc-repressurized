package me.desht.pneumaticcraft.common.recipes;

import com.google.common.collect.ImmutableList;
import me.desht.pneumaticcraft.api.recipe.IPressureChamberRecipe;
import me.desht.pneumaticcraft.api.recipe.ItemIngredient;
import me.desht.pneumaticcraft.common.util.ItemStackHandlerIterable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PressureChamberPressureEnchantHandler implements IPressureChamberRecipe {
    @Override
    public float getCraftingPressure() {
        return 2F;
    }

    @Override
    public boolean isValidRecipe(ItemStackHandler chamberHandler) {
        return getRecipeIngredients(chamberHandler) != null;
    }

    private ItemStack[] getRecipeIngredients(ItemStackHandler inputStacks) {
        List<ItemStack> enchantedBooks = new ItemStackHandlerIterable(inputStacks)
                                                    .stream()
                                                    .filter(book -> book.getItem() == Items.ENCHANTED_BOOK)
                                                    .collect(Collectors.toList());

        if (enchantedBooks.isEmpty()) return null;

        for (ItemStack inputStack : new ItemStackHandlerIterable(inputStacks)) {
            if ((inputStack.isItemEnchantable() || inputStack.isItemEnchanted()) && inputStack.getItem() != Items.ENCHANTED_BOOK) {
                for (ItemStack enchantedBook : enchantedBooks) {
                    Map<Enchantment, Integer> bookMap = EnchantmentHelper.getEnchantments(enchantedBook);
                    for (Map.Entry<Enchantment, Integer> entry : bookMap.entrySet()) {
                        // if the enchantment is applicable, AND the item doesn't have an existing enchantment of the
                        // same type which is equal or stronger to the book's enchantment level...
                        if (entry.getKey().canApply(inputStack) && EnchantmentHelper.getEnchantmentLevel(entry.getKey(), inputStack) < entry.getValue()) {
                            return new ItemStack[]{ inputStack, enchantedBook};
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public NonNullList<ItemStack> craftRecipe(ItemStackHandler chamberHandler) {
        ItemStack[] recipeIngredients = getRecipeIngredients(chamberHandler);
        ItemStack enchantedTool = recipeIngredients[0];
        ItemStack enchantedBook = recipeIngredients[1];
        
        Map<Enchantment, Integer> bookMap = EnchantmentHelper.getEnchantments(enchantedBook);
        Map<Enchantment, Integer> itemMap = EnchantmentHelper.getEnchantments(enchantedTool);

        bookMap.forEach(itemMap::put);
        EnchantmentHelper.setEnchantments(itemMap, enchantedTool);
        
        enchantedBook.shrink(1);
        return NonNullList.from(ItemStack.EMPTY, new ItemStack(Items.BOOK));
    }

    @Override
    public List<ItemIngredient> getInput() {
        ItemIngredient pick = new ItemIngredient(Items.DIAMOND_PICKAXE, 1, 0).setTooltip("gui.nei.tooltip.pressureEnchantItem");

        ItemStack enchBook = new ItemStack(Items.ENCHANTED_BOOK, 1, 0);
        enchBook.addEnchantment(Enchantments.FORTUNE, 1);
        ItemIngredient book = new ItemIngredient(enchBook).setTooltip("gui.nei.tooltip.pressureEnchantBook");

        return ImmutableList.of(pick, book);
    }

    @Override
    public NonNullList<ItemStack> getResult() {
        ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE, 1, 0);
        pick.addEnchantment(Enchantments.FORTUNE, 1);
        IPressureChamberRecipe.setTooltipKey(pick, "gui.nei.tooltip.pressureEnchantItemOut");
        ItemStack book = new ItemStack(Items.BOOK);
        IPressureChamberRecipe.setTooltipKey(book, "gui.nei.tooltip.pressureEnchantBookOut");
        return NonNullList.from(ItemStack.EMPTY, pick, book);
    }
}
