
/**
 * @author parnak Handles the basicshooters loading
 */
public class Basic_shooter extends Sprite {

	private static int basic_Shooter_y = -64;
	private static String shooter_image = "res/basic-shooter.png";

	/**
	 * @param x
	 * @param delay
	 */
	public Basic_shooter(float x, float delay) {
		super(shooter_image, x, basic_Shooter_y, delay);
		// TODO Auto-generated constructor stub
	}
}