package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveJoystickCommand extends Command {
    public static final double DEADBAND = 0.05;

    private final SwerveSubsystem swerveSubsystem;
    private final double m_xSpd;
    private final double m_ySpd;
    private final double m_steerSpd;

    public SwerveJoystickCommand(SwerveSubsystem swerveSubsystem, double xSpd, double ySpd, double steerSpd) {
        this.swerveSubsystem = swerveSubsystem;
        this.m_xSpd = xSpd;
        this.m_ySpd = ySpd;
        this.m_steerSpd = steerSpd;
        addRequirements(swerveSubsystem);
    }

    @Override
    public void execute() {
        double xSpd = Math.abs(this.m_xSpd) > DEADBAND ? this.m_xSpd : 0.0;
        double ySpd = Math.abs(this.m_ySpd) > DEADBAND ? this.m_ySpd : 0.0;
        double steerSpd = Math.abs(this.m_steerSpd) > DEADBAND ? this.m_steerSpd : 0.0;

        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xSpd, ySpd, steerSpd);
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(Units.inchesToMeters(20), -Units.inchesToMeters(20)),
            new Translation2d(Units.inchesToMeters(20), Units.inchesToMeters(20)),
            new Translation2d(-Units.inchesToMeters(20), -Units.inchesToMeters(20)),
            new Translation2d(-Units.inchesToMeters(20), Units.inchesToMeters(20))
        );

        swerveSubsystem.setModuleStates(kinematics.toSwerveModuleStates(chassisSpeeds));
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
