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

    public void updateValues() {
        FL.updateValues();
        FR.updateValues();
        BL.updateValues();
        BR.updateValues();
    }

    public void move(double xSpeed, double ySpeed, String motor) {
        xSpeed /= 2;
        ySpeed /= 2;

        if (motor == "FL" || motor == "ALL") FL.driveMotor.set(ySpeed);
        if (motor == "FR" || motor == "ALL") FR.driveMotor.set(ySpeed);
        if (motor == "BL" || motor == "ALL") BL.driveMotor.set(ySpeed);
        if (motor == "BR" || motor == "ALL") BR.driveMotor.set(ySpeed);

        if (motor == "FL" || motor == "ALL") FL.angleMotor.set(xSpeed);
        if (motor == "FR" || motor == "ALL") FR.angleMotor.set(xSpeed);
        if (motor == "BL" || motor == "ALL") BL.angleMotor.set(xSpeed);
        if (motor == "BR" || motor == "ALL") BR.angleMotor.set(xSpeed);

        updateValues();
    }

    public void logEncoders() {
        System.out.println("FL Distance: " + FL.distance + " Angle: " + FL.angle);
        System.out.println("FR Distance: " + FR.distance + " Angle: " + FR.angle);
        System.out.println("BL Distance: " + BL.distance + " Angle: " + BL.angle);
        System.out.println("BR Distance: " + BR.distance + " Angle: " + BR.angle);
    }
}
