import org.newdawn.slick.Input;

import utilities.BoundingBox;

/**
 * @author parnak Handles the speedpowerup loading and its movement using the
 *         update method
 */
public class SpeedPowerup extends Sprite {

	private static final float shieldspeed = 0.1f;
	private static String speed_powerup = "res/shotspeed-powerup.png";

	/**
	 * @param x
	 * @param y
	 * @param delay
	 */
	public SpeedPowerup(float x, float y, float delay) {
		super(speed_powerup, x, y, delay);
		// TODO Auto-generated constructor stub
	}

	public void update(Input input, int delta) {

		this.setY(this.getY() + delta * shieldspeed);
		BoundingBox bbox = this.getBbox();
		bbox.setTop(this.getY());
	}

}
