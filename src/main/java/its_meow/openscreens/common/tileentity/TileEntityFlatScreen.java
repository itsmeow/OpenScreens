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

    @Override
    public EnumFacing[] validFacings() {
        return super.validFacings();//new EnumFacing[] { EnumFacing.NORTH, EnumFacing.UP, EnumFacing.DOWN};
    }

    @SideOnly(Side.CLIENT)
    public boolean canConnect(EnumFacing side) {
        return side == null || this.facing() == null ? false : (side != EnumFacing.UP && side != EnumFacing.DOWN ? side == this.facing().getOpposite() : side == this.facing());//super.canConnect(side);
    }

    @Override
    public boolean hasKeyboard() {
        return true;
    }

    /*
    @Override
    public Node sidedNode(EnumFacing side) {
        if(side == null || this.facing() == null) {
            return null;
        } else if(side != EnumFacing.UP && side != EnumFacing.DOWN ? side == this.facing().getOpposite() : side == this.facing()) {
            return this.node();
        }

        if(!this.getWorld().isBlockLoaded(this.getPos().offset(side)) || !(this.getWorld().getTileEntity(this.getPos().offset(side)) instanceof Keyboard)) {
            return null;
        }
        return null;
    }
    */

}