package its_meow.openscreens;

import its_meow.openscreens.common.block.BlockFlatScreen;
import its_meow.openscreens.common.tileentity.TileEntityFlatScreen;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
@Mod(modid = OpenScreens.MODID, name = OpenScreens.NAME, version = OpenScreens.VERSION, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class OpenScreens {
    
    public static final String MODID = "openscreens";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "OpenScreens";
    
    public static final CreativeTabs OPENSCREENS_TAB = new CreativeTabs(MODID) {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(FLAT_SCREEN_BLOCK_1);
        }
        
    };
    
    public static final Block FLAT_SCREEN_BLOCK_1 = new BlockFlatScreen(0).setRegistryName(MODID + ":flatscreen1").setTranslationKey(MODID + ".flatscreen1").setCreativeTab(OPENSCREENS_TAB);
    public static final Block FLAT_SCREEN_BLOCK_2 = new BlockFlatScreen(1).setRegistryName(MODID + ":flatscreen2").setTranslationKey(MODID + ".flatscreen2").setCreativeTab(OPENSCREENS_TAB);
    public static final Block FLAT_SCREEN_BLOCK_3 = new BlockFlatScreen(2).setRegistryName(MODID + ":flatscreen3").setTranslationKey(MODID + ".flatscreen3").setCreativeTab(OPENSCREENS_TAB);

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
        event.getRegistry().registerAll(FLAT_SCREEN_BLOCK_1, FLAT_SCREEN_BLOCK_2, FLAT_SCREEN_BLOCK_3);
        GameRegistry.registerTileEntity(TileEntityFlatScreen.class, new ResourceLocation(MODID + ":flatscreen"));
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(FLAT_SCREEN_BLOCK_1).setRegistryName(MODID + ":flatscreen1"));
        event.getRegistry().register(new ItemBlock(FLAT_SCREEN_BLOCK_2).setRegistryName(MODID + ":flatscreen2"));
        event.getRegistry().register(new ItemBlock(FLAT_SCREEN_BLOCK_3).setRegistryName(MODID + ":flatscreen3"));
    }
	
}