package frc.robot.subsystems;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{
    private static Arm instance = null;
    private final CANSparkMax armMotor = new CANSparkMax(Constants.Arm.MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    private static double kP = 0.012;    ;
    private static double kI = 0.000000000;
    private static double kD = 0.0035;
    private static double kF = 0.06;
    private static double kS = 0;
    // private static double kG = 0.33;
    // private static double kV = 1.95;
    // private static double kA = 0.02;
    private double startingAngle;
    
    private PIDController controller = new PIDController(kP, kI, kD);
    //private ArmFeedforward feedForward = new ArmFeedforward(kS, kG, kV, kA);
    private RelativeEncoder encoder = armMotor.getEncoder();
    private double currentAngle;
    private double targetAngle = Constants.Arm.STOW_ANGLE;

    private Arm() {
        this.setName("Arm");
        this.register();

        this.armMotor.restoreFactoryDefaults();
        this.armMotor.setInverted(false);
        this.armMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        encoder.setPosition(this.toTicks(Constants.Arm.MIN_ANGLE));
    }

    @Override
    public void periodic() {
        controller.setPID(kP, kI, kD);
        currentAngle = this.getAngle();
        double feedforward = kF * Math.cos(Math.toRadians(currentAngle)); // account for gravity: tourque =  r * F * cos(theta) |  r * F is tunable kF term//feedForward.calculate(Math.toRadians(targetAngle), 6, 2);//kF * Math.abs(Math.cos(Math.toRadians(currentAngle))); // account for gravity: tourque =  r * F * cos(theta) |  r * F is tunable kF term
        // System.out.println("l" + feedforward);
        System.out.println("target angle: "+ targetAngle);
        // armMotor.set((controller.calculate(encoder.getPosition(), this.toTicks(targetAngle)) + feedforward) * (-Math.pow(0.95 * ((currentAngle-targetAngle)/(Math.abs(startingAngle - targetAngle))), 2) + 1)); // part being multiplied is to limit the speed of the arm early on, and let it go faster later. Scrappy slew limiter
        armMotor.set((controller.calculate(encoder.getPosition(), this.toTicks(targetAngle)) + feedforward));
        // System.out.println(encoder.getPosition());
        // System.out.println(currentAngle);
        System.out.println("Speed" + (controller.calculate(encoder.getPosition(), this.toTicks(targetAngle)) + feedforward));
    }

    public void goUp() {
        armMotor.set(0.05);
    }

    public void goDown() {
        armMotor.set(-0.05);
    }

    public void stop()
    {
        armMotor.set(0);
    }

    public double getAngle() {
        double angle;
        double currentTick = encoder.getPosition();
        angle = 6.0645 * (currentTick + 95.63);
        return angle;
    }

    public double toTicks(double angle) {
        double ticks = (angle / 6.0645) - 95.63;
        return ticks;
    }

    public void setTarget(double t){
        this.targetAngle = t;
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
        this.setTarget(Constants.Arm.MIN_ANGLE);
    }

    public void setIntakeRear(){
        this.setTarget(Constants.Arm.MAX_ANGLE);
    }

    public void stow(){
        this.setTarget(Constants.Arm.STOW_ANGLE);
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

    public void setStartingAngle()
    {
        startingAngle = currentAngle;
    }
}
