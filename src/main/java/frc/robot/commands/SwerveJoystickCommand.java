package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveModule;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveJoystickCommand extends Command {
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

        xSpd = MathUtil.applyDeadband(xSpd, DEADBAND);
        ySpd = MathUtil.applyDeadband(ySpd, DEADBAND);
        steerSpd = MathUtil.applyDeadband(steerSpd, DEADBAND);

        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xSpd * SwerveModule.MAX_SPEED, ySpd * SwerveModule.MAX_SPEED, steerSpd * SwerveModule.MAX_SPEED);
        SwerveDriveKinematics kinematics = swerveSubsystem.getKinematics();

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveModule.MAX_SPEED);
        swerveSubsystem.setModuleStates(states);
    }
}
