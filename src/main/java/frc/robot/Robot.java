package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Swerve;

public class Robot extends TimedRobot {
  private Swerve m_swerve;

  private XboxController m_controller;

  public Robot() {
    m_swerve = new Swerve(1, 2, 3, 4, 5, 6, 7, 8);

    m_controller = new XboxController(0);
  }

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
    String motor = "ALL";
    if (m_controller.getLeftBumperButton()) motor = "FL";
    if (m_controller.getRightBumperButton()) motor = "FR";
    if (m_controller.getLeftTriggerAxis() > 0.8) motor = "BL";
    if (m_controller.getRightTriggerAxis() > 0.8) motor = "BR";
    m_swerve.move(m_controller.getLeftX(), m_controller.getRightY(), motor);
    m_swerve.logEncoders();
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
