package its_meow.openscreens;

import its_meow.openscreens.client.renderer.tileentity.RenderFlatScreen;
import its_meow.openscreens.common.tileentity.TileEntityFlatScreen;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class OpenScreensClient {
    
    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlatScreen.class, new RenderFlatScreen());
    }
    
}