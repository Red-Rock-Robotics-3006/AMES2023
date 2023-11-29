package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase{
    private static Arm instance;
    private final CANSparkMax armMotor = new CANSparkMax(Constants.Arm.MOTOR_ID, CANSparkMax.MotorType.kBrushless);

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

    private Arm() {
        this.setName("Arm");
        this.register();

        this.armMotor.restoreFactoryDefaults();
        this.armMotor.setInverted(false);
        this.armMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        controller.setPID(kP, kI, kD);

        double feedforward = kF * Math.cos(Math.toDegrees(currentAngle)); // account for gravity: tourque =  r * F * cos(theta) |  r * F is tunable kF term

        //TODO: pid to target angle here, must be run every loop regardless yippe
    }

    public void setTarget(double t){
        this.target = t;
    }

    public void setOutputForwardL(){
        this.setTarget(Constants.Arm.SCORE_ANGLE_F_L);
    }

    public void setOutputRearL(){
        this.setTarget(Constants.Arm.SCORE_ANGLE_R_L);
    }

    public void setOutputForwardH(){
        this.setTarget(Constants.Arm.SCORE_ANGLE_F_H);
    }

    public void setOutputRearH(){
        this.setTarget(Constants.Arm.SCORE_ANGLE_R_H);
    }

    public void setIntakeForward(){
        this.setTarget(Constants.Arm.INTAKE_ANGLE_F);
    }

    public void setIntakeRear(){
        this.setTarget(Constants.Arm.INTAKE_ANGLE_R);
    }

    public void stow(){
        //TODO set as default command for this subsystem in RobotContainer
        this.setTarget(Constants.Arm.REST_ANGLE);
    }

    public void setPIDF(double m_kP, double m_kI, double m_kD, double m_kF){
        kP = m_kP;
        kI = m_kI;
        kD = m_kD;
        kF = m_kF;
    }


    public static Arm getInstance(){
        if (instance == null) instance = new Arm();
        return instance;
    }
}