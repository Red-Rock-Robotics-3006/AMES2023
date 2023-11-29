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

@SuppressWarnings("unused")
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public class Arm {
    public static final int MOTOR_ID = 1;//-----
    public static final int TICKS_PER_DEGREE = 0;
    public static final double MAX_ANGLE = 195;
    public static final double MIN_ANGLE = -14;
    public static final double SCORE_ANGLE_F_L = 0;
    public static final double SCORE_ANGLE_R_L = 0;
    public static final double SCORE_ANGLE_F_H = 0;
    public static final double SCORE_ANGLE_R_H = 0;
    public static final double INTAKE_ANGLE_F = 0;
    public static final double INTAKE_ANGLE_R = 0;//all values between these comments are filler angles and are to be adjusted during tuning
    public static final double STOW_ANGLE = 90;
  }

  public class EndEffector {
    public static final int LEFT_MOTOR_ID = 59;
    public static final int RIGHT_MOTOR_ID = 26;
    public static final double BALL_SPIKE_CURRENT = 11;
    public static final double ACCEL_SPIKE_CURRENT = 15;
    public static final double NORMAL_ACTIVE_SPEED = 0.25;
    public static final double SPIKE_SECONDS = 0.5;
    public static final double OUTTAKE_SECONDS = 1;
  }
}
