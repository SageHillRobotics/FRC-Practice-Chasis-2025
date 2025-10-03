package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class SwerveModule {
    public SparkMax driveMotor;
    public SparkMax angleMotor;

    public RelativeEncoder driveEncoder;
    public RelativeEncoder angleEncoder;

    public CANcoder canCoder;

    public double distance;
    public int angle;

    public SwerveModule(int driveMotorId, int angleMotorId, int canCoderId) {
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        angleMotor = new SparkMax(angleMotorId, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        angleEncoder = angleMotor.getEncoder();
        driveEncoder.setPosition(0);
        angleEncoder.setPosition(0);

        canCoder = new CANcoder(canCoderId);
    }

    public void updateValues() {
        distance = driveEncoder.getPosition();
        angle = (int) (angleEncoder.getPosition() * 360) % 360;
        if (angle < 0) {
            angle += 360;
        }
    }

    public void setAngleDegrees(int goal) {
        while (angle != goal) {
            angleMotor.set(0.1);
            updateValues();
        }
        angleMotor.set(0);
    }
}
