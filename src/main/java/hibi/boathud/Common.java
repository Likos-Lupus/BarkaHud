package hibi.boathud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class Common {

	public static HudData hudData;
	public static Minecraft client = null;
	public static boolean ridingBoat = false;
	public static HudRenderer hudRenderer;

	public void onInitializeClient() {
		client = Minecraft.getInstance();
		hudRenderer = new HudRenderer(client);
		Config.load();
		ClientTickEvents.END_LEVEL_TICK.register(clientWorld -> {
			if(client.player == null) return;
			if(client.player.getVehicle() instanceof AbstractBoat boat && boat.getFirstPassenger() == client.player) {
				if (hudData == null) {
					hudData = new HudData();
				}
				hudData.update();
			}
			else {
				if (ridingBoat) {
					ridingBoat = false;
				}
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(client.player == null) return;
			if(!Config.cameraControl) return;
			if(client.player.getVehicle() instanceof AbstractBoat boat && boat.getControllingPassenger() == client.player) {
				CameraHandler.tick(boat, client.player);
			}
		});
	}
}
