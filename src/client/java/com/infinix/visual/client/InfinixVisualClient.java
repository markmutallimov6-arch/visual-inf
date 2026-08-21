package com.infinix.visual.client;

import com.infinix.visual.InfinixVisual;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class InfinixVisualClient implements ClientModInitializer {

    // Клавиша: правый Shift, открывает пустое меню (пока без функций)
    private static final KeyBinding OPEN_MENU_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.infinix-visual.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.infinix-visual.main"
    ));

    @Override
    public void onInitializeClient() {
        InfinixVisual.LOGGER.info("Infinix Visual: инициализация (client)");

        // Открытие меню по правому Shift, только когда мы в игре (не в других экранах/GUI)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_MENU_KEY.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new InfinixMenuScreen());
                }
            }
        });

        // Перестраиваем главное меню: чёрный фон, только Singleplayer/Multiplayer, надпись Infinix Visual
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof TitleScreen titleScreen)) {
                return;
            }

            // Убираем все ванильные кнопки/виджеты (Realms, моды, опции, соцсети и т.д.)
            screen.clearChildren();

            int centerX = scaledWidth / 2;
            int buttonWidth = 200;
            int buttonHeight = 20;
            int startY = scaledHeight / 2 - buttonHeight;

            ButtonWidget singleplayer = ButtonWidget.builder(
                    Text.literal("Одиночная игра"),
                    button -> client.setScreen(new SelectWorldScreen(titleScreen))
            ).dimensions(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight).build();

            ButtonWidget multiplayer = ButtonWidget.builder(
                    Text.literal("Сетевая игра"),
                    button -> client.setScreen(new MultiplayerScreen(titleScreen))
            ).dimensions(centerX - buttonWidth / 2, startY + buttonHeight + 8, buttonWidth, buttonHeight).build();

            screen.addDrawableChild(singleplayer);
            screen.addDrawableChild(multiplayer);

            // Чёрный фон и заголовок рисуем до отрисовки ванильных элементов (панорама/лого перекрываются)
            ScreenEvents.beforeRender(screen).register((s, context, mouseX, mouseY, tickDelta) -> {
                context.fill(0, 0, scaledWidth, scaledHeight, 0xFF000000);
                context.drawCenteredTextWithShadow(
                        client.textRenderer,
                        Text.literal("Infinix Visual"),
                        centerX,
                        scaledHeight / 2 - 60,
                        0xFFFFFFFF
                );
            });
        });
    }
}
