package its_meow.openscreens;

import java.util.List;

import com.google.common.collect.Lists;

import its_meow.openscreens.common.block.BlockFlatScreen;
import its_meow.openscreens.common.block.BlockHoloScreenEmitter;
import its_meow.openscreens.common.tileentity.TileEntityFlatScreen;
import its_meow.openscreens.common.tileentity.TileEntityHoloScreen;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
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
            return new ItemStack(BACK_FLAT_SCREEN_BLOCK_1);
        }
        
    };
    
    public static final Block BACK_FLAT_SCREEN_BLOCK_1 = new BlockFlatScreen(true, 0).setRegistryName(MODID + ":backflatscreen1").setTranslationKey(MODID + ".backflatscreen1");
    public static final Block BACK_FLAT_SCREEN_BLOCK_2 = new BlockFlatScreen(true, 1).setRegistryName(MODID + ":backflatscreen2").setTranslationKey(MODID + ".backflatscreen2");
    public static final Block BACK_FLAT_SCREEN_BLOCK_3 = new BlockFlatScreen(true, 2).setRegistryName(MODID + ":backflatscreen3").setTranslationKey(MODID + ".backflatscreen3");
    public static final Block FRONT_FLAT_SCREEN_BLOCK_1 = new BlockFlatScreen(false, 0).setRegistryName(MODID + ":frontflatscreen1").setTranslationKey(MODID + ".frontflatscreen1");
    public static final Block FRONT_FLAT_SCREEN_BLOCK_2 = new BlockFlatScreen(false, 1).setRegistryName(MODID + ":frontflatscreen2").setTranslationKey(MODID + ".frontflatscreen2");
    public static final Block FRONT_FLAT_SCREEN_BLOCK_3 = new BlockFlatScreen(false, 2).setRegistryName(MODID + ":frontflatscreen3").setTranslationKey(MODID + ".frontflatscreen3");
    public static final Block HOLO_SCREEN_BLOCK_1 = new BlockHoloScreenEmitter(0).setRegistryName(MODID + ":holoscreen1").setTranslationKey(MODID + ".holoscreen1");
    public static final Block HOLO_SCREEN_BLOCK_2 = new BlockHoloScreenEmitter(1).setRegistryName(MODID + ":holoscreen2").setTranslationKey(MODID + ".holoscreen2");
    public static final Block HOLO_SCREEN_BLOCK_3 = new BlockHoloScreenEmitter(2).setRegistryName(MODID + ":holoscreen3").setTranslationKey(MODID + ".holoscreen3");
    
    public static final List<Block> SCREENS = Lists.newArrayList(
        OpenScreens.BACK_FLAT_SCREEN_BLOCK_1, 
        OpenScreens.BACK_FLAT_SCREEN_BLOCK_2, 
        OpenScreens.BACK_FLAT_SCREEN_BLOCK_3,
        OpenScreens.FRONT_FLAT_SCREEN_BLOCK_1, 
        OpenScreens.FRONT_FLAT_SCREEN_BLOCK_2, 
        OpenScreens.FRONT_FLAT_SCREEN_BLOCK_3,
        OpenScreens.HOLO_SCREEN_BLOCK_1,
        OpenScreens.HOLO_SCREEN_BLOCK_2,
        OpenScreens.HOLO_SCREEN_BLOCK_3
    );
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for(Block block : SCREENS) {
            event.getRegistry().register(block.setCreativeTab(OPENSCREENS_TAB));
        }
        GameRegistry.registerTileEntity(TileEntityFlatScreen.class, new ResourceLocation(MODID + ":flatscreen"));
        GameRegistry.registerTileEntity(TileEntityHoloScreen.class, new ResourceLocation(MODID + ":holoscreen"));
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for(Block block : SCREENS) {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }
	
}