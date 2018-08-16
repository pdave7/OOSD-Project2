import org.newdawn.slick.Input;

import utilities.BoundingBox;

/**
 * @author parnak Handles the shieldpowerup loading and its movement from the
 *         update method
 *
 */
public class ShieldPowerup extends Sprite {
	private static final float shieldspeed = 0.1f;
	private static String shield_powerup = "res/shield-powerup.png";

	/**
	 * @param x
	 * @param y
	 * @param delay
	 */
	public ShieldPowerup(float x, float y, float delay) {
		super(shield_powerup, x, y, delay);

		// TODO Auto-generated constructor stub
	}

	public void update(Input input, int delta) {

		this.setY(this.getY() + delta * shieldspeed);
		BoundingBox bbox = this.getBbox();
		bbox.setTop(this.getY());
	}

}
