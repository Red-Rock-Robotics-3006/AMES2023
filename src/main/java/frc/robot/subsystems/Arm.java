package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("unused")
public class Arm extends SubsystemBase{
    private final CANSparkMax armMotor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);//filler motor id
    private final double MAX_ANGLE = 1000;//-----
    private final double MIN_ANGLE = -1000;
    private final double SCORE_ANGLE_L_18 = 0;
    private final double SCORE_ANGLE_R_18 = 0;
    private final double SCORE_ANGLE_L_24 = 0;
    private final double SCORE_ANGLE_R_24 = 0;
    private final double INTAKE_ANGLE_L = 0;
    private final double INTAKE_ANGLE_R = 0;
    private final double REST_ANGLE = 0;//all values between these comments are filler angles and are to be adjusted during tuning
    private double currentAngle;

    public Arm() {
        this.setName("Arm");
        this.register();

        this.armMotor.restoreFactoryDefaults();
        this.armMotor.setInverted(false);
        this.armMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        //if not homing
            //default to REST_ANGLE
    }

    
}