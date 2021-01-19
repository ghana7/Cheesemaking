package ghana7.cheesemaking;

import ghana7.cheesemaking.block.CurdingTub;
import ghana7.cheesemaking.block.MilkBlock;
import ghana7.cheesemaking.item.Curd;
import ghana7.cheesemaking.item.Rennet;
import ghana7.cheesemaking.item.SaturatingStilton;
import ghana7.cheesemaking.rendering.CurdingTubTileEntityRenderer;
import ghana7.cheesemaking.tileentity.CurdingTubTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CheesemakingMod.MODID)
public class CheesemakingMod {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cheesemaking";

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    //blocks
    public static final RegistryObject<Block> CURDING_TUB = BLOCKS.register("curding_tub", () ->
            new CurdingTub()
    );

    public static final RegistryObject<Block> MILK_BLOCK = BLOCKS.register("milk_block", () ->
            new MilkBlock()
    );

    //blockitems
    public static final RegistryObject<Item> CURDING_TUB_BI = ITEMS.register("curding_tub", () ->
            new BlockItem(CURDING_TUB.get(), new Item.Properties().group(ItemGroup.DECORATIONS))
    );

    public static final RegistryObject<Item> MILK_BLOCK_BI = ITEMS.register("milk_block", () ->
            new BlockItem(MILK_BLOCK.get(), new Item.Properties().group(ItemGroup.DECORATIONS))
    );

    //items
    public static final RegistryObject<Item> RENNET = ITEMS.register("rennet", () ->
            new Rennet()
    );

    public static final RegistryObject<Item> CURD = ITEMS.register("curd", () ->
            new Curd()
    );

    public static final RegistryObject<Item> SATURATING_STILTON = ITEMS.register("saturating_stilton", () ->
            new SaturatingStilton()
    );

    //tile entities
    public static final RegistryObject<TileEntityType<CurdingTubTileEntity>> CURDING_TUB_TE = TILE_ENTITY_TYPES.register(
            "curding_tub", () -> TileEntityType.Builder.create(CurdingTubTileEntity::new, CURDING_TUB.get()).build(null)
    );

    public CheesemakingMod() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        ClientRegistry.bindTileEntityRenderer(CURDING_TUB_TE.get(), CurdingTubTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(CURDING_TUB.get(), RenderType.getTranslucent());
    }


}
