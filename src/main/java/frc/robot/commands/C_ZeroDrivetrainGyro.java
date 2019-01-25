package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_HolonomicDrivetrain;
/**
 * sets gyro values to 0
 */

public class C_ZeroDrivetrainGyro extends Command {
	SS_HolonomicDrivetrain drivetrain;
	
	public C_ZeroDrivetrainGyro() {
		requires(Robot.getDrivetrain());
		drivetrain = Robot.getDrivetrain();
	}

	@Override
	public void execute() {
		drivetrain.zeroGyro();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
