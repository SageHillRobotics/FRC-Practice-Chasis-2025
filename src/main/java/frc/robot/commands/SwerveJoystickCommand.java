package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveModule;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveJoystickCommand extends Command {
    public static final double MAX_SPEED = 4.0;
    public static final double DEADBAND = 0.1;

    private final SwerveSubsystem swerveSubsystem;

    private final Supplier<Double> xSupplier;
    private final Supplier<Double> ySupplier;
    private final Supplier<Double> steerSupplier;

    public SwerveJoystickCommand(SwerveSubsystem swerveSubsystem, Supplier<Double> xSupplier, Supplier<Double> ySupplier, Supplier<Double> steerSupplier) {
        this.swerveSubsystem = swerveSubsystem;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        this.steerSupplier = steerSupplier;
        addRequirements(swerveSubsystem);
    }

    @Override
    public void execute() {
        double xSpd = this.xSupplier.get();
        double ySpd = this.ySupplier.get();
        double steerSpd = this.steerSupplier.get();

        xSpd = Math.abs(xSpd) > DEADBAND ? xSpd : 0.0;
        ySpd = Math.abs(ySpd) > DEADBAND ? ySpd : 0.0;
        steerSpd = Math.abs(steerSpd) > DEADBAND ? steerSpd : 0.0;

        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xSpd * MAX_SPEED, ySpd * MAX_SPEED, steerSpd * MAX_SPEED);
        SwerveDriveKinematics kinematics = swerveSubsystem.getKinematics();

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_SPEED);
        swerveSubsystem.setModuleStates(states);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSubsystem.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
