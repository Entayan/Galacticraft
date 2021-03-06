package micdoodle8.mods.galacticraft.core.client.jei.refinery;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.jei.RecipeCategories;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class RefineryRecipeCategory extends BlankRecipeCategory
{
    private static final ResourceLocation refineryGuiTex = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/refinery_recipe.png");

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final IDrawableAnimated oilBar;
    @Nonnull
    private final IDrawableAnimated fuelBar;

    public RefineryRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.createDrawable(refineryGuiTex, 3, 4, 168, 64);
        this.localizedName = GCCoreUtil.translate("tile.refinery.name");

        IDrawableStatic progressBarDrawableOil = guiHelper.createDrawable(refineryGuiTex, 176, 0, 16, 38);
        this.oilBar = guiHelper.createAnimatedDrawable(progressBarDrawableOil, 70, IDrawableAnimated.StartDirection.TOP, true);
        IDrawableStatic progressBarDrawableFuel = guiHelper.createDrawable(refineryGuiTex, 192, 0, 16, 38);
        this.fuelBar = guiHelper.createAnimatedDrawable(progressBarDrawableFuel, 70, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return RecipeCategories.REFINERY_ID;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return this.localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft)
    {
        this.oilBar.draw(minecraft, 40, 24);
        this.fuelBar.draw(minecraft, 114, 24);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper)
    {
        IGuiItemStackGroup itemstacks = recipeLayout.getItemStacks();

        itemstacks.init(0, true, 39, 2);
        itemstacks.init(1, false, 113, 2);

        if (recipeWrapper instanceof RefineryRecipeWrapper)
        {
            RefineryRecipeWrapper circuitFabricatorRecipeWrapper = (RefineryRecipeWrapper) recipeWrapper;
            List inputs = circuitFabricatorRecipeWrapper.getInputs();

            for (int i = 0; i < inputs.size(); ++i)
            {
                Object o = inputs.get(i);
                if (o != null)
                {
                    itemstacks.setFromRecipe(i, o);
                }
            }
            itemstacks.setFromRecipe(1, circuitFabricatorRecipeWrapper.getOutputs());
        }
    }
}
