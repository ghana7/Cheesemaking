package ghana7.cheesemaking.block;

import ghana7.cheesemaking.CheesemakingMod;
import ghana7.cheesemaking.item.Cheese;
import ghana7.cheesemaking.tileentity.CheeseRackTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class CheeseRack extends Block {
    public CheeseRack() {
        super(Properties.create(Material.WOOD)
                .harvestTool(ToolType.AXE)
                .sound(SoundType.WOOD)
                .notSolid().setOpaque((BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_) -> (false)));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CheesemakingMod.CHEESE_RACK_TE.get().create();
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            //CheesemakingMod.LOGGER.debug(handIn);
            ItemStack stack = player.getHeldItem(handIn);
            CheeseRackTileEntity cheeseRackTileEntity = (CheeseRackTileEntity) worldIn.getTileEntity(pos);
            Vector3d hitPos = hit.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
            CheesemakingMod.LOGGER.debug(hitPos);
            int hitIndex = 0;
            if(hitPos.x > 0.5) {
                hitIndex += 1;
            }
            if(hitPos.y > 0.5) {
                hitIndex += 4;
            }
            if(hitPos.z > 0.5) {
                hitIndex += 2;
            }
            CheesemakingMod.LOGGER.debug(hitIndex);
            if(cheeseRackTileEntity.itemHandler.getStackInSlot(hitIndex).isEmpty() && stack.getItem() instanceof Cheese) {

                player.setHeldItem(handIn, cheeseRackTileEntity.itemHandler.insertItem(hitIndex, stack, false));
                return ActionResultType.SUCCESS;
            } else if(!cheeseRackTileEntity.itemHandler.getStackInSlot(hitIndex).isEmpty() && (stack.isEmpty() || stack.getItem().equals(cheeseRackTileEntity.itemHandler.getStackInSlot(hitIndex).getItem()))) {

                if(stack.isEmpty()) {
                    player.setHeldItem(handIn, cheeseRackTileEntity.itemHandler.extractItem(hitIndex, 1, false));
                } else {
                    player.getHeldItem(handIn).grow(cheeseRackTileEntity.itemHandler.extractItem(hitIndex, 1, false).getCount());
                }
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }
    }
}
