package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
    SwerveModule[] swerveModules = new SwerveModule[]{
        new SwerveModule(1, 1, 2),
        new SwerveModule(2, 3, 4),
        new SwerveModule(3, 5, 6),
        new SwerveModule(4, 7, 8)
    };

    public void setModuleStates(SwerveModuleState[] states) {
        for (int i = 0; i < swerveModules.length; i++) {
            swerveModules[i].setDesiredState(states[i]);
        }
    }

    public void stopModules() {
        for (SwerveModule module : swerveModules) {
            module.stop();
        }
    }
}
