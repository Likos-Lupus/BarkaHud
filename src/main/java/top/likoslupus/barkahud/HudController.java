package top.likoslupus.barkahud;

import org.jspecify.annotations.NonNull;
import top.likoslupus.barkahud.core.snapshot.HudSnapshot;
import top.likoslupus.barkahud.core.telemetry.BoatTelemetry;
import top.likoslupus.barkahud.platform.IClientSnapshotProvider;

public class HudController {

    private final HudRenderer hudRenderer;
    private final IClientSnapshotProvider snapshotProvider;
    private HudSnapshot currentSnapshot = HudSnapshot.EMPTY;
    private double oldSpeed;
    private boolean firstFrame = true;

    public HudController(@NonNull IClientSnapshotProvider snapshotProvider) {
        this.snapshotProvider = snapshotProvider;
        this.hudRenderer = new HudRenderer();
    }

    public HudSnapshot getSnapshot() {
        return currentSnapshot;
    }

    public HudRenderer getRenderer() {
        return hudRenderer;
    }

    public void tick() {
        var snapshot = snapshotProvider.capture();
        if (snapshot == HudSnapshot.EMPTY) {
            setEmpty();
            return;
        }
        double gForce = firstFrame ? 0 : BoatTelemetry.calculateGForce(snapshot.speedMS(), oldSpeed);
        firstFrame = false;
        oldSpeed = snapshot.speedMS();

        currentSnapshot = snapshot.withGForce(gForce);
    }

    private void setEmpty() {
        currentSnapshot = HudSnapshot.EMPTY;
        oldSpeed = 0;
        firstFrame = true;
    }

}
