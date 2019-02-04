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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.C_TestFollowMotors;
import frc.robot.util.PIDCont;

/**
 * Add your docs here.
 */
public class SS_FollowTestMotors extends Subsystem {

  private static double SPEED = 0.3;
  private CANSparkMax masterMotor;
  private CANSparkMax slaveMotor;
  private CANPIDController PID;

  public SS_FollowTestMotors() {
    masterMotor = new CANSparkMax(RobotMap.TEST_SPARK_ONE, MotorType.kBrushless);
    slaveMotor = new CANSparkMax(RobotMap.TEST_SPARK_TWO, MotorType.kBrushless);

    // PID = new CANPIDController(masterMotor);
    // PID.setP(1);
    // PID.setI(.01);
    // PID.setD(3);
    // PID.setOutputRange(-.2, .2);

    // slaveMotor.follow(masterMotor);
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void goToPos(double pos){
    masterMotor.set(0.2);
    
    
    // SmartDashboard.putNumber("Follow subsystem", 1);
    // masterMotor.getPIDController().setReference(pos, ControlType.kPosition);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new C_TestFollowMotors());
  }
}
