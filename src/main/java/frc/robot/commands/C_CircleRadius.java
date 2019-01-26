/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_CircleRadius extends Command {

  private double degree = 0;
  private double inchesPerDegree;
  private double arc;
  private double speed;
  private int mirrored = 1;
  private int direction;
  private double oldDistance;

  /**
   * @param speed The speed the robot should tavel (from -1 to 1)
   * @param radius The radius of the circle (inches)
   * @param arc How far the robot travels around the circle (degrees)
   * @param mirrored determines if the circle is mirrored on the x axis.
   */
  public C_CircleRadius(double speed, double radius, double arc, boolean mirrored) {
    requires(Robot.getDrivetrain());
    this.speed = speed;
    this.arc = arc;
    if(mirrored) {
      this.mirrored = -1;
    }
    direction = (int)Math.signum(speed * arc);
    inchesPerDegree = (Math.PI * radius * 2) / arc;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    oldDistance = Robot.getDrivetrain().getSwerveModule(1).getDriveDistance();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double distance = oldDistance = Robot.getDrivetrain().getSwerveModule(1).getDriveDistance();
    double forward = Math.sin(degree * Math.PI / 180) * speed * direction * mirrored;
    double strafe = Math.cos(degree * Math.PI / 180) * speed * direction;
    Robot.getDrivetrain().holonomicDrive(forward, strafe, 0);
    if(distance - oldDistance >= inchesPerDegree) {
      degree += direction;
      oldDistance = distance;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return degree >= arc;
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
