package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class EndEffector extends SubsystemBase {
    

    private final CANSparkMax leftMotor = new CANSparkMax(Constants.EndEffector.LEFT_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    private final CANSparkMax rightMotor = new CANSparkMax(Constants.EndEffector.RIGHT_MOTOR_ID, CANSparkMax.MotorType.kBrushless);


    private static EndEffector instance;

    private EndEffector() {
        this.setName("End Effector");
        this.register();

        this.leftMotor.restoreFactoryDefaults();
        this.leftMotor.setInverted(false);
        this.leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.rightMotor.restoreFactoryDefaults();
        this.rightMotor.setInverted(true);
        this.rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }


    public void startIntake(double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public void brake() {
        this.leftMotor.set(0);
        this.rightMotor.set(0);
    }

    public void startOutput(double speed) {
        this.leftMotor.set(-speed);
        this.rightMotor.set(-speed);
    }

    public void startIntake(){
        this.startIntake(Constants.EndEffector.NORMAL_ACTIVE_SPEED);
    }

    public void startOutput(){
        this.startOutput(Constants.EndEffector.NORMAL_ACTIVE_SPEED);
    }


    public boolean hasBall(){
        return (leftMotor.getOutputCurrent() > Constants.EndEffector.BALL_SPIKE_CURRENT && leftMotor.getOutputCurrent() < Constants.EndEffector.ACCEL_SPIKE_CURRENT) || (rightMotor.getOutputCurrent() > Constants.EndEffector.BALL_SPIKE_CURRENT && rightMotor.getOutputCurrent() < Constants.EndEffector.ACCEL_SPIKE_CURRENT);
    }

    public boolean aboveSpikeLimit(){
        return leftMotor.getOutputCurrent() > Constants.EndEffector.BALL_SPIKE_CURRENT || rightMotor.getOutputCurrent() > Constants.EndEffector.BALL_SPIKE_CURRENT;
    }

    public static EndEffector getInstance(){
        if (instance == null) instance = new EndEffector();
        return instance;
    }
}