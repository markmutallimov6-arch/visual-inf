package com.infinix.visual.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Пустая заготовка меню, открывается на правый Shift.
 * Сюда позже можно будет добавить вкладки/категории с функциями.
 */
public class InfinixMenuScreen extends Screen {

    public InfinixMenuScreen() {
        super(Text.literal("Infinix Visual"));
    }

    @Override
    protected void init() {
        super.init();
        // Пока без кнопок и функций — только пустое окно.
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Полупрозрачная затемнённая подложка поверх игры (не пауза, игра не блокируется)
        context.fill(0, 0, this.width, this.height, 0x88000000);

        int panelWidth = 260;
        int panelHeight = 180;
        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        // Чёрная панель меню
        context.fill(x, y, x + panelWidth, y + panelHeight, 0xFF0A0A0A);
        // Тонкая рамка
        context.drawBorder(x, y, panelWidth, panelHeight, 0xFF2A2A2A);

        // Заголовок
        context.drawCenteredTextWithShadow(this.textRenderer, "Infinix Visual",
                this.width / 2, y + 12, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "функции скоро",
                this.width / 2, y + 28, 0xFF888888);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        // Меню не ставит игру на паузу в одиночной игре
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
