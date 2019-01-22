/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_HolonomicDrivetrain;

public class C_Circle extends Command {

  private static double RADIUS_MULTIPLY = 0.5;
  private static double DEGREE_INCREASE = 0.1;
  
  private double targetDegree;
  private double degree = 0;
  private int direction = 1;

  /**
   * @param radius Radius of the circle in feet
   */
  public C_Circle(double targetDegree, int direction) {
    requires(Robot.getDrivetrain());
    this.direction = direction;
    this.targetDegree = targetDegree * 180 / Math.PI;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double forward = Math.sin(degree) * RADIUS_MULTIPLY * direction;
    double strafe = Math.cos(degree) * RADIUS_MULTIPLY * direction;
    Robot.getDrivetrain().holonomicDrive(forward, strafe, 0);
    degree += DEGREE_INCREASE;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return degree >= targetDegree;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
