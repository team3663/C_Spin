/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ElapsedTime;
import frc.robot.Robot;

/**
 * moves drivetrain forward for certain time
 */
public class C_GoForward extends Command {
  private final ElapsedTime timer = new ElapsedTime();
  double speed = -.5;
  int sec;

  public C_GoForward(int sec) {
    requires(Robot.ss_holonomicdrivetrain);
    this.sec = sec;
  }
  
	private double deadband(double input) {
		if (Math.abs(input) < 0.05) return 0;
		return input;
	}
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.reset();

    speed *= Math.abs(speed);
    speed = deadband(speed);
    SmartDashboard.putNumber("Forward", speed);
    
    Robot.ss_holonomicdrivetrain.holonomicDrive(speed, 0, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(timer.getElapsedSeconds() >= sec){
      return true;
    }
    return false;

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.ss_holonomicdrivetrain.stopDriveMotors();
  }
 
}
