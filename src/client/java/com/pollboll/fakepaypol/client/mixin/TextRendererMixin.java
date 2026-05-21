package com.pollboll.fakepaypol.client.mixin;

import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
    @ModifyVariable(method = "drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int modifyShadowColor(int color, String text, float x, float y, int originalColor, boolean shadow) {
        if (!shadow) return color;

        // Extract RGB
        int rgb = color & 0xFFFFFF;

        // Standard Minecraft shadows are (color & 0xFCFCFC) >> 2.
        // For our colors:
        // 0x00AAFF (User) -> 0x002A3F
        // 0x00FF00 (Amount) -> 0x003F00
        // 0xAAAAAA (Rest) -> 0x2A2A2A

        if (rgb == 0x002A3F) return (color & 0xFF000000) | 0x004477; // User shadow
        if (rgb == 0x003F00) return (color & 0xFF000000) | 0x003F00; // Amount shadow
        if (rgb == 0x2A2A2A) return (color & 0xFF000000) | 0x2A2A2A; // Rest shadow

        return color;
    }
}
