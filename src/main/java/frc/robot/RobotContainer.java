// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.EndEffector;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.InstantCommand;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final CommandXboxController mechStick = new CommandXboxController(1);
  private EndEffector m_endEffector = EndEffector.getInstance();
  private Arm m_arm = Arm.getInstance();

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
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
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    //alpha intake forward (press a)
    mechStick.a()
      .onTrue(new FunctionalCommand(
        () -> m_arm.setIntakeForward(), //init
        () -> m_endEffector.startIntake(), //execute
        (interrupted) -> m_arm.stow(), //end
        () -> m_endEffector.hasBall(), //isFinished
        m_arm, m_endEffector)); //requirements
    
    //alpha intake rear (press a and dpad_left)
    mechStick.a().and(mechStick.povLeft())
      .onTrue(new FunctionalCommand(
        () -> m_arm.setIntakeRear(),
        () -> m_endEffector.startIntake(),
        (interrupted) -> m_arm.stow(), // (interrupted) -> {m_arm.stow(); m_effector.brake();},
        () -> m_endEffector.hasBall(), 
        m_arm, m_endEffector));


    //output angle low forward (press x)
    mechStick.x()
      .onTrue(new InstantCommand(
        () -> m_arm.setOutputForwardL(),
        m_arm));

    //output angle low rear (press x and dpad_left)
    mechStick.x().and(mechStick.povLeft())
      .onTrue(new InstantCommand(
        () -> m_arm.setOutputRearL(),
        m_arm));

    //output angle high forward (press b)
    mechStick.b()
      .onTrue(new InstantCommand(
        () -> m_arm.setOutputForwardH(),
        m_arm));

    //output angle high rear (press b and dpad_left)
    mechStick.x()
      .onTrue(new InstantCommand(
        () -> m_arm.setOutputRearH(),
        m_arm));

    
    // outtake/shoot (press right bumper)
    mechStick.rightBumper()
      .onTrue(new StartEndCommand(
        () -> m_endEffector.startOutput(),
        () -> m_endEffector.brake(),
        m_arm, m_endEffector
      ).withTimeout(Constants.EndEffector.OUTTAKE_SECONDS)
      );
      
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
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
