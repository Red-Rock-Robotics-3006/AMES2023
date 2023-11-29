// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
// import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.*;
// import frc.robot.subsystems.Zipline;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private boolean driverModifier;
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // The robot's subsystems and commands are defined here...
  private final Elevator m_elevator = new Elevator();
  private final Zipline m_zipline = new Zipline(m_elevator);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(0);
  private final CommandXboxController m_mechanismsController = new CommandXboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_elevator.setDefaultCommand(
      new RunCommand(
        () -> m_elevator.setSpeed(
          m_mechanismsController.getRightY()//  * (driverModifier?1:-1)
        )
      )
    );


    m_zipline.setDefaultCommand(
      new RunCommand(
        () -> m_zipline.setSpeed(
          m_driverController.getLeftY() * (driverModifier?1:0)
        )
      )
    );



    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_mechanismsController.getRightY();
    // m_mechanismsController.leftTrigger()
    //   .whileTrue(new InstantCommand(() -> m_elevator.setSpeed(1)))
    //   .onFalse(new InstantCommand(() -> m_elevator.setSpeed(0)));
    //   m_mechanismsController.rightTrigger()
    //     .whileTrue(new InstantCommand(() -> m_elevator.setSpeed(-1)))
    //     .onFalse(new InstantCommand(() -> m_elevator.setSpeed(0)));



    // m_mechanismsController.a().and(m_driverController.povLeft())
    //     .whileTrue(new InstantCommand(() -> m_elevator.setSpeed(1)))
    //     .onFalse(new InstantCommand(() -> m_elevator.setSpeed(0)));


    m_mechanismsController.a()
        .whileTrue(new InstantCommand(() -> m_elevator.setSpeed(1)))
        .onFalse(new InstantCommand(() -> m_elevator.setSpeed(0)));
    m_mechanismsController.b()
        .whileTrue(new InstantCommand(() -> m_elevator.setSpeed(-1)))
        .onFalse(new InstantCommand(() -> m_elevator.setSpeed(0)));

    m_driverController.povLeft()
        .onTrue(new InstantCommand(() -> driverModifier = true))
        .onFalse(new InstantCommand(() -> driverModifier = false));

    // m_driverController.axisGreaterThan(0, 0, null);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
