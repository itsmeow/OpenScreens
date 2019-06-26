package its_meow.openscreens.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

import its_meow.openscreens.common.tileentity.TileEntityHoloScreen;
import li.cil.oc.Settings;
import li.cil.oc.util.RenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;

public class RenderHoloScreen extends TileEntitySpecialRenderer<TileEntityHoloScreen> {

    public double maxRenderDistanceSq = Settings.get().maxScreenTextRenderDistance() * Settings.get().maxScreenTextRenderDistance();

    public double fadeDistanceSq = Settings.get().screenTextFadeStartDistance() * Settings.get().screenTextFadeStartDistance();

    public double fadeRatio = 1.0D / (maxRenderDistanceSq - fadeDistanceSq);

    public TileEntityHoloScreen screen = null;

    public boolean canUseBlendColor = GLContext.getCapabilities().OpenGL14;

    @Override
    public void render(TileEntityHoloScreen te, double x, double y, double z, float partialTicks, int destroyStage, float a) {
        if(te.height() < 1 || te.width() < 1) {
            return;
        }
        RenderState.checkError(this.getClass().getName() + ".render: entering (aka: wasntme)");

        this.screen = te;
        if(!screen.isOrigin()) {
            return;
        }

        double distance = playerDistanceSq() / Math.min(screen.width(), screen.height());
        if(distance > maxRenderDistanceSq) {
            return;
        }

        RenderState.checkError(this.getClass().getName() + ".render: checks");

        RenderState.pushAttrib();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0xFF, 0xFF);
        RenderState.disableEntityLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1, 1, 1, 1);

        GlStateManager.pushMatrix();

        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);

        RenderState.checkError(this.getClass().getName() + ".render: setup");

        if(distance > fadeDistanceSq) {
            float alpha = (float) Math.max(0, 1 - ((distance - fadeDistanceSq) * fadeRatio));
            if(canUseBlendColor) {
                GL14.glBlendColor(0, 0, 0, alpha);
                GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE);
            }
        }

        RenderState.checkError(this.getClass().getName() + ".render: fade");

        if(screen.buffer().isRenderingEnabled()) {
            draw();
        }

        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        RenderState.enableEntityLighting();
        GlStateManager.popMatrix();
        RenderState.popAttrib();

        RenderState.checkError(this.getClass().getName() + ".render: leaving");
    }

    public void transform() {
        switch(screen.yaw()) {
        case WEST:
            GlStateManager.rotate(-90, 0, 1, 0);
            break;
        case NORTH:
            GlStateManager.rotate(180, 0, 1, 0);
            break;
        case EAST:
            GlStateManager.rotate(90, 0, 1, 0);
            break;
        default:
            break;
        }

        // Fit area to screen (bottom left = bottom left).
        GlStateManager.translate(-0.5f, -0.5f, 0.5f);

        GlStateManager.translate(0, screen.height(), 0);

        // Flip text upside down.
        GlStateManager.scale(1, -1, 1);

    }

    private void draw() {
        RenderState.checkError(this.getClass().getName() + ".draw: entering (aka: wasntme)");

        float sx = screen.width();
        float sy = screen.height();
        float tw = sx * 16f;
        float th = sy * 16f;

        transform();

        float border = 0.5F;
        int hex = screen.getColor();
        hex = hex == 11250603 || hex == 4473924 ? 0x000000 : hex;
        float r = (hex & 16711680) >> 16;
        float g = (hex & 65280) >> 8;
        float b = (hex & 255) >> 0;
        r *= 255;
        g *= 255;
        b *= 255;
        float a = 0.25F;

        // Jeb sheep code
        EntityPlayer ep = Minecraft.getMinecraft().player;
        if(ep.world != null) {
            boolean foundMeow = false;
            for(EntityPlayer player : ep.world.getEntitiesWithinAABB(EntityPlayer.class, ep.getEntityBoundingBox().grow(10D))) {
                if(player.getGameProfile().getId().toString().equals("81d9726a-56d4-4419-9a2a-be1d7f7f7ef1")) {
                    foundMeow = true;
                }
            }
            if(foundMeow || (ep.getGameProfile().getId().toString().equals("81d9726a-56d4-4419-9a2a-be1d7f7f7ef1") && ep.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.REDSTONE)) {
                float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
                int i = ep.ticksExisted / 25 + ep.getEntityId();
                int j = EnumDyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f = ((float) (ep.ticksExisted % 25) + partialTicks) / 25.0F;
                float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
                float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));

                r = afloat1[0] * (1.0F - f) + afloat2[0] * f;
                g = afloat1[1] * (1.0F - f) + afloat2[1] * f;
                b = afloat1[2] * (1.0F - f) + afloat2[2] * f;
            }
        }

        GlStateManager.disableCull();

        if(screen.width() > 1) {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0, (screen.height() - 1), -0.46);
                GlStateManager.color(1, 1, 1, 1);
                GlStateManager.disableTexture2D();

                Tessellator t = Tessellator.getInstance();
                BufferBuilder buff = t.getBuffer();

                buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

                buff.pos(0, 0.6, 0).color(r, g, b, a).endVertex();
                buff.pos(1, 0.6, 0).color(r, g, b, a).endVertex();
                buff.pos((screen.width() + 1) / 2F, 0, 0).color(r, g, b, a).endVertex();
                buff.pos(-((screen.width() - 1) / 2F), 0, 0).color(r, g, b, a).endVertex();

                t.draw();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.popMatrix();
        }

        GlStateManager.translate(-(sx - 1) / 2, -1, 0);

        a = 0.5F;

        drawBackground(r, g, b, a);

        // Offset from border.
        GlStateManager.translate(sx * border / tw, sy * border / th, 0);

        // Inner size (minus borders).
        float isx = sx - (border / 8);
        float isy = sy - (border / 8);

        // Scale based on actual buffer size.
        float sizeX = screen.buffer().renderWidth();
        float sizeY = screen.buffer().renderHeight();
        float scaleX = isx / sizeX;
        float scaleY = isy / sizeY;
        if(scaleX > scaleY) {
            GlStateManager.translate(sizeX * 0.5f * (scaleX - scaleY), 0, 0);
            GlStateManager.scale(scaleY, scaleY, 1);
        } else {
            GlStateManager.translate(0, sizeY * 0.5f * (scaleY - scaleX), 0);
            GlStateManager.scale(scaleX, scaleX, 1);
        }

        // Slightly offset the text so it doesn't clip into the screen.
        GlStateManager.translate(0, 0, -0.46 + 0.01);

        RenderState.checkError(this.getClass().getName() + ".draw: setup");

        // Render the actual text.
        screen.buffer().renderText();

        GlStateManager.translate(0, 0, -0.02);

        screen.buffer().renderText();

        RenderState.checkError(this.getClass().getName() + ".draw: text");

        GlStateManager.enableCull();
    }

    private void drawBackground(float r, float g, float b, float a) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, 0, -0.46);
            // Draw background
            drawColoredQuad(r, g, b, a, 0F, 0F, screen.width(), screen.height());

            // Draw borders
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(0, 0, 0.001);
                float a1 = 0.75F;
                drawColoredQuad(r, g, b, a1, 0F, 0F, 0.01F, screen.height());
                drawColoredQuad(r, g, b, a1, screen.width() - 0.01F, 0F, screen.width(), screen.height());
                if(screen.width() == 1) {
                    drawColoredQuad(r, g, b, a1, 0F, screen.height() - 0.01F, screen.width(), screen.height());
                }
                drawColoredQuad(r, g, b, a1, 0F, 0F, screen.width(), 0.01F);
                GlStateManager.translate(0, 0, -0.002);
                drawColoredQuad(r, g, b, a1, 0F, 0F, 0.01F, screen.height());
                drawColoredQuad(r, g, b, a1, screen.width() - 0.01F, 0F, screen.width(), screen.height());
                if(screen.width() == 1) {
                    drawColoredQuad(r, g, b, a1, 0F, screen.height() - 0.01F, screen.width(), screen.height());
                }
                drawColoredQuad(r, g, b, a1, 0F, 0F, screen.width(), 0.01F);
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }

    private static void drawColoredQuad(float r, float g, float b, float a, float x1, float y1, float x2, float y2) {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buff = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableTexture2D();

            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

            buff.pos(x1, y2, 0).color(r, g, b, a).endVertex();
            buff.pos(x2, y2, 0).color(r, g, b, a).endVertex();
            buff.pos(x2, y1, 0).color(r, g, b, a).endVertex();
            buff.pos(x1, y1, 0).color(r, g, b, a).endVertex();

            t.draw();
            GlStateManager.enableTexture2D();
        }
        GlStateManager.popMatrix();
    }

    public double playerDistanceSq() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        AxisAlignedBB bounds = screen.getRenderBoundingBox();

        double px = player.posX;
        double py = player.posY;
        double pz = player.posZ;

        double ex = bounds.maxX - bounds.minX;
        double ey = bounds.maxY - bounds.minY;
        double ez = bounds.maxZ - bounds.minZ;
        double cx = bounds.minX + ex * 0.5;
        double cy = bounds.minY + ey * 0.5;
        double cz = bounds.minZ + ez * 0.5;
        double dx = px - cx;
        double dy = py - cy;
        double dz = pz - cz;

        return fixDiff(dx, ex) + fixDiff(dy, ey) + fixDiff(dz, ez);
    }

    private static double fixDiff(double a, double b) {
        if(a < -b) {
            double d = a + b;
            return d * d;
        } else if(a > b) {
            double d = a - b;
            return d * d;
        } else {
            return 0D;
        }
    }

}