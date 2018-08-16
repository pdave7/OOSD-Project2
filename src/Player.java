import java.util.ArrayList;

import org.newdawn.slick.Input;

import utilities.BoundingBox;

/**
 * @author parnak Handles what to do with the player using the inputs by the
 *         user.
 */
public class Player extends Sprite {

	Input input;

	public static String playerimage = "res/spaceship.png";
	public static String player_lives_image = "res/lives.png";

	private float playerXpos;
	private float playerYpos;
	private static float speed = 0.5f;
	private static float divide_width = 2;
	private static int ScreenStartPos = 0;
	private static int offsetXlaser = 7;
	private int lives;
	private int time_to_shoot = 0;
	private boolean flag = true;

	/**
	 * @param x
	 * @param y
	 */
	public Player(float x, float y) {
		super(playerimage, x, y, 10);
	}

	/*
	 * Updating the players Xpos and Ypos when the different keys are hits and also
	 * updates the lasers position when the Space key is hit to align with the
	 * player and also updating its bounding box so to kill the player when it
	 * intersects with the droid bounding box
	 */

	/**
	 * @param input
	 * @param delta
	 * @param lasers
	 * @param time
	 */
	public void update(Input input, int delta, ArrayList<Laser> lasers, int time) {

		if (input.isKeyDown(Input.KEY_LEFT)) {
			playerXpos = this.getX() - delta * speed;
			this.setX(playerXpos);
			if (super.getX() < ScreenStartPos) {
				super.setX(this.getX() + delta * speed);
			}
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerXpos = this.getX() + delta * speed;
			this.setX(playerXpos);
			if (super.getX() + super.getImage().getWidth() > App.SCREEN_WIDTH) {
				super.setX(this.getX() - delta * speed);
			}
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			playerYpos = this.getY() - delta * speed;
			this.setY(playerYpos);
			if (super.getY() < ScreenStartPos) {
				this.setY(this.getY() + delta * speed);
			}

		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			playerYpos = this.getY() + delta * speed;
			this.setY(playerYpos);
			if (super.getY() + super.getImage().getHeight() > App.SCREEN_HEIGHT) {
				this.setY(this.getY() - delta * speed);
			}
		}
		/*
		 * If the flag is true the player is allowed to fire, which only becomes true
		 * according to the time that is pased on in the update method. If the player
		 * has a powerup a shorter time is passed or else the normal time is passed. The
		 * timer only allows the player to shoot every the timer has passed.
		 */
		if (flag) {
			if (input.isKeyDown(Input.KEY_SPACE)) {
				Laser dot = new Laser(this.getX() + super.getImage().getWidth() / divide_width - offsetXlaser,
						this.getY());
				lasers.add(dot);
				render_shot_time(delta, time);
			}

		} else {
			render_shot_time(delta, time);
		}

		BoundingBox bbox = this.getBbox();
		bbox.setTop(this.getY());
		bbox.setLeft(this.getX());

	}

	/*
	 * Start the timer and the flag becomes true once the timer has passed that lets
	 * the laser to be fired
	 */
	/**
	 * @param delta
	 * @param time
	 */
	public void render_shot_time(int delta, int time) {
		time_to_shoot += delta;
		flag = false;

		if (time_to_shoot > time) {
			flag = true;
			time_to_shoot = 0;
		}
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

}
