package its_meow.openscreens.common.tileentity;

import li.cil.oc.common.tileentity.Screen;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityHoloScreen extends Screen {
    
    public TileEntityHoloScreen(int tier) {
        super(tier);
    }

    public TileEntityHoloScreen() {}
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        this.width_$eq(nbt.getInteger("confWidth"));
        this.height_$eq(nbt.getInteger("confHeight"));
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setInteger("confWidth", this.width());
        compound.setInteger("confHeight", this.height());
        return compound;
    }

    @Override
    public void checkMultiBlock() {}
    
    @Override
    public int delayUntilCheckForMultiBlock() {return 1;}

    @Override
    public void delayUntilCheckForMultiBlock_$eq(int x$1) {}
    

}
