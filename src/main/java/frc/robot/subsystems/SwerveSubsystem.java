package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
    public static final double CHASSIS_WIDTH = Units.inchesToMeters(28);
    public static final double CHASSIS_LENGTH = Units.inchesToMeters(28);

    private SwerveModule[] swerveModules = new SwerveModule[]{
        // TODO: invert motors if necessary
        new SwerveModule(1, 2, 9, false, false),
        new SwerveModule(3, 4, 10, false, false),
        new SwerveModule(5, 6, 11, false, false),
        new SwerveModule(7, 8, 12, false, false)
    };

    private Pigeon2 pigeon = new Pigeon2(13);

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        new Translation2d( CHASSIS_LENGTH / 2,  CHASSIS_WIDTH / 2),
        new Translation2d( CHASSIS_LENGTH / 2, -CHASSIS_WIDTH / 2),
        new Translation2d(-CHASSIS_LENGTH / 2,  CHASSIS_WIDTH / 2),
        new Translation2d(-CHASSIS_LENGTH / 2, -CHASSIS_WIDTH / 2)
    );

    public SwerveSubsystem() {
        pigeon.reset();
    }

    public SwerveDriveKinematics getKinematics() {
        return kinematics;
    }

    public double getHeading() {
        // TODO: negate if necessary
        return -MathUtil.angleModulus(Math.toRadians(pigeon.getYaw().getValueAsDouble()));
    }

    public void setModuleStates(SwerveModuleState[] states) {
        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveModule.MAX_SPEED);
        for (int i = 0; i < swerveModules.length; i++) {
            swerveModules[i].setDesiredState(states[i]);
        }
    }
}
