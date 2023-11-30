package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.EndEffector;

public class SetIntakeWaitCommand extends CommandBase{
    private final EndEffector m_effector;
    private final Arm m_arm;

    public SetIntakeWaitCommand(EndEffector effector, Arm arm){
        this.m_effector = effector;
        this.m_arm = arm;

        this.addRequirements(effector, arm);
    }

    @Override
    public void initialize(){
        this.m_effector.startIntake();
    }

    @Override
    public void execute(){
        this.m_effector.startIntake();
    }

    @Override
    public void end(boolean interrupted){
        this.m_effector.brake();
        this.m_arm.stow();
    }

    @Override
    public boolean isFinished(){
        return this.m_effector.aboveSpikeLimit();
    }


}