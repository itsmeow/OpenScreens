package its_meow.openscreens.common.block;

import java.util.List;

import its_meow.openscreens.common.tileentity.TileEntityHoloScreen;
import li.cil.oc.OpenComputers$;
import li.cil.oc.common.GuiType$;
import li.cil.oc.common.block.RedstoneAware;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHoloScreenEmitter extends RedstoneAware {

    public static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    public static final EnumFacing[] VALID_ROTATIONS = EnumFacing.HORIZONTALS;
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public final int tier;

    public BlockHoloScreenEmitter(int tier) {
        this.setHardness(4F);
        this.setHarvestLevel("pickaxe", 0);
        this.tier = tier;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(player.getHeldItem(hand).getItem().getRegistryName().toString().equals("opencomputers:analyzer")) {
            return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        } else if(world.getTileEntity(pos) instanceof TileEntityHoloScreen) {
            TileEntityHoloScreen screen = (TileEntityHoloScreen) world.getTileEntity(pos);
            if(screen.hasKeyboard() && !player.isSneaking()) {
                if(world.isRemote) {
                    player.openGui(OpenComputers$.MODULE$, GuiType$.MODULE$.Screen().id(), world, pos.getX(), pos.getY(), pos.getZ());
                }
                return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
            } else if(player.isSneaking() && player.getHeldItem(hand).isEmpty()) {
                screen.onShiftRightClickEmpty(side, state.getValue(FACING));
                return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
            }
            screen.markDirty();
        }
        return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.info").getFormattedText());
        tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.dyeable").getFormattedText());
        if(GuiScreen.isShiftKeyDown()) {
            tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.left").getFormattedText());
            tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.right").getFormattedText());
            tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.front").getFormattedText());
            tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.top").getFormattedText());
        } else {
            tooltip.add(new TextComponentTranslation("tooltip.openscreens.holoscreen.moreinfo").getFormattedText());
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return super.shouldSideBeRendered(state, world, pos, side) || side == EnumFacing.UP;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        return VALID_ROTATIONS;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face != EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntityHoloScreen createTileEntity(World world, IBlockState state) {
        return new TileEntityHoloScreen(tier);
    }

}