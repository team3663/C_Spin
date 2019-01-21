/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.ElapsedTime;
import frc.robot.Robot;

/**
 * moves drivetrain forward for certain time
 */
public class C_GoForward extends Command {
  private final ElapsedTime timer = new ElapsedTime();
  double speed = -.5;
  int sec;

  /**
   * moves drivetrain forward for a certian time
   * @param sec how long it will move forward for
   */
  public C_GoForward(int sec ) {
    requires(Robot.ss_holonomicdrivetrain);
    this.sec = sec;
  } 
  /**
   * second constructor to modify speed
   * @param sec how long it will move forward for
   * @param speed how fast it will run
   */
   public C_GoForward(int sec , double speed) {
    requires(Robot.ss_holonomicdrivetrain);
    this.sec = sec;
    this.speed = speed;
  }
  @Override
  protected void initialize() {
    timer.reset();

    speed *= Math.abs(speed);
    SmartDashboard.putNumber("Forward", speed);
    
    Robot.ss_holonomicdrivetrain.holonomicDrive(speed, 0, 0);
  }

  @Override
  protected boolean isFinished() {
    if(timer.getElapsedSeconds() >= sec){
      return true;
    }
    return false;

  }

  @Override
  protected void end() {
    Robot.ss_holonomicdrivetrain.stopDriveMotors();
  }
 
}
