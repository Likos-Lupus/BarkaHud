package top.likoslupus.barkahud.core.telemetry;

public record Vec2d(
        double x,
        double y
) {

    public static final Vec2d ZERO = new Vec2d(0, 0);

    public Vec2d multiply(double factorX, double factorY) {
        return new Vec2d(x * factorX, y * factorY);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double dot(Vec2d other) {
        return x * other.x + y * other.y;
    }

}
