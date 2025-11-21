package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;

public class SwerveModule {
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double DRIVE_GEAR_RATIO = 6.86;
    public static final double STEER_GEAR_RATIO = 150.0 / 7.0;
    public static final double MAX_SPEED = 4.0;
    public static final double MAX_ANGULAR_SPEED = Math.PI;;

    private SparkMax driveMotor;
    private SparkMax steerMotor;

    private RelativeEncoder driveEncoder;
    private RelativeEncoder steerEncoder;

    private PIDController velocityPID;
    private PIDController anglePID;

    public SwerveModule(int driveMotorId, int steerMotorId, boolean driveInverted, boolean steerInverted) {
        this.driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        this.steerMotor = new SparkMax(steerMotorId, MotorType.kBrushless);

        this.driveEncoder = driveMotor.getEncoder();
        this.steerEncoder = steerMotor.getEncoder();

        driveMotor.setInverted(driveInverted);
        steerMotor.setInverted(steerInverted);

        this.velocityPID = new PIDController(0.1, 0, 0);
        this.anglePID = new PIDController(0.1, 0, 0);
        this.anglePID.enableContinuousInput(-Math.PI, Math.PI);

        driveEncoder.setPosition(0);
        steerEncoder.setPosition(0);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getSteerPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        state.optimize(new Rotation2d(getSteerPosition()));

        driveMotor.set(velocityPID.calculate(getDriveVelocity(), state.speedMetersPerSecond));
        steerMotor.set(anglePID.calculate(getSteerPosition(), state.angle.getRadians()));
    }

    public double getDriveRot2Meters(double rotations) {
        return Math.PI * WHEEL_DIAMETER * rotations / DRIVE_GEAR_RATIO;
    }

    public double getDriveRPM2MetersPerSec(double rpm) {
        return getDriveRot2Meters(rpm) / 60.0;
    }

    public double getDrivePosition() {
        return getDriveRot2Meters(driveEncoder.getPosition());
    }

    public double getDriveVelocity() {
        return getDriveRPM2MetersPerSec(driveEncoder.getVelocity());
    }

    public double getSteerPosition() {
        return steerEncoder.getPosition() / STEER_GEAR_RATIO * 2.0 * Math.PI;
    }
}