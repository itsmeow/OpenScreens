package its_meow.openscreens.common.tileentity;

import li.cil.oc.common.tileentity.Screen;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFlatScreen extends Screen {

    public TileEntityFlatScreen(int tier) {
        super(tier);
    }

    public TileEntityFlatScreen() {
    }

    @SideOnly(Side.CLIENT)
    public boolean canConnect(EnumFacing side) {
        return side == null || this.facing() == null ? false : side == this.facing().getOpposite();
    }

    @Override
    public boolean hasKeyboard() {
        return true;
    }

}