package frc.robot.subsystems.scoring;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MechanismException;

@SuppressWarnings("unused")
public class ArmSubsystem extends SubsystemBase{
    private static ArmSubsystem instance;
    private final CANSparkMax armMotor = new CANSparkMax(Constants.Arm.MOTOR_ID, CANSparkMax.MotorType.kBrushless);//filler motor id
    
    private static final double MAX_ANGLE = 1000;//-----
    private static final double MIN_ANGLE = -1000;
    private static final double SCORE_ANGLE_L_18 = 0;
    private static final double SCORE_ANGLE_R_18 = 0;
    private static final double SCORE_ANGLE_L_24 = 0;
    private static final double SCORE_ANGLE_R_24 = 0;
    private static final double INTAKE_ANGLE_L = 0;
    private static final double INTAKE_ANGLE_R = 0;
    private static final double REST_ANGLE = 0;//all values between these comments are filler angles and are to be adjusted during tuning

    private static final double TICKS_PER_DEGREES = 1; // convert encoder ticks to a usable angle

    private static double kP = 0;
    private static double kI = 0;
    private static double kD = 0;
    private static double kF = 0;
    
    private PIDController controller = new PIDController(kP, kI, kD);

    //intended to be in degrees
    private double currentAngle; //TODO: make this usable
    // 0 = stow/upright position
    // 90 = forward horizantal
    // -90 = rear horizontal



    private double target = 0;



    private ArmSubsystem() {
        this.setName("Arm");
        this.register();

        this.armMotor.restoreFactoryDefaults();
        this.armMotor.setInverted(false);
        this.armMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        controller.setPID(kP, kI, kD);

        double feedforward = kF * Math.cos(Math.toDegrees(currentAngle)); // account for gravity: tourque = F * r * cos(theta) |  F * r is tunable kF term


        //TODO: pid code here, must be run every loop regardless
        
    }

    public void setTarget(double target){
        //TODO:
        // if (target > MAX_POS || target < MIN_POS){
        //     throw new MechanismException("arm pos out of bounds");
        // }

        //changes target for the pid
        //this method will be called every time an arm position change is desired
        this.target = target;
    }

    public void setIntakeForward(){
        //TODO:
        //set target to forward intake position
        //call the setTarget method
        //this.setTarget(FORWARD_INTAKE_POSITION);
    }

    public void setIntakeRear(){
        //TODO:
        //same as setIntakeForward() but for the rear intake position
    }

    public void setOutTakeForward(){
        //TODO:
    }

    public void setOutTakeRear(){
        //TODO:
    }

    public void stow(){
        //TODO:
        //default position for arm 
        //arm will be facing upwards
        //set as default command for this subsystem in RobotContainer
    }

    public void setPIDF(double m_kP, double m_kI, double m_kD, double m_kF){
        kP = m_kP;
        kI = m_kI;
        kD = m_kD;
        kF = m_kF;
    }


    public static ArmSubsystem getInstance(){
        if (instance == null) instance = new ArmSubsystem();
        return instance;
    }
    
}
