package ghana7.cheesemaking.tileentity;

import ghana7.cheesemaking.CheesemakingMod;
import ghana7.cheesemaking.item.Cheese;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CheeseRackTileEntity extends TileEntity implements ITickableTileEntity {
    public ItemStackHandler itemHandler = createHandler();
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(8) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof Cheese;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
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

    private int timerMax = 1200; //60 seconds
    private int timer = timerMax;

    public CheeseRackTileEntity() {
        super(CheesemakingMod.CHEESE_RACK_TE.get());
        timer = timerMax;
    }

    @Override
    public void tick() {
        if(world.isRemote) {
            return;
        }

        if(timer > 0) {
            if(true) {
                timer--;
            }
        }

        if(timer <= 0) {
            timer = timerMax;
            //CheesemakingMod.LOGGER.debug(getEnvironmentType());
            Cheese.EnvironmentType envType = getEnvironmentType();
            for(int i = 0; i < 8; i++) {
                if(itemHandler.getStackInSlot(i).getItem() instanceof Cheese) {
                    Cheese cheeseItem = (Cheese)(itemHandler.getStackInSlot(i).getItem());
                    ItemStack newCheese = new ItemStack(cheeseItem.getAged(envType), 1);
                    itemHandler.extractItem(i, 1, false);
                    itemHandler.insertItem(i, newCheese, false);
                }
            }
        }
    }

    private Cheese.EnvironmentType getEnvironmentType() {
        if(world.getDimensionKey().equals(World.THE_NETHER)) {
            return Cheese.EnvironmentType.NETHER;
        }
        if(world.canBlockSeeSky(pos)) {
            if(pos.getY() > 120) {
                return Cheese.EnvironmentType.SKY;
            } else {
                return Cheese.EnvironmentType.LAND;
            }
        } else {
            if(pos.getY() < 60) {
                return Cheese.EnvironmentType.CAVE;
            } else {
                return Cheese.EnvironmentType.BUILDING;
            }
        }


    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        timer = tag.getInt("timer");
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.putInt("timer", timer);
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

}
