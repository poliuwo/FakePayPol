package com.pollboll.fakepaypol.client;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
public class CenteredTextFieldWidget extends TextFieldWidget {
    private final TextRenderer textRenderer;
    public CenteredTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);
        this.textRenderer = textRenderer;
    }
    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!this.visible) {
            return;
        }
        String text = this.getText();
        int color = 0xFFE0E0E0;
        int textWidth = this.textRenderer.getWidth(text);
        int centerX = this.getX() + (this.width / 2);
        int centerY = this.getY() + (this.height - 8) / 2;
        context.drawTextWithShadow(this.textRenderer, text, centerX - (textWidth / 2), centerY, color);
        if (this.isFocused() && (System.currentTimeMillis() / 500) % 2 == 0) {
            int cursorX = centerX + (textWidth / 2) + 1;
            context.fill(cursorX, centerY, cursorX + 1, centerY + 9, 0xFFD0D0D0);
        }
    }
}
