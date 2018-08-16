import org.newdawn.slick.Input;

import utilities.BoundingBox;

/**
 * @author parnak Handles the enemylaser shots.
 */
public class EnemyLaser extends Sprite {

	private static float E_laser_speed = 0.7f;

	private static String Droidshot = "res/enemy-shot.png";

	/**
	 * @param x
	 * @param y
	 * @param delay
	 */
	public EnemyLaser(float x, float y, float delay) {
		super(Droidshot, x, y, 0);
		// TODO Auto-generated constructor stub
	}

	public void update(Input input, int delta) {

		this.setY(this.getY() + delta * E_laser_speed);
		BoundingBox bbox = this.getBbox();
		bbox.setTop(this.getY());
	}
}
