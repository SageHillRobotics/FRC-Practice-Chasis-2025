package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.SensorDirectionValue;
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
    
    private int id;

    private SparkMax driveMotor;
    private SparkMax steerMotor;

    private RelativeEncoder driveEncoder;
    private RelativeEncoder steerEncoder;
    
    private CANcoder absoluteEncoder;

    private PIDController velocityPID;
    private PIDController anglePID;

    public SwerveModule(int id, int driveMotorId, int steerMotorId, int absoluteEncoderId, int absoluteEncoderOffset) {
        this.id = id;

        this.driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        this.steerMotor = new SparkMax(steerMotorId, MotorType.kBrushless);

        this.driveEncoder = driveMotor.getEncoder();
        this.steerEncoder = steerMotor.getEncoder();

        this.absoluteEncoder = new CANcoder(absoluteEncoderId);
        CANcoderConfigurator configurator = this.absoluteEncoder.getConfigurator();
        configurator.apply(new CANcoderConfiguration());
        configurator.apply(new MagnetSensorConfigs().withSensorDirection(SensorDirectionValue.CounterClockwise_Positive).withAbsoluteSensorDiscontinuityPoint(0.5).withMagnetOffset(absoluteEncoderOffset));

        this.velocityPID = new PIDController(0.1, 0, 0);
        this.anglePID = new PIDController(0.1, 0, 0);
        this.anglePID.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getSteerPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        state = SwerveModuleState.optimize(state, new Rotation2d(getSteerPosition()));

        driveMotor.set(velocityPID.calculate(getDriveVelocity(), state.speedMetersPerSecond));
        steerMotor.set(anglePID.calculate(getSteerPosition(), state.angle.getRadians()));
    }

    public double getDriveRot2Meters(double rotations) {
        return (Math.PI * WHEEL_DIAMETER * rotations) / DRIVE_GEAR_RATIO;
    }

    public double getSteerRot2Rad(double rotations) {
        return (2.0 * Math.PI * rotations) / STEER_GEAR_RATIO;
    }

    public double getDriveRPM2MetersPerSec(double rpm) {
        return getDriveRot2Meters(rpm) / 60.0;
    }

    public double getSteerRPM2RadPerSec(double rpm) {
        return getSteerRot2Rad(rpm) / 60.0;
    }

    public double getDrivePosition() {
        return getDriveRot2Meters(driveEncoder.getPosition());
    }

    public double getSteerPosition() {
        return getSteerRot2Rad(steerEncoder.getPosition());
    }

    public double getDriveVelocity() {
        return getDriveRPM2MetersPerSec(driveEncoder.getVelocity());
    }

    public double getSteerVelocity() {
        return getSteerRPM2RadPerSec(steerEncoder.getVelocity());
    }

    public void resetEncoders() {
        driveEncoder.setPosition(0);
        steerEncoder.setPosition(absoluteEncoder.getAbsolutePosition().refresh().getValueAsDouble() * STEER_GEAR_RATIO);
    }

    public void stop() {
        driveMotor.set(0);
        steerMotor.set(0);
    }
}