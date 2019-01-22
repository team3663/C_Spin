package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_HolonomicDrivetrain;
/**
 * sets field orientation
 */

public final class C_SetFieldOriented extends Command {

    private boolean field;

    @Deprecated
    public C_SetFieldOriented(boolean field) {
        requires(Robot.getDrivetrain());
        this.field = field;
    }

    @Override
    protected void execute() {
        Robot.getDrivetrain().setFieldOriented(field);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
