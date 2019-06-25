package its_meow.openscreens.common.block;

import its_meow.openscreens.common.tileentity.TileEntityFlatScreen;
import li.cil.oc.common.block.Screen;
import li.cil.oc.common.block.property.PropertyRotatable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlatScreen extends Screen {

    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 1.0D - 0.0625D, 1.0D, 1.0D, 1.0D);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(1.0D - 0.0625D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
    public static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.0D, 1.0 - 0.0625D, 0.0D, 1.0D, 1.0D, 1.0D);
    
    public final boolean isBack;

    public BlockFlatScreen(boolean isBack, int tier) {
        super(tier);
        this.setHardness(4F);
        this.setHarvestLevel("pickaxe", 0);
        this.isBack = isBack;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(PropertyRotatable.Yaw(), placer.getHorizontalFacing().getOpposite()).withProperty(PropertyRotatable.Pitch(), ((placer.rotationPitch > 45 && facing == EnumFacing.UP) || (placer.prevRotationPitch < -45 && facing == EnumFacing.DOWN)) ? facing : EnumFacing.NORTH);
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return toLocal(world, pos, side) == (isBack ? EnumFacing.NORTH : EnumFacing.SOUTH);
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return toLocal(worldIn, pos, face) == (isBack ? EnumFacing.NORTH : EnumFacing.SOUTH) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if(source.getTileEntity(pos) instanceof TileEntityFlatScreen) {
            EnumFacing facing = ((TileEntityFlatScreen)source.getTileEntity(pos)).facing();
            facing = isBack ? facing : facing.getOpposite();
            switch(facing) {
            case NORTH: return NORTH_AABB;
            case EAST: return EAST_AABB;
            case SOUTH: return SOUTH_AABB;
            case WEST: return WEST_AABB;
            case UP: return UP_AABB;
            case DOWN: return DOWN_AABB;
            default: return NORTH_AABB;
            }
        }
        return NORTH_AABB;
    }

    @Override
    public TileEntityFlatScreen createNewTileEntity(World world, int meta) {
        return new TileEntityFlatScreen(isBack, tier());
    }

}