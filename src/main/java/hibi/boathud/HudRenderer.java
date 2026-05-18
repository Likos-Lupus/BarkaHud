package hibi.boathud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class HudRenderer {

	private final Minecraft client;
	private int scaledWidth;
	private int scaledHeight;

	// Used for lerping
	private double displayedSpeed = 0.0d;

	public HudRenderer(Minecraft client) {
		this.client = client;
	}

	public void render(GuiGraphicsExtractor graphics, DeltaTracker counter) {
		this.scaledWidth = graphics.guiWidth();
		this.scaledHeight = graphics.guiHeight();
		int i = this.scaledWidth / 2;
		int nameLen = this.client.font.width(Common.hudData.name);

		// Lerping the displayed speed with the actual speed against how far we are into the tick not only is mostly accurate,
		// but gives the impression that it's being updated faster than 20 hz (which it isn't)
		this.displayedSpeed = Mth.lerp(counter.getGameTimeDeltaPartialTick(true), this.displayedSpeed, Common.hudData.speed);

		if(Config.extended) {
			// Overlay texture and bar
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_EXTENDED, i - 91, this.scaledHeight - 83, 182, 33);
			this.renderBar(graphics, i - 91, this.scaledHeight - 83);

			// Sprites
			if(Common.hudData.isDriver) {
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.client.options.keyLeft.isDown()? LEFT_LIT : LEFT_UNLIT, i - 86, this.scaledHeight - 65, 17, 8);
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.client.options.keyRight.isDown()? RIGHT_LIT : RIGHT_UNLIT, i - 63, this.scaledHeight - 65, 17, 8);
				// Brake-throttle bar
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.client.options.keyUp.isDown()? FORWARD_LIT : FORWARD_UNLIT, i, this.scaledHeight - 55, 61, 5);
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.client.options.keyDown.isDown()? BACKWARD_LIT : BACKWARD_UNLIT, i - 61, this.scaledHeight - 55, 61, 5);
			}

			// Ping
			this.renderPing(graphics, i + 75 - nameLen, this.scaledHeight - 65);
			
			// Text
			// First Row
			this.typeCentered(graphics, String.format(Config.speedFormat, this.displayedSpeed * Config.speedRate), i - 58, this.scaledHeight - 76, 0xFFFFFFFF);
			this.typeCentered(graphics, String.format(Config.angleFormat, Common.hudData.driftAngle), i, this.scaledHeight - 76, 0xFFFFFFFF);
			this.typeCentered(graphics, String.format(Config.gFormat, Common.hudData.g), i + 58, this.scaledHeight - 76, 0xFFFFFFFF);
			// Second Row
			graphics.text(this.client.font, Common.hudData.name, i + 88 - nameLen, this.scaledHeight - 65, 0xFFFFFFFF);

		} else { // Compact mode
			// Overlay texture and bar
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_COMPACT, i - 91, this.scaledHeight - 83, 182, 20);
			this.renderBar(graphics, i - 91, this.scaledHeight - 83);
			// Speed and drift angle
			this.typeCentered(graphics, String.format(Config.speedFormat, this.displayedSpeed * Config.speedRate), i - 58, this.scaledHeight - 76, 0xFFFFFFFF);
			this.typeCentered(graphics, String.format(Config.angleFormat, Common.hudData.driftAngle), i + 58, this.scaledHeight - 76, 0xFFFFFFFF);
		}
	}

	/** Renders the speed bar atop the HUD, uses displayedSpeed to, well, diisplay the speed. */
	private void renderBar(GuiGraphicsExtractor graphics, int x, int y) {
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Config.barType.unlitBar, x, y, 182, 5);
		int progress = Config.barType.getProgress(this.displayedSpeed);
		if (progress == 0) return;
		if (progress == -1) {
			if(this.client.level.getGameTime() % 2 == 0) return;
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Config.barType.litBar, x, y, 182, 5);
			return;
		}
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Config.barType.litBar, 182, 5, 0, 0, x, y, progress, 5);
	}

	/** Implementation is cloned from the notchian ping display in the tab player list.	 */
	private void renderPing(GuiGraphicsExtractor graphics, int x, int y) {
		Identifier bar = PING_5;
		if(Common.hudData.ping < 0) {
			bar = PING_UNKNOWN;
		}
		else if(Common.hudData.ping < 150) {
			bar = PING_5;
		}
		else if(Common.hudData.ping < 300) {
			bar = PING_4;
		}
		else if(Common.hudData.ping < 600) {
			bar = PING_3;
		}
		else if(Common.hudData.ping < 1000) {
			bar = PING_2;
		}
		else {
			bar = PING_1;
		}
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, bar, x, y, 10, 8);
	}

	/** Renders a piece of text centered horizontally on an X coordinate. */
	private void typeCentered(GuiGraphicsExtractor graphics, String text, int centerX, int y, int color) {
		graphics.text(this.client.font, text, centerX - this.client.font.width(text) / 2, y, color);
	}

	private static final Identifier
		BACKGROUND_EXTENDED = Identifier.fromNamespaceAndPath("boathud", "background_extended"),
		BACKGROUND_COMPACT = Identifier.fromNamespaceAndPath("boathud", "background_compact"),
		LEFT_UNLIT = Identifier.fromNamespaceAndPath("boathud", "left_unlit"),
		LEFT_LIT = Identifier.fromNamespaceAndPath("boathud", "left_lit"),
		RIGHT_UNLIT = Identifier.fromNamespaceAndPath("boathud", "right_unlit"),
		RIGHT_LIT = Identifier.fromNamespaceAndPath("boathud", "right_lit"),
		FORWARD_UNLIT = Identifier.fromNamespaceAndPath("boathud", "forward_unlit"),
		FORWARD_LIT = Identifier.fromNamespaceAndPath("boathud", "forward_lit"),
		BACKWARD_UNLIT = Identifier.fromNamespaceAndPath("boathud", "backward_unlit"),
		BACKWARD_LIT = Identifier.fromNamespaceAndPath("boathud", "backward_lit"),
		PING_5 = Identifier.fromNamespaceAndPath("boathud", "ping_5"),
		PING_4 = Identifier.fromNamespaceAndPath("boathud", "ping_4"),
		PING_3 = Identifier.fromNamespaceAndPath("boathud", "ping_3"),
		PING_2 = Identifier.fromNamespaceAndPath("boathud", "ping_2"),
		PING_1 = Identifier.fromNamespaceAndPath("boathud", "ping_1"),
		PING_UNKNOWN = Identifier.fromNamespaceAndPath("boathud", "ping_unknown")
	;
}
