package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class SwerveModule {
    public SparkMax driveMotor;
    public SparkMax angleMotor;

    public SwerveModule(int driveMotorId, int angleMotorId) {
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        angleMotor = new SparkMax(angleMotorId, MotorType.kBrushless);
    }
}
