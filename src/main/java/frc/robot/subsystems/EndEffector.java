package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    private final CANSparkMax leftMotor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushed);
    private final CANSparkMax rightMotor = new CANSparkMax(2, CANSparkMax.MotorType.kBrushed);

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
        //if the driver presses some sort of button
            startIntake(4);//fille r value for speed
        //if the driver presses another button
            startOutput(4);//filler value for speed
    }

    public void startIntake(double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public void startOutput(double speed) {
        this.leftMotor.set(-speed);
        this.rightMotor.set(-speed);
    }
}