/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.C_MotorTest;

/**
 * Add your docs here.
 */
public class SS_Climber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private CANSparkMax rearClimberMotor;
  private CANPIDController PID;

  private double fakeEncoder = 0;
  private double speedMultiplier = 1;
  private double TICKS_PER_DEGREE = 210/360  ; 

  public SS_Climber() {
    rearClimberMotor = new CANSparkMax(RobotMap.TEST, MotorType.kBrushless);
    PID = new CANPIDController(rearClimberMotor);
    PID.setP(15);
    PID.setI(0);
    PID.setD(0);
    PID.setOutputRange(-1, 1);
  }


  public double degreeToTicks(int degree){
    return degree/TICKS_PER_DEGREE;
  }

  public double ticksToDegrees(double ticks){
    return ticks*TICKS_PER_DEGREE;
  }

  public double getRawEncoder(){
    return rearClimberMotor.getEncoder().getPosition();
  }


  public double getAngle(){

    fakeEncoder = getRawEncoder();
    Math.abs(fakeEncoder);

    double angle = 0;
    double currentAngleMod = ticksToDegrees(fakeEncoder) % 360;
    if (currentAngleMod < 0){
      angle = currentAngleMod*360;
    } 
    

    return angle;
  }

  public void set(double speed){
    rearClimberMotor.set(speed);
  }

  public void goToDegree(int degree){

  }

  public void goToPos(double tick){
    rearClimberMotor.getPIDController().setReference(tick, ControlType.kPosition);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new C_MotorTest());
  }
}
