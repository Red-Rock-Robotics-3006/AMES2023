// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Zipline extends SubsystemBase
{
    private final boolean ADJUST_ELEVATOR = false;
    private Elevator m_elevator;
    // Initialize motors
    private CANSparkMax m_ziplineLeft = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax m_ziplineRight = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    // One might need to be inverted
  
  /** Creates a new ExampleSubsystem. */
  public Zipline(Elevator elevator)
  {
    this.m_elevator = elevator;
    this.setName("Zipline");
    this.register();



    // Set up motors
    this.m_ziplineLeft.restoreFactoryDefaults();
    this.m_ziplineRight.restoreFactoryDefaults();
    this.m_ziplineLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
    this.m_ziplineRight.setIdleMode(CANSparkMax.IdleMode.kBrake);

  }


  @Override
  public void periodic()
  {
    
  }


  public void setSpeed(double targetSpeed)
  {
    this.m_ziplineLeft.set(targetSpeed);
    this.m_ziplineRight.set(targetSpeed);

    if(ADJUST_ELEVATOR)
        m_elevator.setDirection((int)Math.signum(targetSpeed));
  }

//   @Override
//   public void simulationPeriodic() {
//     // This method will be called once per scheduler run during simulation
//   }
}
