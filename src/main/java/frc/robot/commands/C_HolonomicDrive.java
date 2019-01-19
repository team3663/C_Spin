package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class C_HolonomicDrive extends Command {
	

	public C_HolonomicDrive() {
		requires(Robot.ss_holonomicdrivetrain);
	}

	private double deadband(double input) {
		if (Math.abs(input) < 0.05) return 0;
		return input;
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {

		double forward = -Robot.getOI().getPrimaryController().getLeftYValue();
		double strafe = Robot.getOI().getPrimaryController().getLeftXValue();
		double rotation = -Robot.getOI().getPrimaryController().getRightXValue();

		forward *= Math.abs(forward);
		strafe *= Math.abs(strafe);
		rotation *= Math.abs(rotation);

		forward = deadband(forward);
		strafe = deadband(strafe);
		rotation = deadband(rotation);

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		// CANSparkMax tests = new CANSparkMax(RobotMap.getDriveMotors(3), MotorType.kBrushless);
		// tests.set(.4);	

		// TalonSRX testt = new TalonSRX(RobotMap.getAngleMotors(3));
		// testt.set( ControlMode.MotionMagic.Velocity, .3);
		Robot.ss_holonomicdrivetrain.holonomicDrive(forward, strafe, rotation);
	}

	@Override
	protected void end() {
		Robot.ss_holonomicdrivetrain.stopDriveMotors();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
