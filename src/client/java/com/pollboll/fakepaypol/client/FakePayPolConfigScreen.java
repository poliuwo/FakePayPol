package com.pollboll.fakepaypol.client;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
public class FakePayPolConfigScreen extends Screen {
    private TextFieldWidget userField;
    private TextFieldWidget amountField;
    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 135;
    private static final int ACCENT_COLOR = 0xFF00FF00;
    private static final int BG_COLOR = 0xAA111111;
    private static final int TITLE_BG = 0xAA222222;
    private static final int INDICATOR_BG = 0xFF222226;
    private static final int INDICATOR_ACTIVE = 0xFFFFFFFF;
    private boolean dragging = false;
    private double dragX, dragY;
    public FakePayPolConfigScreen() {
        super(Text.literal("FakePayPol Configuration"));
    }
    @Override
    protected void init() {
        if (FakePayPolClient.windowX == -1) {
            FakePayPolClient.windowX = this.width / 2 - WINDOW_WIDTH / 2;
            FakePayPolClient.windowY = this.height / 2 - WINDOW_HEIGHT / 2;
        }
        updateWidgets();
    }
    private void updateWidgets() {
        this.clearChildren();
        int x = FakePayPolClient.windowX;
        int y = FakePayPolClient.windowY;
        this.userField = new CenteredTextFieldWidget(this.textRenderer, x + 56, y + 25, 128, 20, Text.literal("User"));
        this.userField.setMaxLength(16);
        this.userField.setText(FakePayPolClient.user);
        this.userField.setDrawsBackground(false);
        this.addDrawableChild(this.userField);
        this.amountField = new CenteredTextFieldWidget(this.textRenderer, x + 56, y + 48, 128, 20, Text.literal("Amount"));
        this.amountField.setText(FakePayPolClient.amount);
        this.amountField.setTextPredicate(text -> text.matches("^[0-9kmbtKMBT]*$"));
        this.amountField.setDrawsBackground(false);
        this.addDrawableChild(this.amountField);
    }
    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        int x = FakePayPolClient.windowX;
        int y = FakePayPolClient.windowY;
        double mouseX = click.x();
        double mouseY = click.y();
        if (mouseX >= x && mouseX <= x + WINDOW_WIDTH && mouseY >= y && mouseY <= y + 20) {
            this.dragging = true;
            this.dragX = mouseX - x;
            this.dragY = mouseY - y;
            return true;
        }
        if (mouseX >= x + 10 && mouseX <= x + 100 && mouseY >= y + 75 && mouseY <= y + 90) {
            FakePayPolClient.mode = 0;
            return true;
        }
        if (mouseX >= x + 10 && mouseX <= x + 100 && mouseY >= y + 92 && mouseY <= y + 107) {
            FakePayPolClient.mode = 1;
            return true;
        }
        if (mouseX >= x + 10 && mouseX <= x + 100 && mouseY >= y + 109 && mouseY <= y + 124) {
            FakePayPolClient.mode = 2;
            return true;
        }
        return super.mouseClicked(click, doubled);
    }
    @Override
    public boolean mouseReleased(Click click) {
        this.dragging = false;
        return super.mouseReleased(click);
    }
    @Override
    public boolean mouseDragged(Click click, double deltaX, double deltaY) {
        if (this.dragging) {
            FakePayPolClient.windowX = (int) (click.x() - this.dragX);
            FakePayPolClient.windowY = (int) (click.y() - this.dragY);
            updateWidgets();
            return true;
        }
        return super.mouseDragged(click, deltaX, deltaY);
    }
    @Override
    public void close() {
        FakePayPolClient.user = this.userField.getText();
        FakePayPolClient.amount = this.amountField.getText().toUpperCase();
        super.close();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x88000000);
        int x = FakePayPolClient.windowX;
        int y = FakePayPolClient.windowY;
        drawRoundedRect(context, x, y, WINDOW_WIDTH, WINDOW_HEIGHT, 8, BG_COLOR);
        drawRoundedRect(context, x, y, WINDOW_WIDTH, 20, 8, TITLE_BG);
        context.fill(x, y + 10, x + WINDOW_WIDTH, y + 20, TITLE_BG);
        context.fill(x, y + 20, x + WINDOW_WIDTH, y + 21, ACCENT_COLOR);
        context.drawTextWithShadow(this.textRenderer, "FAKEPAYPOL", x + 8, y + 6, ACCENT_COLOR);
        context.drawTextWithShadow(this.textRenderer, "_", x + WINDOW_WIDTH - 15, y + 4, 0xFFBBBBBB);
        context.drawTextWithShadow(this.textRenderer, "USER", x + 10, y + 31, 0xFFBBBBBB);
        context.drawTextWithShadow(this.textRenderer, "AMOUNT", x + 10, y + 54, 0xFFBBBBBB);
        drawRoundedRect(context, x + 56, y + 25, 128, 20, 4, 0x66000000);
        drawRoundedRect(context, x + 56, y + 48, 128, 20, 4, 0x66000000);
        renderCustomButton(context, x + 10, y + 75, "YOU PAID", FakePayPolClient.mode == 0);
        renderCustomButton(context, x + 10, y + 92, "PAID YOU", FakePayPolClient.mode == 1);
        renderCustomButton(context, x + 10, y + 109, "/PAY", FakePayPolClient.mode == 2);
        String footerText = "made by pol";
        int textWidth = this.textRenderer.getWidth(footerText);
        context.drawTextWithShadow(this.textRenderer, footerText, x + WINDOW_WIDTH - textWidth - 5, y + WINDOW_HEIGHT - 12, 0xFF888888);
        super.render(context, mouseX, mouseY, delta);
    }
    private void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        if (radius <= 0) {
            context.fill(x, y, x + width, y + height, color);
            return;
        }
        radius = Math.min(radius, Math.min(width / 2, height / 2));
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);
        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                float dx = radius - i - 0.5f;
                float dy = radius - j - 0.5f;
                if (dx * dx + dy * dy <= radius * radius) {
                    context.fill(x + i, y + j, x + i + 1, y + j + 1, color);
                    context.fill(x + width - 1 - i, y + j, x + width - i, y + j + 1, color);
                    context.fill(x + i, y + height - 1 - j, x + i + 1, y + height - j, color);
                    context.fill(x + width - 1 - i, y + height - 1 - j, x + width - i, y + height - j, color);
                }
            }
        }
    }
    private void renderCustomButton(DrawContext context, int x, int y, String label, boolean active) {
        int boxY = y + 2;
        drawRoundedRect(context, x, boxY, 10, 10, 5, 0xFF444448);
        drawRoundedRect(context, x + 1, boxY + 1, 8, 8, 4, INDICATOR_BG);
        if (active) {
            drawRoundedRect(context, x + 2, boxY + 2, 6, 6, 3, INDICATOR_ACTIVE);
        }
        context.drawTextWithShadow(this.textRenderer, label.toUpperCase(), x + 16, y + 3, active ? 0xFFFFFFFF : 0xFFBBBBBB);
    }
}
