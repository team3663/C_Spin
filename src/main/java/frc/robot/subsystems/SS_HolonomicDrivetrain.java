/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
    * 0 is Front Right
    * 1 is Front Left
    * 2 is Back Left
    * 3 is Back Right
    * 
    *   front
    * 1------0
    * |      |          
    * |      |
    * 2------3
*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.C_HolonomicDrive;

public class SS_HolonomicDrivetrain extends Subsystem {
    public static final double WHEELBASE = 14.5;  // Swerve bot: 14.5 Comp bot: 20.5
    public static final double TRACKWIDTH = 14.5; // Swerve bot: 13.5 Comp bot: 25.5

    public static final double WIDTH = 21;  // Swerve bot: 20 Comp bot: 37
    public static final double LENGTH = 21; // Swerve bot: 19 Comp bot: 32

	private double adjustmentAngle = 0;
	private boolean fieldOriented = true;

	private final double width = 14.5;
	private final double length = 14.5;
	private double speedMultiplier = 1;

    private SwerveModule[] swerveModules;
    private AHRS navX = new AHRS(Port.kUSB);

    public SS_HolonomicDrivetrain() { 
        zeroGyro();

        double FL = 148; //front right
        double FR = 10; //fronnt left
        double BL = 65; //back right
        double BR = 32; //back left
        SmartDashboard.putNumber("Front Right", FR);
        SmartDashboard.putNumber("Front Left", FL);
        SmartDashboard.putNumber("Back Right", BR);
        SmartDashboard.putNumber("Back Left", BL);

        swerveModules = new SwerveModule[]  {
            new SwerveModule(0, new TalonSRX(RobotMap.getAngleMotors(0)), new CANSparkMax(RobotMap.getDriveMotors(0), MotorType.kBrushless), FR),
            new SwerveModule(1, new TalonSRX(RobotMap.getAngleMotors(1)), new CANSparkMax(RobotMap.getDriveMotors(1), MotorType.kBrushless), FL),
            new SwerveModule(2, new TalonSRX(RobotMap.getAngleMotors(2)), new CANSparkMax(RobotMap.getDriveMotors(2), MotorType.kBrushless), BL),
            new SwerveModule(3, new TalonSRX(RobotMap.getAngleMotors(3)), new CANSparkMax(RobotMap.getDriveMotors(3), MotorType.kBrushless), BR),
        };
        swerveModules[3].setDriveInverted(true);
    
        for (SwerveModule module : swerveModules) {
            //module.setTargetAngle(0);
            module.setDriveGearRatio(5.7777);
            module.setDriveWheelRadius(module.getDriveWheelRadius() * 1.05);
        }
        // holonomicDrive(0, 0, .4);
    }

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new C_HolonomicDrive());
	}

	public void zeroGyro() {
		setAdjustmentAngle(getRawGyroAngle());
    }

    public void stopDriveMotors() {
        for (SwerveModule module : swerveModules) {
            module.setTargetSpeed(0);
        }
    }
    
    /**
     * resets the motors on all four swerve modules
     */
    public void resetMotors() {
    	for(int i = 0; i < swerveModules.length; i++) {
    		swerveModules[i].resetMotor();
    	}
    }

    public void holonomicDrive(double forward, double strafe, double rotation) {
		holonomicDrive(forward, strafe, rotation, isFieldOriented());
    }

    /**
     * @param forward Forward power (-1 to 1)
     * @param strafe Side to side power (-1 to 1)
     * @param rotation Speed of rotation (-1 to 1)
     * @param fieldOriented Determines wether robot moves relative to field or its rotation
     */
    public void holonomicDrive(double forward, double strafe, double rotation, boolean fieldOriented) {
        forward *= getSpeedMultiplier();
        strafe *= getSpeedMultiplier();

        if (fieldOriented) {
            double angleRad = Math.toRadians(getGyroAngle());
            double temp = forward * Math.cos(angleRad) +
            strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
        }

        double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
        double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
        double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
        double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

        double[] angles = new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };

        double[] speeds = new double[]{
                Math.sqrt(b * b + c * c),
                Math.sqrt(b * b + d * d),
                Math.sqrt(a * a + d * d),
                Math.sqrt(a * a + c * c)
        };

        double max = speeds[0];

        for (double speed : speeds) {
            if (speed > max) {
                max = speed;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (Math.abs(forward) > 0.05 ||
                    Math.abs(strafe) > 0.05 ||
                    Math.abs(rotation) > 0.05) {
                swerveModules[i].setTargetAngle(angles[i] + 180);
            } else {
                swerveModules[i].setTargetAngle(swerveModules[i].getTargetAngle());
            }
            swerveModules[i].setTargetSpeed(speeds[i]);
        }
    }

    /**
     * @param forward Forward power (-1 to 1)
     * @param strafe Side to side power (-1 to 1)
     * @param rotation Speed of rotation (-1 to 1)
     * @return An array of doubles for the angles of each swerve module (in degrees)
     */
    public double[] calculateSwerveModuleAngles(double forward, double strafe, double rotation) {
        if (isFieldOriented()) {
            double angleRad = Math.toRadians(getGyroAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
        }

        double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
        double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
        double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
        double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

        return new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };
    }


	public boolean isFieldOriented() {
		return fieldOriented;
    }
    
    public AHRS getNavX() {
        return navX;
    }

    /**
     * @return The gyro angle in degrees
     */
    public double getGyroAngle() {
        double angle = navX.getAngle() - getAdjustmentAngle();
        angle %= 360;
        if (angle < 0) angle += 360;

        if (Robot.PRACTICE_BOT) {
            return angle;
        } else {
            return 360 - angle;
        }
    }

    public double getGyroRate() {
        return navX.getRate();
    }

    /**
     * @return returns the raw gyro angle with no adjustment angle
     */
    public double getRawGyroAngle() {
        double angle = navX.getAngle();
        angle %= 360;
        if (angle < 0) angle += 360;

        return angle;
    }

    public SwerveModule getSwerveModule(int i) {
        return swerveModules[i];
    }

    public SwerveModule[] getSwerveModules() {
        return swerveModules;
    }

    public final double getWidth() {
		return width;
	}

	public final double getLength() {
		return length;
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

    public double getMaxAcceleration() {
        return 5.5;
    }

    public double getMaxVelocity() {
        return 10;
    }

	public double getAdjustmentAngle() {
		return adjustmentAngle;
    }
    

    public void setAdjustmentAngle(double adjustmentAngle) {
		System.out.printf("New Adjustment Angle: % .3f\n", adjustmentAngle);
		adjustmentAngle = adjustmentAngle;
	}

	public void setFieldOriented(boolean fieldOriented) {
		fieldOriented = fieldOriented;
    }
    
    public void setSpeedMultiplier(double speed) {
        speedMultiplier = speed;
    }
}
