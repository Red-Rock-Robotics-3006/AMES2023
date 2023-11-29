package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
// import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  public static final double MIN_HEIGHT = 0.28; //Min height in meters from the ground !FILLER VALUE!
  public static final double MAX_HEIGHT = 1.24; //Max height in meters from the ground !FILLER VALUE!
  private static final double encoderUnitsPerRot = 1; //Number of encoder units per rotation !FILLER VALUE!
  private static final double rotationsPerMeter = 46.405; //Motor rotations per meter of travel !FILLER VALUE!
  private static final double kP = -0.25; //Proportional control for height movement !FILLER VALUE!

  //Target
  private double targetPosLeft = 0.5; //Target height in meters from the ground for left elevator !FILLER VALUE!
  private double targetPosRight = 0.5; //Target height in meters from the ground for right elevator !FILLER VALUE!
  private double targetVel = 0; //Target velocity in mps with away from the ground being positive !FILLER VALUE!
  private boolean m_targetHoming = false; //hm

  //Components
  private CANSparkMax m_elevatorLeft = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax m_elevatorRight = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
  
  // RELATIVE ENCODERS: PLEASE DON'T USE THEM
  // Use absolte encoders instead!
  // I don't know how though
  private RelativeEncoder m_encoderLeft = m_elevatorLeft.getEncoder();
  private RelativeEncoder m_encoderRight = m_elevatorRight.getEncoder();

//   // Idk 
//   private AbsoluteEncoder m_encoderLeft = new AbsoluteEncoder(){
//     // What happens here
//   };
//   private AbsoluteEncoder m_encoderRight = new AbsoluteEncoder(){
//     // What happens here
//   };

  public Elevator()
  {
    //Basic Setup
    this.setName("Elevator");
    this.register();

    //Motor Setup
    this.m_elevatorLeft.restoreFactoryDefaults();
    this.m_elevatorRight.restoreFactoryDefaults();
    this.m_elevatorLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
    this.m_elevatorRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
    //Check if one motor needs to be inverted
  }

  /** Sets the target height in meters relative to the ground */
  public void setTargetPos(double target, elevatorSide s) { // true indicates left elevator, false is right
    if(s == elevatorSide.LEFT)
    {
        this.targetPosLeft = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, target));
        if(Math.abs(this.targetPosLeft-target) > 0.0001) throw new IllegalArgumentException("Target is beyond Elevator range.");
    }
    else if(s == elevatorSide.RIGHT)
    {
        this.targetPosRight = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, target));
        if(Math.abs(this.targetPosRight-target) > 0.0001) throw new IllegalArgumentException("Target is beyond Elevator range.");
    }
  }
  /** Sets mps velocity of elevator with up positive */
//   public void setTargetVel(double target) {
//     this.targetVel = target;
//   }
  /** Meter position of elevator off of ground */
  public double getPos(boolean encoder) { // true indicates left encoder, false indicates right
    //https://www.chiefdelphi.com/t/help-using-neo-motor-encoders/405181/8
    if(encoder)
    {
        return MIN_HEIGHT + this.m_encoderLeft.getPosition()/encoderUnitsPerRot/rotationsPerMeter;
    }
    else
    {
        return MIN_HEIGHT + this.m_encoderRight.getPosition()/encoderUnitsPerRot/rotationsPerMeter;
    }
  }

  //Target Homing
  public void enableHoming() {
    this.m_targetHoming = true;
  }
  public void disableHoming() {
    this.m_targetHoming = false;
  }

  @Override
  public void periodic()
  {
    // Only for use with dynamic elevator length
    if(this.m_targetHoming) {
      try {
        setTargetPos(targetPosLeft + (this.targetVel*0.02), elevatorSide.LEFT); //0.02 mps for CommandScheduler update cycle
        setTargetPos(targetPosRight + (this.targetVel*0.02), elevatorSide.RIGHT); //0.02 mps for CommandScheduler update cycle
      } catch(IllegalArgumentException e) {
        this.targetVel = 0; //Stops movement if range end met.
        System.out.println("Elevator range met. Setting Velocity to 0.");
      }
      
      System.out.println((this.targetPosLeft - getPos(true)) / (MAX_HEIGHT-MIN_HEIGHT));
      double motorPowerLeft = -kP * ((this.targetPosLeft - getPos(true)) / (MAX_HEIGHT-MIN_HEIGHT));
      System.out.println((this.targetPosRight - getPos(false)) / (MAX_HEIGHT-MIN_HEIGHT));
      double motorPowerRight = -kP * ((this.targetPosRight - getPos(false)) / (MAX_HEIGHT-MIN_HEIGHT));
        //Dividing error scales to -1 to 1
      this.m_elevatorLeft.set(motorPowerLeft);
      this.m_elevatorRight.set(motorPowerRight);
    }
  }

  public void resetEncoder() {
    this.m_encoderLeft.setPosition(0);
    this.m_encoderRight.setPosition(0);
  }

  public void setSpeed(double targetSpeed)
  {
    this.m_elevatorLeft.set(targetSpeed);
    this.m_elevatorRight.set(targetSpeed);
  }

  // ONLY FOR USE WITH AN ENCODER ON BOTH MOTORS
  public void setDirection(int direction) // -1 is backwards, 0 is stopped, 1 is forwards
  {
    if(direction>0)
    {
        // lower which ever elevator is in front or back?
    }
    else if(direction<0)
    {

    }
    else
    {
        this.setTargetPos(MAX_HEIGHT-0.2, elevatorSide.LEFT); // !FILLER VALUE!
        this.setTargetPos(MAX_HEIGHT-0.2, elevatorSide.RIGHT); // !FILLER VALUE!
    }
  }

  public enum elevatorSide {
    LEFT, RIGHT
  }
//   @Deprecated
//   public double getEncoderPos() {
//     return this.m_encoder.getPosition();
//   }
}