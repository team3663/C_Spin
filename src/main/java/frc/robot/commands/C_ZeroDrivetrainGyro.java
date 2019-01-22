package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_HolonomicDrivetrain;
/**
 * sets gyro values to 0
 */

public class C_ZeroDrivetrainGyro extends Command {

	public C_ZeroDrivetrainGyro() {
	}

	@Override
	public void execute() {
		Robot.getDrivetrain().zeroGyro();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
