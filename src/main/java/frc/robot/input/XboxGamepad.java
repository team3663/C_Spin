package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * <p>IGamepad implementation for XBox-like gamepads.</p>
 *
 * <p>Currently known to work with:
 * <ul>
 *      <li>Xbox 360 wired controller</li>
 *      <li>Logitech F310</li>
 * </ul>
 * </p>
 *
 * @author Jacob Bublitz
 * @since 1.0
 */

public final class XboxGamepad extends Joystick {
	
	public enum Direction {
		UP(0),
		UPRIGHT(45),
		RIGHT(90),
		DOWNRIGHT(135),
		DOWN(180),
		DOWNLEFT(225),
		LEFT(270),
		UPLEFT(315),
		CENTER(-1);

		private final int mAngle;

		Direction(int angle) {
			mAngle = angle;
		}

		public int getAngle() {
			return mAngle;
		}
	}
	
	private final Button[] buttons = {
			new JoystickButton(this, 1), // A Button
			new JoystickButton(this, 2), // B Button
			new JoystickButton(this, 3), // X Button
			new JoystickButton(this, 4), // Y Button
			new JoystickButton(this, 5), // Left Bumper
			new JoystickButton(this, 6), // Right Bumper
			new JoystickButton(this, 7), // Back Button
			new JoystickButton(this, 8), // Start Button
			new JoystickButton(this, 9), // Left Stick Button
			new JoystickButton(this, 10), // Right Stick Button
			new AxisButton(this, 2,0.75), // Left Trigger Button
			new AxisButton(this, 3,0.75), // Right Trigger Button
	};

	private final DPadButton[] dPadButtons;

	/**
	 * @param port The port the controller is on
	 */
	public XboxGamepad(int port) {
		super(port);

		dPadButtons = new DPadButton[DPadButton.Direction.values().length];

		for (DPadButton.Direction dir : DPadButton.Direction.values()) {
			dPadButtons[dir.ordinal()] = new DPadButton(this, dir);
		}
	}

	public double getLeftTriggerValue() {
		return getRawAxis(2);
	}

	public double getLeftXValue() {
		return getRawAxis(0);
	}

	public double getLeftYValue() {
		return -getRawAxis(1);
	}

	public double getRightTriggerValue() {
		return getRawAxis(3);
	}

	public double getRightXValue() {
		return getRawAxis(4);
	}

	public double getRightYValue() {
		return -getRawAxis(5);
	}

	public Button getAButton() {
		return buttons[0];
	}

	public Button getBButton() {
		return buttons[1];
	}

	public Button getXButton() {
		return buttons[2];
	}

	public Button getYButton() {
		return buttons[3];
	}

	public Button getLeftBumperButton() {
		return buttons[4];
	}

	public Button getRightBumperButton() {
		return buttons[5];
	}

	public Button getBackButton() {
		return buttons[6];
	}

	public Button getStartButton() {
		return buttons[7];
	}

	public Button getLeftJoystickButton() {
		return buttons[8];
	}

	public Button getRightJoystickButton() {
		return buttons[9];
	}

	public Button getLeftTriggerButton() {
		return buttons[10];
	}

	public Button getRightTriggerButton() {
		return buttons[11];
	}

	public Button getDPadButton(DPadButton.Direction direction) {
		return dPadButtons[direction.ordinal()];
	}
}
