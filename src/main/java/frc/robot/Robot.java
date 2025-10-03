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

  public void testMotors() {
    m_swerve.FL.driveMotor.set(0.5);
    Timer.delay(5);
    m_swerve.FL.driveMotor.set(0);
    m_swerve.FR.driveMotor.set(0.5);
    Timer.delay(5);
    m_swerve.FR.driveMotor.set(0);
    m_swerve.BL.driveMotor.set(0.5);
    Timer.delay(5);
    m_swerve.BL.driveMotor.set(0);
    m_swerve.BR.driveMotor.set(0.5);
    Timer.delay(5);
    m_swerve.BR.driveMotor.set(0);
    m_swerve.FL.angleMotor.set(0.5);
    Timer.delay(5);
    m_swerve.FL.angleMotor.set(0);
    m_swerve.FR.angleMotor.set(0.5);
    Timer.delay(5);
    m_swerve.FR.angleMotor.set(0);
    m_swerve.BL.angleMotor.set(0.5);
    Timer.delay(5);
    m_swerve.BL.angleMotor.set(0);
    m_swerve.BR.angleMotor.set(0.5);
    Timer.delay(5);
    m_swerve.BR.angleMotor.set(0);
  }

  public void motor

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
    testMotors();
  }

  @Override
  public void autonomousPeriodic() {
    m_swerve.logEncoders();
    boolean inProgress = true;
    inProgress = !m_swerve.stepDistance(2);
    if (inProgress) return;
    inProgress = !m_swerve.stepAngleDegrees(90);
    if (inProgress) return;
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    m_swerve.move(m_controller.getLeftX(), m_controller.getRightY());
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
