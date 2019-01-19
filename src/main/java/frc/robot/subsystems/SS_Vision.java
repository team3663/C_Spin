/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.C_InitCamera;

import edu.wpi.first.networktables.*; 

/**
 * Add your docs here.
 */
public class SS_Vision extends Subsystem {
  private NetworkTable limelightNetworkTable;
  
  
  private NetworkTableEntry x;
  private NetworkTableEntry y;
  private NetworkTableEntry targetArea;
  private NetworkTableEntry validTarget;
  private NetworkTableEntry skew;
  private NetworkTableEntry latency;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_InitCamera());
  }

  public SS_Vision() {
    limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
    x = limelightNetworkTable.getEntry("tx");
    y = limelightNetworkTable.getEntry("ty");
    targetArea = limelightNetworkTable.getEntry("ta");
    validTarget = limelightNetworkTable.getEntry("tv");
    skew = limelightNetworkTable.getEntry("ts");
    latency = limelightNetworkTable.getEntry("tl");
  }

  //0 = LED mode set in current pipeline, 1 = off, 2 = blink, 3 = on
  public void setLightMode(int mode) {
		limelightNetworkTable.getEntry("ledMode").setNumber(mode);
  }
  

  // 0 or 1, 0 = vision processing, 1 = driver camera, no vision processing
  public boolean setCameraMode(int mode) {
		return limelightNetworkTable.getEntry("camMode").setNumber(mode);
  } 
  
  //0 - 9, pipeline numbers
  public void setPipeline(int pipeline) {
		limelightNetworkTable.getEntry("pipeline").setNumber(pipeline);
  }
  
  //Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
	public double getXOffset() {
		return x.getDouble(-1);
  }
  
  //Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
  public double getYOffset() {
    return y.getDouble(-1);
  }

  //Target Area (0% of image to 100% of image)
  public double getTargetArea() {
    return targetArea.getDouble(-1);
  }

  //Whether the limelight has any valid targets, T/F
  public boolean validTarget() {
    return validTarget.getDouble(-1) == 1; 
  }

  //imma be honest, i have no idea what this data means but here it is 
  //from -90 degrees to 0 degrees, or so the documentation says
  public double getTargetSkew() {
    return skew.getDouble(1);
  }

  //this one's pretty obvious
  public double getCameraLatency() {
    return latency.getDouble(-1);
  }

  // called by C_InitCamera, sets basic camera settings defined in RobotMap
  private boolean init = false;
  public boolean initCamera(int cameraMode, int lightMode, int pipeline) {
    if (!init) {
      setCameraMode(cameraMode);
      setLightMode(lightMode);
      setPipeline(pipeline);
      init = true;
      return true;
    }
    return false;
  }
}