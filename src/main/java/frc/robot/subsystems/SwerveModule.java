package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class SwerveModule {
    public SparkMax driveMotor;
    public SparkMax angleMotor;

    public RelativeEncoder driveEncoder;
    public RelativeEncoder angleEncoder;

    public double distance;
    public int angle;

    public SwerveModule(int driveMotorId, int angleMotorId) {
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        angleMotor = new SparkMax(angleMotorId, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        angleEncoder = angleMotor.getEncoder();

        driveEncoder.setPosition(0);
        angleEncoder.setPosition(0);
    }

    public void updateValues() {
        distance = driveEncoder.getPosition();
        angle = (int) (angleEncoder.getPosition() * 360) % 360;
        if (angle < 0) {
            angle += 360;
        }
    }
}
