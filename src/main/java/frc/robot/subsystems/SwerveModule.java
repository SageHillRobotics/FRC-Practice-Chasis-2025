package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {
    public static final double WHEEL_DIAMETER_INCHES = 4;
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_INCHES / 39.37;
    public static final double DRIVE_GEAR_RATIO = 6.86;
    public static final double STEER_GEAR_RATIO = 150.0 / 7.0;

    private int id;

    private SparkMax driveMotor;
    private SparkMax steerMotor;

    private RelativeEncoder driveEncoder;
    private RelativeEncoder steerEncoder;

    private PIDController velocityPID;
    private PIDController anglePID;

    public SwerveModule(int id, int driveMotorId, int steerMotorId) {
        this.id = id;

        this.driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        this.steerMotor = new SparkMax(steerMotorId, MotorType.kBrushless);

        this.driveEncoder = driveMotor.getEncoder();
        this.steerEncoder = steerMotor.getEncoder();

        this.driveEncoder.setPosition(0);
        this.steerEncoder.setPosition(0);

        this.velocityPID = new PIDController(1, 0, 0);
        this.anglePID = new PIDController(1, 0, 0);

        this.anglePID.enableContinuousInput(-Math.PI, Math.PI);
    }

    public void setDesiredState(SwerveModuleState state) {
        double currentSpeed = getCurrentSpeedMetersPerSecond();
        double currentAngle = getCurrentAngleRadians();

        double targetSpeed = state.speedMetersPerSecond;
        double targetAngle = state.angle.getRadians();

        double velocityOutput = MathUtil.clamp(velocityPID.calculate(currentSpeed, targetSpeed), -1.0, 1.0);
        double angleOutput = MathUtil.clamp(anglePID.calculate(currentAngle, targetAngle), -1.0, 1.0);

        steerMotor.set(angleOutput);
        driveMotor.set(velocityOutput);
    }

    private double getCurrentSpeedMetersPerSecond() {
        return driveEncoder.getVelocity() / DRIVE_GEAR_RATIO / 60.0 * WHEEL_CIRCUMFERENCE;
    }

    private double getDriveDistanceMeters() {
        return driveEncoder.getPosition() / DRIVE_GEAR_RATIO * WHEEL_CIRCUMFERENCE;
    }

    private double getCurrentAngleRadians() {
        double radians = steerEncoder.getPosition() / STEER_GEAR_RATIO * 2.0 * Math.PI;
        return Math.atan2(Math.sin(radians), Math.cos(radians));
    }
}
