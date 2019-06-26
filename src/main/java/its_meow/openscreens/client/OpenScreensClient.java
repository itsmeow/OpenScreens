package its_meow.openscreens.client;

import its_meow.openscreens.OpenScreens;
import its_meow.openscreens.client.renderer.tileentity.RenderFlatScreen;
import its_meow.openscreens.client.renderer.tileentity.RenderHoloScreen;
import its_meow.openscreens.common.tileentity.TileEntityFlatScreen;
import its_meow.openscreens.common.tileentity.TileEntityHoloScreen;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class OpenScreensClient {

    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlatScreen.class, new RenderFlatScreen());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHoloScreen.class, new RenderHoloScreen());
        initModel(OpenScreens.BACK_FLAT_SCREEN_BLOCK_1, 0);
        initModel(OpenScreens.BACK_FLAT_SCREEN_BLOCK_2, 0);
        initModel(OpenScreens.BACK_FLAT_SCREEN_BLOCK_3, 0);
        initModel(OpenScreens.FRONT_FLAT_SCREEN_BLOCK_1, 0);
        initModel(OpenScreens.FRONT_FLAT_SCREEN_BLOCK_2, 0);
        initModel(OpenScreens.FRONT_FLAT_SCREEN_BLOCK_3, 0);
        initModel(OpenScreens.HOLO_SCREEN_BLOCK_1, 0);
        initModel(OpenScreens.HOLO_SCREEN_BLOCK_2, 0);
        initModel(OpenScreens.HOLO_SCREEN_BLOCK_3, 0);
    }

    public static void initModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void initModel(Block block, int meta) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}