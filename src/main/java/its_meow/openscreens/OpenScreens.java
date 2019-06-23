package its_meow.openscreens;

import its_meow.openscreens.common.block.BlockFlatScreen;
import its_meow.openscreens.common.tileentity.TileEntityFlatScreen;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
@Mod(modid = Ref.MODID, name = Ref.NAME, version = Ref.VERSION, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class OpenScreens {
    
    public static final Block FLAT_SCREEN_BLOCK = new BlockFlatScreen(0).setRegistryName(Ref.MODID + ":flatscreen");
    public static final Block FLAT_SCREEN_BLOCK_2 = new BlockFlatScreen(1).setRegistryName(Ref.MODID + ":flatscreen2");
    public static final Block FLAT_SCREEN_BLOCK_3 = new BlockFlatScreen(2).setRegistryName(Ref.MODID + ":flatscreen3");
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

	}
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(FLAT_SCREEN_BLOCK, FLAT_SCREEN_BLOCK_2, FLAT_SCREEN_BLOCK_3);
        GameRegistry.registerTileEntity(TileEntityFlatScreen.class, new ResourceLocation(Ref.MODID + ":flatscreen"));
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(FLAT_SCREEN_BLOCK).setRegistryName(Ref.MODID + ":flatscreen"));
        event.getRegistry().register(new ItemBlock(FLAT_SCREEN_BLOCK_2).setRegistryName(Ref.MODID + ":flatscreen2"));
        event.getRegistry().register(new ItemBlock(FLAT_SCREEN_BLOCK_3).setRegistryName(Ref.MODID + ":flatscreen3"));
    }
	
}