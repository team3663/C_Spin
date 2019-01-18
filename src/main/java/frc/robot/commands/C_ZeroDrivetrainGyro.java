package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SS_HolonomicDrivetrain;

public class C_ZeroDrivetrainGyro extends Command {
	private SS_HolonomicDrivetrain mDrivetrain;

	public C_ZeroDrivetrainGyro(SS_HolonomicDrivetrain drivetrain) {
		mDrivetrain = drivetrain;
	}

	@Override
	public void execute() {
		mDrivetrain.zeroGyro();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
