// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

public class RobotContainer {
  // Subsystems
  private final DriveSubsystem m_drive = new DriveSubsystem();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Controllers
  private final XboxController m_controller = new XboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Default command for driving: left stick Y = forward/back, right stick X = turn
    m_drive.setDefaultCommand(
        new RunCommand(
            () -> m_drive.arcade(-m_controller.getLeftY(), m_controller.getRightX()),
            m_drive));

    // Configure any button/trigger bindings
    configureBindings();
  }

  /** Define trigger->command mappings. */
  private void configureBindings() {
    // Keep one example from the template so it compiles
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Removed the m_driverController.b().whileTrue(...) line from the template
    // because we're not using CommandXboxController in this simplified setup.
  }

  /** Autonomous command */
  public Command getAutonomousCommand() {
    // Keep the template auto so Autos.java compiles unchanged
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
