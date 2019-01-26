/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.C_Circle;
import frc.robot.commands.C_HolonomicDrive;
import frc.robot.commands.C_Orbit;
import frc.robot.subsystems.SS_HolonomicDrivetrain;


public class Robot extends TimedRobot {
  public static final boolean PRACTICE_BOT = true;

  public static SS_HolonomicDrivetrain ss_holonomicdrivetrain;
  public static OI oi;

  
  /**
   * initializes Oi and ss_holonomicdrivetrains
   */
  @Override
  public void robotInit() {
    oi = new OI(this);
    ss_holonomicdrivetrain = new SS_HolonomicDrivetrain();


		oi.registerControls();
    // chooser.addOption("My Auto", new MyAutoCommand());
    

    
  }

  public static OI getOI() {
		return oi;
  }

  
  public static SS_HolonomicDrivetrain getDrivetrain() {
		return ss_holonomicdrivetrain;///
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("TEST " , oi.getPrimaryController().getAButton().get());

		oi.registerControls();


    for (int i = 0; i < 4; i++) {
      SmartDashboard.putNumber("Module Angle " + i, ss_holonomicdrivetrain.getSwerveModule(i).getCurrentAngle());
      SmartDashboard.putNumber("Module Pos " + i, (ss_holonomicdrivetrain.getSwerveModule(i).getDriveDistance()));
      SmartDashboard.putNumber("Module Raw Angle " + i, ss_holonomicdrivetrain.getSwerveModule(i).getAngleMotor().getSelectedSensorPosition(0));
      
  }


SmartDashboard.putNumber("Drivetrain Angle", ss_holonomicdrivetrain.getGyroAngle());
  }

  @Override
  public void disabledInit() {
    for (int i = 0; i < 4; i++) {
			ss_holonomicdrivetrain.getSwerveModule(i).robotDisabledInit();
		}
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }
  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    C_Circle circleOne = new C_Circle(360, false);
    circleOne.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.

  
    

    for (int i = 0; i < 4; i++)
    ss_holonomicdrivetrain.getSwerveModule(i).zeroDistance();
  }
}
