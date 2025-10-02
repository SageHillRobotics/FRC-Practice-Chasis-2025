package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Swerve;

public class Robot extends TimedRobot {
  private Swerve m_swerve;

  private final RobotContainer m_robotContainer;

  public Robot() {
    m_swerve = new Swerve(1, 2, 3, 4, 5, 6, 7, 8);

    m_robotContainer = new RobotContainer();
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
    testMotors();
  }

  @Override
  public void teleopPeriodic() {
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
