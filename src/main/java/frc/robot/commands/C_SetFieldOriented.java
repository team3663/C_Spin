package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_HolonomicDrivetrain;
/**
 * sets field orientation
 */

public final class C_SetFieldOriented extends Command {

    private boolean mField;

    @Deprecated
    public C_SetFieldOriented(boolean field) {
        requires(Robot.ss_holonomicdrivetrain);
        mField = field;
    }

    @Override
    protected void execute() {
        Robot.ss_holonomicdrivetrain.setFieldOriented(mField);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
