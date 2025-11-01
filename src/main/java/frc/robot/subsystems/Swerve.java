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

    public void updateValues() {
        FL.updateValues();
        FR.updateValues();
        BL.updateValues();
        BR.updateValues();
    }

    public void resetEncoders() {
        FL.driveEncoder.setPosition(0);
        FL.angleEncoder.setPosition(0);
        FR.driveEncoder.setPosition(0);
        FR.angleEncoder.setPosition(0);
        BL.driveEncoder.setPosition(0);
        BL.angleEncoder.setPosition(0);
        BR.driveEncoder.setPosition(0);
        BR.angleEncoder.setPosition(0);
    }

    public void move(double x, double y, String motor) {
        int targetAngle = (int) Math.round(x * 360) % 360;
        if (targetAngle < 0) {
            targetAngle += 360;
        }

        if (motor.equals("FL") || motor.equals("ALL")) FL.move(FL.distanceMeters + y, targetAngle);
        if (motor.equals("FR") || motor.equals("ALL")) FR.move(FR.distanceMeters + y, targetAngle);
        if (motor.equals("BL") || motor.equals("ALL")) BL.move(BL.distanceMeters + y, targetAngle);
        if (motor.equals("BR") || motor.equals("ALL")) BR.move(BR.distanceMeters + y, targetAngle);

        updateValues();
    }

    public void logEncoders() {
        System.out.println("FL Distance: " + FL.distanceMeters + "m, Angle: " + FL.angleDegrees + "°");
        System.out.println("FR Distance: " + FR.distanceMeters + "m, Angle: " + FR.angleDegrees + "°");
        System.out.println("BL Distance: " + BL.distanceMeters + "m, Angle: " + BL.angleDegrees + "°");
        System.out.println("BR Distance: " + BR.distanceMeters + "m, Angle: " + BR.angleDegrees + "°");
    }
}
