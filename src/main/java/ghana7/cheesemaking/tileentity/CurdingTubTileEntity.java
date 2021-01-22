package ghana7.cheesemaking.tileentity;

import ghana7.cheesemaking.CheesemakingMod;
import ghana7.cheesemaking.item.cheese.Curd;
import ghana7.cheesemaking.item.Rennet;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CurdingTubTileEntity extends TileEntity implements ITickableTileEntity {
    public ItemStackHandler itemHandler = createHandler();
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot == 0) {
                    return stack.getItem() instanceof Rennet;
                }
                if(slot == 1) {
                    return stack.getItem() instanceof Curd;
                }
                return true;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 4;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
                return super.extractItem(slot, amount, simulate);
            }
        };
    }

    public NonNullList<ItemStack> getAllItems() {
        NonNullList<ItemStack> items = NonNullList.create();
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            items.add(stack);
        }
        return items;
    }

    private int timerMax = 60; //3 seconds
    private int timer = timerMax;
    private int milkCapacity = 4000;
    private int currentMilkAmount = 0;

    public CurdingTubTileEntity() {
        super(CheesemakingMod.CURDING_TUB_TE.get());
        timer = timerMax;
    }

    @Override
    public void tick() {
        if(world.isRemote) {
            return;
        }

        if(timer > 0) {
            if(currentMilkAmount >= 1000 && !itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(1).getCount() < 4) {
                timer--;
            }
        }

        if(timer <= 0) {
            timer = timerMax;
            removeMilk(1000);
            itemHandler.extractItem(0, 1, false);
            itemHandler.insertItem(1, new ItemStack(CheesemakingMod.CURD.get(), 1), false);
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        timer = tag.getInt("timer");
        currentMilkAmount = tag.getInt("milkAmount");
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.putInt("timer", timer);
        tag.putInt("milkAmount", currentMilkAmount);
        return super.write(tag);
    }
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        read(state, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);

        // the number here is generally ignored for non-vanilla TileEntities, 0 is safest
        return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        this.read(getBlockState(), packet.getNbtCompound());
    }

    public boolean addMilk(int amount) {
        if(currentMilkAmount + amount <= milkCapacity) {
            currentMilkAmount += amount;
            //CheesemakingMod.LOGGER.debug(currentMilkAmount);
            markDirty();
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
            return true;
        }
        return false;
    }

    public boolean removeMilk(int amount) {
        if(currentMilkAmount >= amount) {
            currentMilkAmount -= amount;
            //CheesemakingMod.LOGGER.debug(currentMilkAmount);
            markDirty();
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
            return true;
        }
        return false;
    }

    public int getMilk() {
        return currentMilkAmount;
    }

}
