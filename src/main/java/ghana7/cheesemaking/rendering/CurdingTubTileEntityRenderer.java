package ghana7.cheesemaking.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import ghana7.cheesemaking.CheesemakingMod;
import ghana7.cheesemaking.tileentity.CurdingTubTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class CurdingTubTileEntityRenderer extends TileEntityRenderer<CurdingTubTileEntity> {
    public CurdingTubTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(CurdingTubTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(tileEntityIn.getMilk() > 0) {
            matrixStackIn.push();
            matrixStackIn.translate(0.0625f, 0, 0.0625f);
            matrixStackIn.translate(0, 0.0625f * 1.5f * (tileEntityIn.getMilk() / 1000), 0);
            matrixStackIn.scale(0.875f, 0.0625f, 0.875f);

            BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockState state = CheesemakingMod.MILK_BLOCK.get().getDefaultState();
            blockRenderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
            matrixStackIn.pop();
        }
        int numRennets = tileEntityIn.itemHandler.getStackInSlot(0).getCount();
        ItemStack rennetItemStack = tileEntityIn.itemHandler.getStackInSlot(0);
        int numCurds = tileEntityIn.itemHandler.getStackInSlot(1).getCount();
        ItemStack curdItemStack = tileEntityIn.itemHandler.getStackInSlot(1);
        matrixStackIn.push();
        RenderHelper.disableStandardItemLighting();
        if(numRennets > 0) {
            matrixStackIn.push();
            matrixStackIn.translate(0.25, 0.375, 0.25);
            matrixStackIn.scale(0.5f,0.5f, 0.5f);
            Minecraft.getInstance().getItemRenderer().renderItem(rennetItemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
        if(numRennets > 1) {

            matrixStackIn.push();
            matrixStackIn.translate(0.75, 0.375, 0.25);
            matrixStackIn.scale(0.5f,0.5f, 0.5f);
            Minecraft.getInstance().getItemRenderer().renderItem(rennetItemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
        if(numRennets > 2) {
            matrixStackIn.push();
            matrixStackIn.translate(0.75, 0.375, 0.75);
            matrixStackIn.scale(0.5f,0.5f, 0.5f);
            Minecraft.getInstance().getItemRenderer().renderItem(rennetItemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
        if(numRennets > 3) {
            matrixStackIn.push();
            matrixStackIn.translate(0.25, 0.375, 0.75);
            matrixStackIn.scale(0.5f,0.5f, 0.5f);
            Minecraft.getInstance().getItemRenderer().renderItem(rennetItemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
        if(numCurds > 0) {
            matrixStackIn.push();
            matrixStackIn.translate(0.5, 0.0625 + 0.0625f * 1.5f * (tileEntityIn.getMilk() / 1000), 0.5);
            matrixStackIn.rotate(new Quaternion(new Vector3f(1.0f,0.0f,0.0f), 90, true));
            matrixStackIn.scale(0.5f,0.5f, 0.5f);
            Minecraft.getInstance().getItemRenderer().renderItem(curdItemStack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
        RenderHelper.enableStandardItemLighting();
        matrixStackIn.pop();
    }
}
