package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.scoring.ArmSubsystem;
import frc.robot.subsystems.scoring.EndEffectorSubsystem;

public class SetIntakeCommand extends CommandBase{

    private final EndEffectorSubsystem m_effector;
    private final ArmSubsystem m_arm;

    public SetIntakeCommand(EndEffectorSubsystem effector, ArmSubsystem arm){
        this.m_effector = effector;
        this.m_arm = arm;

        this.addRequirements(effector, arm);
    }

    @Override
    public void initialize(){
        m_arm.setIntakeForward();
    }

    @Override
    public void execute(){
        m_effector.startIntake();
    }

    @Override
    public boolean isFinished(){
        return m_effector.hasBall();
    }

    @Override
    public void end(boolean interrupted){
        m_effector.brake();
    }

    
    
}
