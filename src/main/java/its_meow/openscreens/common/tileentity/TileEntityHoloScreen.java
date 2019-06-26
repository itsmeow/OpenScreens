package its_meow.openscreens.common.tileentity;

import li.cil.oc.common.tileentity.Screen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;

public class TileEntityHoloScreen extends Screen {

    public TileEntityHoloScreen(int tier) {
        super(tier);
    }

    public TileEntityHoloScreen() {}

    public void onShiftRightClickEmpty(EnumFacing face, EnumFacing stateFace) {
        if(face.getAxis() != Axis.Y) {
            if(stateFace == face.rotateY() /*&& this.width() < 10*/) {
                this.width_$eq(this.width() + 1);
            } else if(stateFace == face.rotateYCCW() && this.width() > 1){
                this.width_$eq(this.width() - 1);
            } else if(stateFace == face && this.height() > 1){
                this.height_$eq(this.height() - 1);
            }
        } else {
            if(EnumFacing.UP == face /*&& this.height() < 10*/) {
                this.height_$eq(this.height() + 1);
            } else if(EnumFacing.DOWN == face && this.height() > 1){
                this.height_$eq(this.height() - 1);
            }
        }
    }

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
