package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SwerveDrive;

public class Robot extends TimedRobot {
  private SwerveDrive m_swerveDrive = new SwerveDrive();
  private XboxController m_controller = new XboxController(0);
  private Timer m_timer = new Timer();

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
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void teleopPeriodic() {
    double x1 = -m_controller.getLeftY(); // Forward/backward
    double y1 = -m_controller.getLeftX(); // Left/right
    double x2 = -m_controller.getRightX(); // Rotation

    m_swerveDrive.drive(x1, y1, x2);
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
