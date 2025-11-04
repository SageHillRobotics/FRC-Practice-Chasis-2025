package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
    public static final double CHASSIS_WIDTH = Units.inchesToMeters(40.0);
    public static final double CHASSIS_LENGTH = Units.inchesToMeters(40.0);

    private SwerveModule[] swerveModules = new SwerveModule[]{
        new SwerveModule(1, 1, 2),
        new SwerveModule(2, 3, 4),
        new SwerveModule(3, 5, 6),
        new SwerveModule(4, 7, 8)
    };

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        new Translation2d( CHASSIS_LENGTH / 2,  CHASSIS_WIDTH / 2),
        new Translation2d( CHASSIS_LENGTH / 2, -CHASSIS_WIDTH / 2),
        new Translation2d(-CHASSIS_LENGTH / 2,  CHASSIS_WIDTH / 2),
        new Translation2d(-CHASSIS_LENGTH / 2, -CHASSIS_WIDTH / 2)
    );    

    public SwerveDriveKinematics getKinematics() {
        return kinematics;
    }

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
