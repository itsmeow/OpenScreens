package its_meow.openscreens.common.tileentity;

import li.cil.oc.common.tileentity.Screen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFlatScreen extends Screen {
    
    public boolean isBack;

    public TileEntityFlatScreen(boolean isBack, int tier) {
        super(tier);
        this.isBack = isBack;
    }

    public TileEntityFlatScreen() {}

    @SideOnly(Side.CLIENT)
    public boolean canConnect(EnumFacing side) {
        return isBack ? side == null || this.facing() == null ? false : side == this.facing().getOpposite() : false;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        this.isBack = nbt.getBoolean("isBack");
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setBoolean("isBack", isBack);
        return compound;
    }

}