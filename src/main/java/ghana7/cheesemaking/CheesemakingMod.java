package ghana7.cheesemaking;

import ghana7.cheesemaking.block.CheeseRack;
import ghana7.cheesemaking.block.CurdingTub;
import ghana7.cheesemaking.block.MilkBlock;
import ghana7.cheesemaking.item.*;
import ghana7.cheesemaking.item.cheese.*;
import ghana7.cheesemaking.rendering.CheeseRackTileEntityRenderer;
import ghana7.cheesemaking.rendering.CurdingTubTileEntityRenderer;
import ghana7.cheesemaking.tileentity.CheeseRackTileEntity;
import ghana7.cheesemaking.tileentity.CurdingTubTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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

    public static final RegistryObject<Block> CHEESE_RACK = BLOCKS.register("cheese_rack", () ->
            new CheeseRack()
    );
    //blockitems
    public static final RegistryObject<Item> CURDING_TUB_BI = ITEMS.register("curding_tub", () ->
            new BlockItem(CURDING_TUB.get(), new Item.Properties().group(ItemGroup.DECORATIONS))
    );

    public static final RegistryObject<Item> MILK_BLOCK_BI = ITEMS.register("milk_block", () ->
            new BlockItem(MILK_BLOCK.get(), new Item.Properties().group(ItemGroup.DECORATIONS))
    );

    public static final RegistryObject<Item> CHEESE_RACK_BI = ITEMS.register("cheese_rack", () ->
            new BlockItem(CHEESE_RACK.get(), new Item.Properties().group(ItemGroup.DECORATIONS))
    );

    //items
    public static final RegistryObject<Item> RENNET = ITEMS.register("rennet", () ->
            new Rennet()
    );

    //t0 cheeses
    public static final RegistryObject<Item> CURD = ITEMS.register("curd", () ->
            new Curd()
    );

    public static final RegistryObject<Item> FLAWED_CHEESE = ITEMS.register("flawed_cheese", () ->
            new FlawedCheese()
    );

    //t1 cheeses
    public static final RegistryObject<Item> BORING_BRIE = ITEMS.register("boring_brie", () ->
            new BoringBrie()
    );

    public static final RegistryObject<Item> GENERIC_GRUYERE = ITEMS.register("generic_gruyere", () ->
            new GenericGruyere()
    );

    public static final RegistryObject<Item> SATURATING_STILTON = ITEMS.register("saturating_stilton", () ->
            new SaturatingStilton()
    );

    public static final RegistryObject<Item> SNACK_JACK = ITEMS.register("snack_jack", () ->
            new SnackJack()
    );

    //t2 cheeses
    public static final RegistryObject<Item> AIRY_AMERICAN = ITEMS.register("airy_american", () ->
            new AiryAmerican()
    );

    public static final RegistryObject<Item> FLAMING_FETA = ITEMS.register("flaming_feta", () ->
            new FlamingFeta()
    );

    public static final RegistryObject<Item> HEALTHY_HAVARTI = ITEMS.register("healthy_havarti", () ->
            new HealthyHavarti()
    );

    public static final RegistryObject<Item> LUCKY_LIMBURGER = ITEMS.register("lucky_limburger", () ->
            new LuckyLimberger()
    );

    public static final RegistryObject<Item> POWERFUL_PARMESAN = ITEMS.register("powerful_parmesan", () ->
            new PowerfulParmesan()
    );

    public static final RegistryObject<Item> RAPID_RICOTTA = ITEMS.register("rapid_ricotta", () ->
            new RapidRicotta()
    );

    public static final RegistryObject<Item> REGENERATING_ROQUEFORT = ITEMS.register("regenerating_roquefort", () ->
            new RegeneratingRoquefort()
    );

    public static final RegistryObject<Item> RISING_ROMANO = ITEMS.register("rising_romano", () ->
            new RisingRomano()
    );

    public static final RegistryObject<Item> SWIMMING_SWISS = ITEMS.register("swimming_swiss", () ->
            new SwimmingSwiss()
    );

    public static final RegistryObject<Item> YOU_SEE_GOUDA = ITEMS.register("you_see_gouda", () ->
            new YouSeeGouda()
    );

    //t3 cheeses

    public static final RegistryObject<Item> BULKY_BLEU = ITEMS.register("bulky_bleu", () ->
            new BulkyBleu()
    );

    public static final RegistryObject<Item> CHAOTIC_CHEDDAR = ITEMS.register("chaotic_cheddar", () ->
            new ChaoticCheddar()
    );

    public static final RegistryObject<Item> GIGANTIC_GORGONZOLA = ITEMS.register("gigantic_gorgonzola", () ->
            new AiryAmerican()
    );

    public static final RegistryObject<Item> MINERS_MOZZARELLA = ITEMS.register("miners_mozzarella", () ->
            new MinersMozzarella()
    );




    //tile entities
    public static final RegistryObject<TileEntityType<CurdingTubTileEntity>> CURDING_TUB_TE = TILE_ENTITY_TYPES.register(
            "curding_tub", () -> TileEntityType.Builder.create(CurdingTubTileEntity::new, CURDING_TUB.get()).build(null)
    );

    public static final RegistryObject<TileEntityType<CheeseRackTileEntity>> CHEESE_RACK_TE = TILE_ENTITY_TYPES.register(
            "cheese_rack", () -> TileEntityType.Builder.create(CheeseRackTileEntity::new, CHEESE_RACK.get()).build(null)
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
        ClientRegistry.bindTileEntityRenderer(CHEESE_RACK_TE.get(), CheeseRackTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(CURDING_TUB.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(CHEESE_RACK.get(), RenderType.getTranslucent());
    }


}
