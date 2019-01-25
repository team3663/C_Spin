/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_Orbit extends Command {
  private double radius;
  private double speed;
  private double degree = 0;
  private final double DEGREE_MULTIPLYER = 0.02;
  public C_Orbit(double radius, double speed) {
    requires(Robot.getDrivetrain());
    this.radius = radius;
    this.speed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double rotation = radius * DEGREE_MULTIPLYER * speed;
    Robot.getDrivetrain().holonomicDrive(0, speed, rotation);
    degree += speed;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return degree >= 360;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.ss_holonomicdrivetrain.stopDriveMotors();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
