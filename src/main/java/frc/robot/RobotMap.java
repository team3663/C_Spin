/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

/*
    * 0 is Front Right
    * 1 is Front Left
    * 2 is Back Left
    * 3 is Back Right
    * 
    *   front
    * 0------1
    * |      |
    * |      |
    * 3------2
 */
public class RobotMap {

  public static final int TEST = 20;

  public static final int getDriveMotors(int module){
    int motors[] = {0,4,7,3};
    int motor = motors[module];
    return  motor;
  }
  
  public static final int getAngleMotors(int module){
    int motors[] = {1,5,6,2};
    int motor = motors[module];
    return  motor;
    
  }
}
