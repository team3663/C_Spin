/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ElapsedTime;
import frc.robot.Robot;
import frc.robot.subsystems.SS_HolonomicDrivetrain;
import frc.robot.util.PIDCont;

public class C_Turn extends Command {

  //time it waits at angle withtin tolerance (in milliseconds)
  private static final double TIME_TOLERANCE = 100;
  private static final double ANGLE_TOLERANCE = 4;

  private SS_HolonomicDrivetrain robot;
  private ElapsedTime timer;
  private PIDCont pid;
  private double targetAngle;
  private double error;

  public C_Turn(double targetAngle, double speed) {
    robot = Robot.getDrivetrain();

    this.targetAngle = targetAngle + robot.getGyroAngle();

    pid = new PIDCont(speed, 1, 0, 0);
    timer = new ElapsedTime();
  }

  @Override
  protected void initialize() { 
    timer.reset();
  }

  @Override
  protected void execute() {
    error = targetAngle - robot.getGyroAngle();
    SmartDashboard.putNumber("turn error", error);
    robot.holonomicDrive(0, 0, pid.get(error), false);

    if(Math.abs(error) > ANGLE_TOLERANCE) {
      timer.reset();
      SmartDashboard.putString("C_Turn", "Running");
    } else {
      SmartDashboard.putString("C_Turn", "At target angle");
    }
  }

  @Override
  protected boolean isFinished() {
    return timer.getElapsedMillis() >= TIME_TOLERANCE;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
    end();
  }
}
