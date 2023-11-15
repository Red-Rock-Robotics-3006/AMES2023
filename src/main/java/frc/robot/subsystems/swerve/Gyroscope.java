package frc.robot.subsystems.swerve;

import com.ctre.phoenix.sensors.Pigeon2;

public class Gyroscope {
  private static Pigeon2 instance;

  public static Pigeon2 getPigeonInstance() {
    if(instance==null) instance = new Pigeon2(45);
    return instance;
  }
}
