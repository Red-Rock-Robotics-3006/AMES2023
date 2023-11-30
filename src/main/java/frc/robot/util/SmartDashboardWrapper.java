package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardWrapper{
    public static double createDashboardNumber(String key, double defValue){
        double value = SmartDashboard.getNumber(key, defValue);
        SmartDashboard.putNumber(key, value);
        return value;
    }
}