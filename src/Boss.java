
/**
 * @author parnak Handles the boss's loading
 */
public class Boss extends Sprite {

	private static int boss_y = -64;

	private static String BossImage = "res/boss.png";

	/**
	 * @param x
	 * @param delay_value
	 */
	public Boss(float x, float delay_value) {
		super(BossImage, x, boss_y, delay_value);
		// TODO Auto-generated constructor stub
	}

}
