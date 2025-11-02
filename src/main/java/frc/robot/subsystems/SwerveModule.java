package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;

public class SwerveModule {
    public static final double DRIVE_GEAR_RATIO = 8.14; // L1 Gear Ratio, TODO: Update DRIVE_GEAR_RATIO to Correct Value
    public static final double WHEEL_DIAMETER_INCHES = 4;
    public static final double ANGLE_GEAR_RATIO = 150.0 / 7.0;

    public SparkMax driveMotor;
    public SparkMax angleMotor;

    public RelativeEncoder driveEncoder;
    public RelativeEncoder angleEncoder;

    public PIDController drivePID;
    public PIDController anglePID;

    public double distanceMeters;
    public int angleDegrees;

    public SwerveModule(int driveMotorId, int angleMotorId) {
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        angleMotor = new SparkMax(angleMotorId, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        angleEncoder = angleMotor.getEncoder();

        driveEncoder.setPosition(0);
        angleEncoder.setPosition(0);

        drivePID = new PIDController(0.1, 0, 0);
        anglePID = new PIDController(0.1, 0, 0);
    }

   public void move(double driveSpeed, double targetAngle) {
    updateValues();

    double angleError = (targetAngle - angleDegrees + 540) % 360 - 180;
    double angleOutput = anglePID.calculate(angleError, 0);
    angleOutput = Math.max(-1, Math.min(1, angleOutput));
    driveMotor.set(Math.max(-1, Math.min(1, driveSpeed)));
    angleMotor.set(angleOutput);

    updateValues();
}

    public double driveEncoderToMeters(double encoderPosition) {
        return encoderPosition * (Math.PI * WHEEL_DIAMETER_INCHES) / DRIVE_GEAR_RATIO / 39.37;
    }

    public int angleEncoderToDegrees(double encoderPosition) {
        int angleDegrees = (int) Math.round(encoderPosition / ANGLE_GEAR_RATIO * 360) % 360;
        if (angleDegrees < 0) angleDegrees += 360;
        return angleDegrees;
    }

    public void updateValues() {
        distanceMeters = driveEncoderToMeters(driveEncoder.getPosition());
        angleDegrees = angleEncoderToDegrees(angleEncoder.getPosition());
    }
}
