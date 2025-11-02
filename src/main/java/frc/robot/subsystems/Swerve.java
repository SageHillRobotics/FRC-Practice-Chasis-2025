package frc.robot.subsystems;

public class Swerve {
    public SwerveModule FL;
    public SwerveModule FR;
    public SwerveModule BL;
    public SwerveModule BR;

    public Swerve(int[] ids) {
        FL = new SwerveModule(ids[0], ids[1]);
        FR = new SwerveModule(ids[2], ids[3]);
        BL = new SwerveModule(ids[4], ids[5]);
        BR = new SwerveModule(ids[6], ids[7]);
    }

    public void move(double x, double y, String motor) {
        double targetAngle = Math.toDegrees(Math.atan2(y, x));
        if (targetAngle < 0) {
            targetAngle += 360;
        }

        double driveSpeed = Math.sqrt(x * x + y * y);
        if (driveSpeed > 1.0) driveSpeed = 1.0;

        if (motor.equals("FL") || motor.equals("ALL")) FL.move(driveSpeed, targetAngle);
        if (motor.equals("FR") || motor.equals("ALL")) FR.move(driveSpeed, targetAngle);
        if (motor.equals("BL") || motor.equals("ALL")) BL.move(driveSpeed, targetAngle);
        if (motor.equals("BR") || motor.equals("ALL")) BR.move(driveSpeed, targetAngle);
    }
}
