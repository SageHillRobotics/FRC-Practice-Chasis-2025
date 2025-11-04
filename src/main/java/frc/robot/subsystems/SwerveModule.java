package frc.robot.subsystems;

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
    public static final double MAX_DRIVE_SPEED = 5676.0 / DRIVE_GEAR_RATIO * Math.PI * WHEEL_DIAMETER / 60.0;
    public static final double MAX_STEER_SPEED = MAX_DRIVE_SPEED / Math.hypot(SwerveSubsystem.CHASSIS_LENGTH / 2.0, SwerveSubsystem.CHASSIS_WIDTH / 2.0); // TODO: ChatGPT math may need update
    
    private int id;

    private SparkMax driveMotor;
    private SparkMax steerMotor;

    private RelativeEncoder driveEncoder;
    private RelativeEncoder steerEncoder;

    private PIDController velocityPID;
    private PIDController anglePID;

    public SwerveModule(int id, int driveMotorId, int steerMotorId, boolean driveReversed, boolean steerReversed) {
        this.id = id;

        this.driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        this.steerMotor = new SparkMax(steerMotorId, MotorType.kBrushless);

        this.driveMotor.setInverted(driveReversed);
        this.steerMotor.setInverted(steerReversed);

        this.driveEncoder = driveMotor.getEncoder();
        this.steerEncoder = steerMotor.getEncoder();

        resetEncoders();

        this.velocityPID = new PIDController(0.1, 0, 0);
        this.anglePID = new PIDController(0.1, 0, 0);

        this.anglePID.enableContinuousInput(-Math.PI, Math.PI);
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
        steerEncoder.setPosition(0);
    }

    public void stop() {
        driveMotor.set(0);
        steerMotor.set(0);
    }
}