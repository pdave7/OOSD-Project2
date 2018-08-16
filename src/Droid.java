
/**
 * @author parnak Handles the basicenemy loading
 */
public class Droid extends Sprite {

	private static float Droid_Y = -64;
	private static String type = "BasicEnemy";
	private static String droidImage = "res/basic-enemy.png";

	/**
	 * @param x
	 * @param delay_value
	 */
	public Droid(float x, float delay_value) {
		super(droidImage, x, Droid_Y, delay_value);
		// TODO Auto-generated constructor stub
	}

	public static String getType() {
		return type;
	}

	public static void setType(String type) {
		Droid.type = type;
	}

}
