package its_meow.openscreens.common.tileentity;

import li.cil.oc.api.network.Node;
import li.cil.oc.common.tileentity.Screen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.world.WorldServer;

public class TileEntityHoloScreen extends Screen {

    public TileEntityHoloScreen(int tier) {
        super(tier);
    }

    public TileEntityHoloScreen() {
    }

    public void onShiftRightClickEmpty(EnumFacing face, EnumFacing stateFace) {
        if(face.getAxis() != Axis.Y) {
            if(stateFace == face.rotateY() /* && this.width() < 10 */) {
                this.width_$eq(this.width() + 1);
            } else if(stateFace == face.rotateYCCW() && this.width() > 1) {
                this.width_$eq(this.width() - 1);
            } else if(stateFace == face && this.height() > 1) {
                this.height_$eq(this.height() - 1);
            }
        } else {
            if(EnumFacing.UP == face /* && this.height() < 10 */) {
                this.height_$eq(this.height() + 1);
            } else if(EnumFacing.DOWN == face && this.height() > 1) {
                this.height_$eq(this.height() - 1);
            }
        }
        this.markDirty();
        if(!world.isRemote && world instanceof WorldServer) {
            world.getMinecraftServer().getPlayerList().sendPacketToAllPlayersInDimension(this.getUpdatePacket(), world.provider.getDimension());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.width_$eq(compound.getInteger("confWidth"));
        this.height_$eq(compound.getInteger("confHeight"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("confWidth", this.width());
        compound.setInteger("confHeight", this.height());
        return compound;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new SPacketUpdateTileEntity(this.pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
        this.world.scheduleUpdate(this.pos, this.blockType, 100);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public void writeToNBTForClient(NBTTagCompound nbt) {
        super.writeToNBTForClient(nbt);
        nbt.setInteger("confWidth", this.width());
        nbt.setInteger("confHeight", this.height());
    }

    @Override
    public void writeToNBTForServer(NBTTagCompound nbt) {
        super.writeToNBTForServer(nbt);
        nbt.setInteger("confWidth", this.width());
        nbt.setInteger("confHeight", this.height());
    }

    @Override
    public void readFromNBTForClient(NBTTagCompound nbt) {
        super.readFromNBTForClient(nbt);
        this.width_$eq(nbt.getInteger("confWidth"));
        this.height_$eq(nbt.getInteger("confHeight"));
    }

    @Override
    public void readFromNBTForServer(NBTTagCompound nbt) {
        super.readFromNBTForServer(nbt);
        this.width_$eq(nbt.getInteger("confWidth"));
        this.height_$eq(nbt.getInteger("confHeight"));
    }

    @Override
    public void checkMultiBlock() {
    }

    @Override
    public int delayUntilCheckForMultiBlock() {
        return 1;
    }

    @Override
    public void delayUntilCheckForMultiBlock_$eq(int x$1) {
    }

    @Override
    public Node sidedNode(EnumFacing side) {
        Node node = super.sidedNode(side);
        TileEntity te = getWorld().getTileEntity(getPos().offset(side));
        if(te instanceof Screen) {
            return null;
        }
        return node;
    }

}
