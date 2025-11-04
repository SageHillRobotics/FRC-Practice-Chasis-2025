package frc.robot;

import java.lang.ModuleLayer.Controller;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.subsystems.SwerveSubsystem;

public class Robot extends TimedRobot {
  private SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  private XboxController controller = new XboxController(0);

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    // TODO: Invert if necessary
    double xSpd = -controller.getLeftY(); // Forward/backward
    double ySpd = -controller.getLeftX(); // Left/right
    double steerSpd = -controller.getRightX(); // Rotation

    new SwerveJoystickCommand(swerveSubsystem, xSpd, ySpd, steerSpd).execute();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
