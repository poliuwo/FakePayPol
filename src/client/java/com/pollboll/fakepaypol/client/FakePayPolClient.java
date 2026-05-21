package com.pollboll.fakepaypol.client;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;
public class FakePayPolClient implements ClientModInitializer {
    private static KeyBinding bKeyBind;
    public static String user = "";
    public static String amount = "";
    public static int mode = 0;
    public static int windowX = -1;
    public static int windowY = -1;
    @Override
    public void onInitializeClient() {
        bKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open FakePayPol GUI",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KeyBinding.Category.MISC
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (bKeyBind.wasPressed()) {
                boolean altPressed = InputUtil.isKeyPressed(client.getWindow(), GLFW.GLFW_KEY_LEFT_ALT);
                if (altPressed) {
                    sendPaymentMessage(client, user, amount, mode == 0 || mode == 2, false);
                } else {
                    client.setScreen(new FakePayPolConfigScreen());
                }
            }
        });
        ClientSendMessageEvents.ALLOW_COMMAND.register(command -> {
            if (mode == 2 && command.startsWith("pay ")) {
                String[] parts = command.split(" ");
                if (parts.length >= 3) {
                    String targetUser = parts[1];
                    String targetAmount = parts[2];
                    MinecraftClient client = MinecraftClient.getInstance();
                    sendPaymentMessage(client, targetUser, targetAmount, true, true);
                    return false;
                }
            }
            return true;
        });
    }
    private void sendPaymentMessage(MinecraftClient client, String messageUser, String messageAmount, boolean youPaid, boolean playSound) {
        if (client.player == null) return;
        MutableText message = Text.empty();
        int restColor = 0xAAAAAA;
        int userColor = 0x00AAFF;
        int amountColor = 0x00FF00;
        String displayAmount = messageAmount.toUpperCase();
        if (youPaid) {
            message.append(Text.literal("You paid ").setStyle(Style.EMPTY.withColor(restColor)))
                   .append(Text.literal(messageUser).setStyle(Style.EMPTY.withColor(userColor)))
                   .append(Text.literal(" ").setStyle(Style.EMPTY.withColor(restColor)))
                   .append(Text.literal("$").setStyle(Style.EMPTY.withColor(amountColor)))
                   .append(Text.literal(displayAmount).setStyle(Style.EMPTY.withColor(amountColor)))
                   .append(Text.literal(".").setStyle(Style.EMPTY.withColor(restColor)));
        } else {
            message.append(Text.literal(messageUser).setStyle(Style.EMPTY.withColor(userColor)))
                   .append(Text.literal(" paid you ").setStyle(Style.EMPTY.withColor(restColor)))
                   .append(Text.literal("$").setStyle(Style.EMPTY.withColor(amountColor)))
                   .append(Text.literal(displayAmount).setStyle(Style.EMPTY.withColor(amountColor)))
                   .append(Text.literal(".").setStyle(Style.EMPTY.withColor(restColor)));
        }
        client.inGameHud.getChatHud().addMessage(message);
        if (playSound) {
            client.player.playSound(net.minecraft.sound.SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        }
    }
}
