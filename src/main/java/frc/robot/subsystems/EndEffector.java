package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    private final CANSparkMax leftMotor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushed);//filler motor id
    private final CANSparkMax rightMotor = new CANSparkMax(2, CANSparkMax.MotorType.kBrushed);//filler motor id

    //private double targetSpeed;

    public EndEffector() {
        this.setName("End Effector");
        this.register();

        this.leftMotor.restoreFactoryDefaults();
        this.leftMotor.setInverted(false);
        this.leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.rightMotor.restoreFactoryDefaults();
        this.rightMotor.setInverted(true);
        this.rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void periodic() {
        //if there is no ball in the endeffector
            this.startIntake(4);//filler value for speed
        //else if ball is in the endeffector
            this.brake();
        //else if the driver presses shoot button
            this.startOutput(4);//filler value for speed

            //will need to somehow detect when ball enters/leaves, which will be able to make the endeffector brake or startIntake again
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
}