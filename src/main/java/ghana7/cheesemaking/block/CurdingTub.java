package ghana7.cheesemaking.block;

import ghana7.cheesemaking.CheesemakingMod;
import ghana7.cheesemaking.item.cheese.Curd;
import ghana7.cheesemaking.item.Rennet;
import ghana7.cheesemaking.tileentity.CurdingTubTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class CurdingTub extends Block {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public CurdingTub() {
        super(Properties.create(Material.WOOD)
        .sound(SoundType.WOOD)
        .hardnessAndResistance(2.0F)
        .notSolid().setOpaque((BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_) -> (false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CheesemakingMod.CURDING_TUB_TE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            //CheesemakingMod.LOGGER.debug(handIn);
            ItemStack stack = player.getHeldItem(handIn);
            CurdingTubTileEntity curdingTubTileEntity = (CurdingTubTileEntity) worldIn.getTileEntity(pos);
            if(stack.getItem() instanceof MilkBucketItem) {
                if(curdingTubTileEntity.addMilk(1000)) {
                    if(!player.isCreative()) {
                        player.setHeldItem(handIn, new ItemStack(Items.BUCKET, 1));
                    }
                }
                return ActionResultType.SUCCESS;
            } else if(stack.getItem() instanceof BucketItem) {
                if(curdingTubTileEntity.removeMilk(1000)) {
                    if(!player.isCreative()) {
                        player.setHeldItem(handIn, new ItemStack(Items.MILK_BUCKET, 1));
                    }
                }
                return ActionResultType.SUCCESS;
            } else if(stack.getItem() instanceof Rennet) {
                ItemStack newStack = curdingTubTileEntity.itemHandler.insertItem(0, stack, false);
                if(!player.isCreative()) {
                    player.setHeldItem(handIn, newStack);
                }

                return ActionResultType.SUCCESS;
            } else if(!curdingTubTileEntity.itemHandler.getStackInSlot(1).isEmpty() && (stack.isEmpty() || stack.getItem() instanceof Curd)) {
                if(stack.isEmpty()) {
                    player.setHeldItem(handIn, curdingTubTileEntity.itemHandler.extractItem(1, 4, false));
                } else {
                    player.getHeldItem(handIn).grow(curdingTubTileEntity.itemHandler.extractItem(1, 4, false).getCount());
                }
                return ActionResultType.SUCCESS;
            } else if(!curdingTubTileEntity.itemHandler.getStackInSlot(0).isEmpty() && (stack.isEmpty() || stack.getItem() instanceof Rennet)) {
                if(stack.isEmpty()) {
                    player.setHeldItem(handIn, curdingTubTileEntity.itemHandler.extractItem(0, 4, false));
                } else {
                    player.getHeldItem(handIn).grow(curdingTubTileEntity.itemHandler.extractItem(0, 4, false).getCount());
                }
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof CurdingTubTileEntity) {
            InventoryHelper.dropItems(worldIn, pos, ((CurdingTubTileEntity)tileEntity).getAllItems());
        }
    }
}
