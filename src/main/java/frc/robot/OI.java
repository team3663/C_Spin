package frc.robot;

import frc.robot.commands.*;
import frc.robot.input.IGamepad;
import frc.robot.input.XboxGamepad;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private IGamepad primaryController = new XboxGamepad(0);

    private Robot mRobot;

    public OI(Robot robot) {
        mRobot = robot;
    }

    public void registerControls() {
        

        primaryController.getLeftBumperButton().whenPressed(new C_SetFieldOriented(mRobot.getDrivetrain(), false));
        primaryController.getLeftBumperButton().whenReleased(new C_SetFieldOriented(mRobot.getDrivetrain(), true));
        primaryController.getStartButton().whenPressed(new C_HolonomicDrive());
        
    }

    public IGamepad getPrimaryController() {
        return primaryController;
    }

}
