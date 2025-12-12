package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
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
    private final Supplier<Boolean> fieldRelativeSupplier;

    public SwerveJoystickCommand(SwerveSubsystem swerveSubsystem, Supplier<Double> xSupplier, Supplier<Double> ySupplier, Supplier<Double> steerSupplier, Supplier<Boolean> fieldRelativeSupplier) {
        this.swerveSubsystem = swerveSubsystem;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        this.steerSupplier = steerSupplier;
        this.fieldRelativeSupplier = fieldRelativeSupplier;
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

        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xSpd * SwerveModule.MAX_SPEED, ySpd * SwerveModule.MAX_SPEED, steerSpd * SwerveModule.MAX_ANGULAR_SPEED);
        if (!fieldRelativeSupplier.get()) {
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(chassisSpeeds, Rotation2d.fromRadians(swerveSubsystem.getHeading()));
        }
        
        SwerveDriveKinematics kinematics = swerveSubsystem.getKinematics();

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
        swerveSubsystem.setModuleStates(states);
    }
}
