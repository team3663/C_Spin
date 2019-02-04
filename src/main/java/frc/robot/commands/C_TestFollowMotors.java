        /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_FollowTestMotors;

public class C_TestFollowMotors extends Command {
  
  private SS_FollowTestMotors testMotors;

  public C_TestFollowMotors() {
    requires(Robot.getTestMotors());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    testMotors = Robot.getTestMotors();
    testMotors.goToPos(0.3);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.getTestMotors().goToPos(3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
