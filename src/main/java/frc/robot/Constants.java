// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static int DRIVER_CONTROLLER_PORT = 0;
    public static int MECH_STICK_PORT = 1;
    public static int STRAFE_AXIS = 0;
    public static int FORWARD_AXIS = 1;
    public static int TURN_AXIS = 4;
    public static int BUTTON_Y = 4;
  }

  public static class Drive{
    //motor ids
    public static final int FL_TURN_MOTOR_ID = 5;
    public static final int FL_DRIVE_MOTOR_ID = 4;
    public static final int FL_CCODER_ID = 23;

    public static final int FR_TURN_MOTOR_ID = 6;
    public static final int FR_DRIVE_MOTOR_ID = 20;
    public static final int FR_CCODER_ID = 24;

    public static final int BL_TURN_MOTOR_ID = 3;
    public static final int BL_DRIVE_MOTOR_ID = 50;
    public static final int BL_CCODER_ID = 22;

    public static final int BR_TURN_MOTOR_ID = 8;
    public static final int BR_DRIVE_MOTOR_ID = 11;
    public static final int BR_CCODER_ID = 21;
  }

  public class Arm{
    public static final int MOTOR_ID = 1;
  }

  public class EndEffector{
    public static final int LEFT_MOTOR_ID = 59;
    public static final int RIGHT_MOTOR_ID = 26;
  }
}
