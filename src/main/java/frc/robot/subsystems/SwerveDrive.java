package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveDrive {
    SwerveModule[] swerveModules = new SwerveModule[]{
        new SwerveModule(1, 1, 2), // Front left
        new SwerveModule(2, 3, 4), // Front right
        new SwerveModule(3, 5, 6), // Back left
        new SwerveModule(4, 7, 8)  // Back right
    };

    public void drive(double x1, double y1, double x2) {
        drive(new ChassisSpeeds(x1, y1, x2));
    }

    public void drive(ChassisSpeeds chassisSpeeds) {
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(0.5, 0.5), // Front left
            new Translation2d(0.5, -0.5), // Front right
            new Translation2d(-0.5, 0.5), // Back left
            new Translation2d(-0.5, -0.5) // Back right
        );
        SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(chassisSpeeds);
        for (int i = 0; i < 4; i++) {
            swerveModules[i].setDesiredState(moduleStates[i]);
        }
    }
}
