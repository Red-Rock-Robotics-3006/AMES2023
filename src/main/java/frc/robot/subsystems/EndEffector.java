package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    private final CANSparkMax leftMotor = new CANSparkMax(59, CANSparkMax.MotorType.kBrushed);
    private final CANSparkMax rightMotor = new CANSparkMax(26, CANSparkMax.MotorType.kBrushed);

    private boolean hasBall;

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
        if (leftMotor.getOutputCurrent() > 13 && rightMotor.getOutputCurrent() > 13)
            hasBall = true;

        if (!hasBall)
            this.startIntake(0.25);

        else
            this.brake();
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

    public void setBall(boolean b) {
        this.hasBall = b;
    }
}