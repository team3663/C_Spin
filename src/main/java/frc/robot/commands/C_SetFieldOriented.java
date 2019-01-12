package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SS_HolonomicDrivetrain;

public final class C_SetFieldOriented extends Command {

    private final SS_HolonomicDrivetrain drivetrain;
    private final boolean isFieldOriented;

    @Deprecated
    public C_SetFieldOriented(SS_HolonomicDrivetrain drivetrain) {
        this(drivetrain,true);
    }

    public C_SetFieldOriented(SS_HolonomicDrivetrain drivetrain, boolean isFieldOriented) {
        this.drivetrain = drivetrain;
        this.isFieldOriented = isFieldOriented;
    }

    @Override
    protected void execute() {
        drivetrain.setFieldOriented(isFieldOriented);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
