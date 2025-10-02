package frc.robot.subsystems;

public class Swerve {
    public SwerveModule FL;
    public SwerveModule FR;
    public SwerveModule BL;
    public SwerveModule BR;

    public Swerve(int... ids) {
        FL = new SwerveModule(ids[0], ids[1]);
        FR = new SwerveModule(ids[2], ids[3]);
        BL = new SwerveModule(ids[4], ids[5]);
        BR = new SwerveModule(ids[6], ids[7]);
    }

    public void move(double xSpeed, double ySpeed) {
        FL.driveMotor.set(ySpeed);
        FR.driveMotor.set(ySpeed);
        BL.driveMotor.set(ySpeed);
        BR.driveMotor.set(ySpeed);

        FL.angleMotor.set(xSpeed);
        FR.angleMotor.set(xSpeed);
        BL.angleMotor.set(xSpeed);
        BR.angleMotor.set(xSpeed);
    }
}
