package top.likoslupus.barkahud.config;

public enum SpeedBarProfile {

    PACKED(0, 40, 4.55),
    MIXED(8, 70, 182.0 / 62.0),
    BLUE(40, 70, 182.0 / 30.0),
    PROGRESSIVE(0, 70, 0) {
        @Override
        public int getProgress(double displayedSpeed, int barWidth) {
            double ratio = (double) barWidth / 182.0;
            if (displayedSpeed < this.minV) return 0;
            if (displayedSpeed > this.maxV) return -1;
            if (displayedSpeed < 8) return (int) (displayedSpeed * 22.75 * ratio);
            if (displayedSpeed < 40) return (int) ((displayedSpeed - 8) * 5.6875 * ratio);
            return (int) ((displayedSpeed - 40) * 6.0666 * ratio);
        }
    };

    public final double minV;
    public final double maxV;
    public final double scaleV;

    SpeedBarProfile(
            double minV,
            double maxV,
            double scaleV
    ) {
        this.minV = minV;
        this.maxV = maxV;
        this.scaleV = scaleV;
    }

    public int getProgress(double displayedSpeed, int barWidth) {
        double ratio = (double) barWidth / 182.0;
        if (displayedSpeed < this.minV) return 0;
        if (displayedSpeed > this.maxV) return -1;
        return (int) ((displayedSpeed - this.minV) * this.scaleV * ratio);
    }

}
