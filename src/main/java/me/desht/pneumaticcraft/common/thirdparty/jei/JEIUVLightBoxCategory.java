package me.desht.pneumaticcraft.common.thirdparty.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.desht.pneumaticcraft.common.core.ModBlocks;
import me.desht.pneumaticcraft.common.core.ModItems;
import me.desht.pneumaticcraft.common.recipes.machine.UVLightBoxRecipe;
import me.desht.pneumaticcraft.common.tileentity.TileEntityUVLightBox;
import me.desht.pneumaticcraft.common.util.PneumaticCraftUtils;
import me.desht.pneumaticcraft.lib.Textures;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JEIUVLightBoxCategory implements IRecipeCategory<UVLightBoxRecipe> {
    private final String localizedName;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated progressBar;

    private static final List<UVLightBoxRecipe> UV_LIGHT_BOX_RECIPES;
    static {
        ItemStack out = new ItemStack(ModItems.EMPTY_PCB.get());
        TileEntityUVLightBox.setExposureProgress(out, 100);
        UVLightBoxRecipe recipe = new UVLightBoxRecipe(Ingredient.fromItems(ModItems.EMPTY_PCB.get()), out);
        UV_LIGHT_BOX_RECIPES = Collections.singletonList(recipe);
    }

    JEIUVLightBoxCategory() {
        localizedName = I18n.format(ModBlocks.UV_LIGHT_BOX.get().getTranslationKey());
        background = JEIPlugin.jeiHelpers.getGuiHelper().createDrawable(Textures.GUI_JEI_MISC_RECIPES, 0, 0, 82, 18);
        icon = JEIPlugin.jeiHelpers.getGuiHelper().createDrawableIngredient(new ItemStack(ModBlocks.UV_LIGHT_BOX.get()));
        IDrawableStatic d = JEIPlugin.jeiHelpers.getGuiHelper().createDrawable(Textures.GUI_JEI_MISC_RECIPES, 82, 0, 38, 17);
        progressBar = JEIPlugin.jeiHelpers.getGuiHelper().createAnimatedDrawable(d, 60, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return ModCategoryUid.UV_LIGHT_BOX;
    }

    @Override
    public Class<? extends UVLightBoxRecipe> getRecipeClass() {
        return UVLightBoxRecipe.class;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(UVLightBoxRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getIn()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOut());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, UVLightBoxRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 0, 0);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        recipeLayout.getItemStacks().init(1, false, 64, 0);
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    public void draw(UVLightBoxRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        progressBar.draw(matrixStack, 22, 0);
        icon.draw(matrixStack, 30, -2);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(UVLightBoxRecipe recipe, double mouseX, double mouseY) {
        List<ITextComponent> res = new ArrayList<>();
        if (mouseX >= 23 && mouseX <= 60) {
            res.addAll(PneumaticCraftUtils.splitStringComponent(I18n.format("pneumaticcraft.gui.nei.recipe.uvLightBox")));
        }
        return res;
    }

    static Collection<UVLightBoxRecipe> getAllRecipes() {
        return UV_LIGHT_BOX_RECIPES;
    }
}
