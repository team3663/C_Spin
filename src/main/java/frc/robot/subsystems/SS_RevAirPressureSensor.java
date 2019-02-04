  /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.C_TestPressureSensor;

public class SS_RevAirPressureSensor extends Subsystem {

  //normalized voltage = Vo / (0.004 * Po + 0.1)
  private static final double SUPPLY_VOLTAGE = 5;
  private static final double NORMALIZED_VOLTAGE = 4.98;
  private AnalogInput pressureSensor;

  public SS_RevAirPressureSensor() {
    pressureSensor = new AnalogInput(RobotMap.PRESSURE_SENSOR);
  }

  /**
   * finds the pressure
   * @return the pressure (PSI) that the sensor reads
   */
  public double getPressure() {
    double voltageOut = pressureSensor.getVoltage();
    SmartDashboard.putNumber("Pressure voltage", pressureSensor.getVoltage());
    return 250 * voltageOut / NORMALIZED_VOLTAGE - 25;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_TestPressureSensor());
  }
}
