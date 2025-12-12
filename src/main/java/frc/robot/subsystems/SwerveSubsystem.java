package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Rotation;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
    public static final double CHASSIS_WIDTH = Units.inchesToMeters(28);
    public static final double CHASSIS_LENGTH = Units.inchesToMeters(28);

    private SwerveModule[] swerveModules = new SwerveModule[]{
        // TODO: invert motors if necessary
        new SwerveModule(1, 2, false, false),
        new SwerveModule(3, 4, false, false),
        new SwerveModule(5, 6, false, false),
        new SwerveModule(7, 8, false, false)
    };

    private Pigeon2 pigeon = new Pigeon2(13);

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        new Translation2d(CHASSIS_LENGTH / 2, -CHASSIS_WIDTH / 2),
        new Translation2d(CHASSIS_LENGTH / 2, CHASSIS_WIDTH / 2),
        new Translation2d(-CHASSIS_LENGTH / 2, -CHASSIS_WIDTH / 2),
        new Translation2d(-CHASSIS_LENGTH / 2, CHASSIS_WIDTH / 2)
    );

    private SwerveDriveOdometry odometry = new SwerveDriveOdometry(kinematics, new Rotation2d(), new SwerveModulePosition[]{
        new SwerveModulePosition(swerveModules[0].getDrivePosition(), Rotation2d.fromRadians(swerveModules[0].getSteerPosition())),
        new SwerveModulePosition(swerveModules[1].getDrivePosition(), Rotation2d.fromRadians(swerveModules[1].getSteerPosition())),
        new SwerveModulePosition(swerveModules[2].getDrivePosition(), Rotation2d.fromRadians(swerveModules[2].getSteerPosition())),
        new SwerveModulePosition(swerveModules[3].getDrivePosition(), Rotation2d.fromRadians(swerveModules[3].getSteerPosition()))
    });

    public SwerveSubsystem() {
        pigeon.reset();
    }

    @Override
    public void periodic() {
        odometry.update(Rotation2d.fromRadians(getHeading()), new SwerveModulePosition[]{
            new SwerveModulePosition(swerveModules[0].getDrivePosition(), Rotation2d.fromRadians(swerveModules[0].getSteerPosition())),
            new SwerveModulePosition(swerveModules[1].getDrivePosition(), Rotation2d.fromRadians(swerveModules[1].getSteerPosition())),
            new SwerveModulePosition(swerveModules[2].getDrivePosition(), Rotation2d.fromRadians(swerveModules[2].getSteerPosition())),
            new SwerveModulePosition(swerveModules[3].getDrivePosition(), Rotation2d.fromRadians(swerveModules[3].getSteerPosition()))
        });
        SmartDashboard.putNumber("Robot Heading", Math.toDegrees(getHeading()));
        SmartDashboard.putString("Robot Position", getPose().getTranslation().toString());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(Rotation2d.fromRadians(getHeading()), new SwerveModulePosition[]{
            new SwerveModulePosition(swerveModules[0].getDrivePosition(), Rotation2d.fromRadians(swerveModules[0].getSteerPosition())),
            new SwerveModulePosition(swerveModules[1].getDrivePosition(), Rotation2d.fromRadians(swerveModules[1].getSteerPosition())),
            new SwerveModulePosition(swerveModules[2].getDrivePosition(), Rotation2d.fromRadians(swerveModules[2].getSteerPosition())),
            new SwerveModulePosition(swerveModules[3].getDrivePosition(), Rotation2d.fromRadians(swerveModules[3].getSteerPosition()))
        }, pose);
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
