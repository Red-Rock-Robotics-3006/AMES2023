// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.SetIntakeWaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.EndEffector;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.subsystems.swerve.Gyroscope;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final CommandXboxController mechStick = new CommandXboxController(1);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);

  // private EndEffector m_endEffector = EndEffector.getInstance();
  // private Arm m_arm = Arm.getInstance();
  private Drivetrain m_swerve = Drivetrain.getInstance();

  private Command driveCommand = null;


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

    m_driverController.y().onTrue(new InstantCommand(() -> Gyroscope.getPigeonInstance().setYaw(0)));

    m_driverController.rightBumper()
    .onTrue(new InstantCommand(() -> {
      RunCommand dc = new RunCommand(
        () -> m_swerve.drive(
          m_driverController.getRawAxis(Constants.OperatorConstants.STRAFE_AXIS)*4, 
          m_driverController.getRawAxis(Constants.OperatorConstants.FORWARD_AXIS)*4, 
          Math.pow(m_driverController.getRawAxis(Constants.OperatorConstants.TURN_AXIS),3)*2,
          false
        ),
        m_swerve
      );
      dc.setName("Joystick Control");
      System.out.println("LeftY: " + m_driverController.getRawAxis(Constants.OperatorConstants.FORWARD_AXIS) + "\nLeftX: " + m_driverController.getRawAxis(Constants.OperatorConstants.STRAFE_AXIS) + "\nRightX: " + m_driverController.getRawAxis(Constants.OperatorConstants.TURN_AXIS));
      this.driveCommand = dc;
      m_swerve.getDefaultCommand().cancel();
      m_swerve.setDefaultCommand(dc);
    }))
    .onFalse(new InstantCommand(() -> {
      RunCommand dc = new RunCommand(
        () -> m_swerve.drive(
          m_driverController.getRawAxis(Constants.OperatorConstants.STRAFE_AXIS)*4, 
          m_driverController.getRawAxis(Constants.OperatorConstants.FORWARD_AXIS)*4, 
          Math.pow(m_driverController.getRawAxis(Constants.OperatorConstants.TURN_AXIS),3)*100,
          true
        ),
        m_swerve
      );
      dc.setName("Joystick Control");

      this.driveCommand = dc;
      m_swerve.getDefaultCommand().cancel();
      m_swerve.setDefaultCommand(dc);
    }));

    //------------------------------------------------------------------------------------------------------------------

    /**
     * Note about intake commands
     * there are two different implementations of intake commands:
     * 1) - for convenience nicknamed "alpha" - determine the ball spike current and then the accel spike current
     *      thus if current > ball spke && current < accel spike, stop the intake (because it a spike but not above motor starting current)
     *      this implementation is seen in the first two(2) bindings, mainly mechStick.a as well as mechStick.a && mechStick.povLeft
     * 
     * 2) - for convenience nicknamed "beta" - wait for a little bit (lets say 0.5 seconds) before monitoring current spike. This way:
     *      motor has time to spin up without triggering an exit condition
     *      this implementation is seen in the last two(2) bindings, mainly mechStick.leftBumper as well as mechStick.leftBumper && mechStick.povLeft
     * 
     * This way we can test multiple implementations and see whihc one is best
     */


    //alpha intake forward (press a)
    // mechStick.a()
    //   .onTrue(new FunctionalCommand(
    //     () -> m_arm.setIntakeForward(), //init
    //     () -> m_endEffector.startIntake(), //execute
    //     (interrupted) -> m_arm.stow(), //end
    //     () -> m_endEffector.hasBall(), //isFinished
    //     m_arm, m_endEffector)); //requirements
    
    // //alpha intake rear (press a and dpad_left)
    // mechStick.a().and(mechStick.povLeft())
    //   .onTrue(new FunctionalCommand(
    //     () -> m_arm.setIntakeRear(),
    //     () -> m_endEffector.startIntake(),
    //     (interrupted) -> m_arm.stow(), // (interrupted) -> {m_arm.stow(); m_effector.brake();},
    //     () -> m_endEffector.hasBall(), 
    //     m_arm, m_endEffector));


    // //output angle low forward (press x)
    // mechStick.x()
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.setOutputForwardL(),
    //     m_arm));

    // //output angle low rear (press x and dpad_left)
    // mechStick.x().and(mechStick.povLeft())
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.setOutputRearL(),
    //     m_arm));

    // mechStick.x().and(mechStick.povRight())
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.setOutputForwardH(),
    //     m_arm));    

    // //output angle high forward (press b)
    // mechStick.b()
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.setOutputForwardH(),
    //     m_arm));

    // //output angle high rear (press b and dpad_left)
    // mechStick.b().and(mechStick.povLeft())
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.setOutputRearH(),
    //     m_arm));

    
    // // outtake/shoot (press right bumper)
    // mechStick.rightBumper()
    //   .onTrue(new StartEndCommand(
    //     () -> m_endEffector.startOutput(),
    //     () -> m_endEffector.brake(),
    //     m_arm, m_endEffector
    //   ).withTimeout(Constants.EndEffector.OUTTAKE_SECONDS)
    //   );

    // m_driverController.x()
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.goUp()))
    //   .onFalse(new InstantCommand(
    //     () -> m_arm.stop()));

    // m_driverController.b()
    //   .onTrue(new InstantCommand(
    //     () -> m_arm.goDown()))
    //   .onFalse(new InstantCommand(
    //     () -> m_arm.stop()));
      
    
    // //default behavior to stow arm and brake intake
    // m_endEffector.setDefaultCommand(new RunCommand(() -> m_endEffector.brake(), m_endEffector));
    // // m_arm.setDefaultCommand(new RunCommand(() -> m_arm.stow(), m_arm));

  }

  private void configureBindings2(){

  }

  public void enableControllers() {
    if(m_swerve.getDefaultCommand() != null) m_swerve.getDefaultCommand().cancel();

    RunCommand dc = new RunCommand(
      () -> m_swerve.drive(
        m_driverController.getRawAxis(Constants.OperatorConstants.STRAFE_AXIS)*4, 
        m_driverController.getRawAxis(Constants.OperatorConstants.FORWARD_AXIS)*4, 
        Math.pow(m_driverController.getRawAxis(Constants.OperatorConstants.TURN_AXIS),3)*150,
        true
      ),
      m_swerve
    );
    dc.setName("Joystick Control");

    this.driveCommand = dc;
    m_swerve.setDefaultCommand(dc);

    // RunCommand mce = new RunCommand(
    //   () -> {
    //     m_extender.setSpeed(0.5*mechStick.getRawAxis(4));
    //   },
    //   m_extender
    // );
    // mce.setName("Joystick Extend Control");

    // m_extender.setDefaultCommand(mce);

    // RunCommand mcu = new RunCommand(
    //   () -> {
    //     m_elevator.setSpeed(0.3*mechStick.getRawAxis(5));
    //   },
    //   m_elevator
    // );
    // mcu.setName("Joystick Elevator Control");

    // m_elevator.setDefaultCommand(mcu);
  }

  public void disableControllers() {
    if(this.driveCommand == null && this.driveCommand.getName().equals("Joystick Control")) {
      this.driveCommand.cancel();
      this.driveCommand = null;
    }
  }

  public void zeroAllOutputs() {
    if(m_swerve.getDefaultCommand() != null) m_swerve.getDefaultCommand().cancel();

    RunCommand dc = new RunCommand(
      () -> m_swerve.zeroWheels(),
      m_swerve
    );
    dc.setName("Zeroing Control");

    this.driveCommand = dc;
    m_swerve.setDefaultCommand(dc);
  }

  public Command getAutonomousCommand() {
    /*return new InstantCommand(() -> {
      RunCommand dc = new RunCommand(
        () -> m_swerve.drive(
          0,-0.56,0,true
        ),
        m_swerve
      );
      dc.setName("Joystick Control");
  
      this.driveCommand = dc;
      m_swerve.setDefaultCommand(dc);
    });*/
    // return this.m_chooser.getSelected();
    //return new CSBalancing(false);
    return null;
  }

}
