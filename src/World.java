import org.newdawn.slick.GameContainer;
import java.util.Random;
import org.newdawn.slick.Input;
import java.util.ArrayList;
import utilities.BoundingBox;
import java.lang.Math;

/**
 * @author Parnak.Niranjan.Dave
 * @Student ID: 695184 The game is extended from project 1.
 *
 *          Handles all the movement of all the enemies and also handles all of
 *          the game functionality according to the specifications.
 */

public class World {

	private static final String backgroundImage = "res/space.png";
	private static final String basic_enemy_image = "res/basic-enemy.png";
	private static final String sine_enemy_image = "res/sine-enemy.png";
	private static final String basic_shooter_image = "res/basic-shooter.png";
	private static final String boss_image = "res/boss.png";
	private static final String loader_file = "res/waves.txt";

	private int playerXpos = 480;
	private int playerYpos = 688;
	private int time = 0;
	private int rendertime = 0;
	private int hit = 0;
	private int playerLivesXpos = 20;
	private int playerLivesYpos = 696;

	private boolean flag = false;

	Random rand = new Random();

	private static int num;
	private static int num2;
	private static int A = 96;
	private static int T = 1500;
	private static int playerliveXpos = 40;
	private static int gamespeedup = 5;
	private static int game_start_shield = 3000;
	private static int shield_offset_x = 47;
	private static int shield_offset_y = 50;
	private static int two = 2;
	private static int zero = 0;
	private static int basic_shooter_x_offset = 5;
	private static int basic_shooter_y_offset = 23;
	private static int boss_start_y = 72;
	private static int bosswaittime1 = 5000;
	private static int bosswaittime2 = 2000;
	private static int bossrand_1 = 896;
	private static int bossrand_2 = 128;
	private static int one = 1;
	private static int bossfiretime = 3000;
	private static int bossfireratetime = 200;
	private static int boss_total_shots = 4;
	private static int boss_shot1_x_offset = 97;
	private static int boss_shot2_x_offset = 74;
	private static int boss_shot3_x_offset = 194;
	private static int boss_shot4_x_offset = 171;
	private static int boss_shot4_y_offset = 100;
	private static int drop_chance = 19;
	private static int basic_enemy_score = 50;
	private static int sine_enemy_score = 100;
	private static int basic_shooter_score = 200;
	private static int boss_score = 5000;
	private static int boss_hits = 60;
	private static int powerups_timer = 5000;
	private static int death_shield_timer = 3000;
	private static int shooter_timer = 3500;

	private static double PI = Math.PI;

	private static float basic_enemy_speed = 0.2f;
	private static float sine_enemy_speed = 0.15f;
	private static float basic_shooter_speed = 0.2f;
	private static float boss_ymov1 = 0.05f;
	private static float boss_xmov1 = 0.2f;
	private static float boss_xmov2 = 0.1f;

	/** score, in integer to draw on screen in App class */
	public int score = 0;

	private int bosstime = 0;
	private int bossMX2 = 0;
	private int bossMX3 = 0;
	private int bossMX4 = 0;
	private int Shot_powerup_timer = 0;
	private int fastspeedtime = 150;
	private int normalspeedtime = 350;

	private boolean bossM1 = true;
	private boolean bossM2 = false;
	private boolean bossM3 = false;
	private boolean repeat = true;
	private boolean repeat2 = true;
	private boolean render_boss = true;
	private boolean boss_fire = false;
	private boolean startshield = false;
	private boolean poweruptime = false;

	private ArrayList<Laser> lasers;
	private ArrayList<EnemyLaser> Elaser;
	private ArrayList<ShieldPowerup> powerupS;
	private ArrayList<SpeedPowerup> speedpower;
	private Shield shield;
	private ArrayList<Sprite> basic_enemy_sprites = new ArrayList<Sprite>();
	private Player renderplayer;
	private Background background;
	private ArrayList<PlayerLives> lives;

	/* Initialising all the Sprite in the game */
	public World() {

		this.basic_enemy_sprites = Loader.loadSprites(loader_file);
		this.lasers = new ArrayList<Laser>();
		this.Elaser = new ArrayList<EnemyLaser>();
		this.lives = new ArrayList<PlayerLives>();
		this.powerupS = new ArrayList<ShieldPowerup>();
		this.speedpower = new ArrayList<SpeedPowerup>();
		background = new Background(backgroundImage);
		renderplayer = new Player(playerXpos, playerYpos);
		shield = new Shield(renderplayer.getX(), renderplayer.getY());

		for (int j = 0; j < 3; j++) {
			lives.add(j, new PlayerLives(playerLivesXpos + (playerliveXpos * j), playerLivesYpos));
		}

	}

	/* Updating the sprite positions according to what happens in the game. */
	/**
	 * @param gc
	 * @param input
	 * @param delta
	 */
	public void update(GameContainer gc, Input input, int delta) {

		if (input.isKeyDown(Input.KEY_S)) {
			delta = delta * gamespeedup;
		}

		render_time(delta);
		/* Start off with the shield for 3000ms */
		if (rendertime < game_start_shield) {
			death(delta);
			flag = true;
		}

		/*
		 * Update the speed of laser when player makes contact with the speedPowerup
		 * drop
		 */
		background.update(input, delta);
		if (poweruptime) {
			renderplayer.update(input, delta, lasers, fastspeedtime);
		} else {
			renderplayer.update(input, delta, lasers, normalspeedtime);

		}

		/* Setting the shield with offset around the player */
		shield.setX(renderplayer.getX() - renderplayer.getImage().getWidth() + shield_offset_x);
		shield.setY(renderplayer.getY() - renderplayer.getImage().getHeight() + shield_offset_y);

		/*
		 * Reads in at what enemy type the file reads and behaves according to the
		 * specification
		 */
		for (int k = 0; k < basic_enemy_sprites.size(); k++) {

			if (rendertime > basic_enemy_sprites.get(k).getDelay()
					&& (basic_enemy_sprites.get(k).getImagename() == basic_enemy_image)) {
				basic_enemy_sprites.get(k).setY(basic_enemy_sprites.get(k).getY() + delta * (basic_enemy_speed));
			}
			/*
			 * Moving in sine wave patterns using the PI and SINE functions imported from
			 * the Java.math library
			 */
			if (rendertime > basic_enemy_sprites.get(k).getDelay()
					&& ((basic_enemy_sprites.get(k).getImagename() == sine_enemy_image))) {
				basic_enemy_sprites.get(k).setY(basic_enemy_sprites.get(k).getY() + delta * (sine_enemy_speed));

				basic_enemy_sprites.get(k).setX((float) (basic_enemy_sprites.get(k).getStaticx()
						+ (A * Math.sin((two * PI / T * (rendertime - basic_enemy_sprites.get(k).getDelay()))))));
			}
			/* The basic shooter enemy renders in with the delay and shoots every 3500ms. */

			if (rendertime > basic_enemy_sprites.get(k).getDelay()
					&& (basic_enemy_sprites.get(k).getImagename() == basic_shooter_image)) {

				if (rendertime > basic_enemy_sprites.get(k).getDelay()) {

				}
				/*
				 * Sets the shoot flag to true so it can start a counter of 3500ms and shot when
				 * it hits it and resets it
				 */
				if (basic_enemy_sprites.get(k).getY() < basic_enemy_sprites.get(k).getNum()) {
					basic_enemy_sprites.get(k).setY(basic_enemy_sprites.get(k).getY() + delta * basic_shooter_speed);
					basic_enemy_sprites.get(k).setShootflag(true);

				} else {
					basic_enemy_sprites.get(k).update(input, delta);
					if (basic_enemy_sprites.get(k).isShootflag()
							|| basic_enemy_sprites.get(k).getRendertime() > shooter_timer) {
						if (basic_enemy_sprites.get(k).getRendertime() > shooter_timer) {
							basic_enemy_sprites.get(k).setRendertime(zero);
						} else {
							basic_enemy_sprites.get(k).update(input, delta);
						}
						EnemyLaser dot = new EnemyLaser(basic_enemy_sprites.get(k).getX() + basic_shooter_x_offset,
								basic_enemy_sprites.get(k).getY() + basic_shooter_y_offset, zero);
						Elaser.add(dot);
						/*
						 * Uses the flag to know when the enemy should shoot, Sets the boolean flag to
						 * false and then shoots after every 3500ms
						 */
						basic_enemy_sprites.get(k).setShootflag(false);
					}

				}

			}
			/* Reads in the boss and starts four different timers for each step. */
			if (rendertime > basic_enemy_sprites.get(k).getDelay()
					&& (basic_enemy_sprites.get(k).getImagename() == boss_image)) {
				if (basic_enemy_sprites.get(k).getY() < boss_start_y) {
					basic_enemy_sprites.get(k).setY(basic_enemy_sprites.get(k).getY() + delta * (boss_ymov1));
				} else {
					/*
					 * Loops through the four steps according the boolean flag to start the timer
					 * and choose a random X value and moves towards it at a speed of using the
					 * given specified value in spec.
					 */
					if (render_boss) {
						bossMX3 = zero;
						bossM3 = false;
						boss_fire = false;
						render_Boss(delta);
					}
					if (bosstime > bosswaittime1 && bossM1) {
						render_boss = false;
						/*
						 * Sets up a repeat flag that sets up a random num value to move towards and
						 * sets it to false to stop it from creating values again and again.
						 */
						if (repeat) {
							num = rand.nextInt((bossrand_1 - bossrand_2 + one) + bossrand_2);
							repeat = false;

						}
						if (basic_enemy_sprites.get(k).getX() < num) {
							basic_enemy_sprites.get(k).setX(basic_enemy_sprites.get(k).getX() + boss_xmov1 * delta);

						}
						if (basic_enemy_sprites.get(k).getX() > num) {
							basic_enemy_sprites.get(k).setX(basic_enemy_sprites.get(k).getX() - boss_xmov1 * delta);

						}
						/*
						 * Once the value is equal to the rand num value. Using the .ceil value rounds
						 * it to the next int so that there is no floating to num confusion and the
						 * condition becomes true. Sets the the boss behaviour movement 2 flags to true
						 * and sets the step one to false
						 */
						if (Math.ceil(basic_enemy_sprites.get(k).getX()) == num) {
							bosstime = zero;
							repeat = true;
							bossM2 = true;
							bossM1 = false;

						}

					}
					if (bossM2) {
						render_Boss_M2(delta);
					}
					/*
					 * Waits for 2000ms and sets the thrid movement behaviour to true so the boss
					 * acts according to the specified behaviour and follows the same steps as step
					 * 1 and moves towards it
					 */
					if (bossMX2 > bosswaittime2) {
						bossM2 = false;
						/*
						 * Start the timer for the boss's third movement according to the specification
						 */
						if (repeat2) {
							bossM3 = true;
							num2 = rand.nextInt((bossrand_1 - bossrand_2 + one) + bossrand_2);
							repeat2 = false;
						}
						if (basic_enemy_sprites.get(k).getX() < num2) {
							basic_enemy_sprites.get(k).setX(basic_enemy_sprites.get(k).getX() + boss_xmov2 * delta);

						}
						if (basic_enemy_sprites.get(k).getX() > num2) {
							basic_enemy_sprites.get(k).setX(basic_enemy_sprites.get(k).getX() - boss_xmov2 * delta);

						}

						if (Math.ceil(basic_enemy_sprites.get(k).getX()) == num2) {
							repeat2 = true;
							bossMX2 = zero;
							bossM1 = true;
						}
					}

					/*
					 * Starts the third movement and fires for 3000ms every 200ms and sets it to
					 * false once the time of 3000ms is up in order to repeat the movement.
					 */
					if (bossM3) {
						boss_fire = true;
						render_Boss_M3(delta);
					}

					if (boss_fire) {

						if (bossMX3 < bossfiretime) {

							render_Boss_M4(delta);
							/*
							 * Fires the 4 laser shots with the offset given every 200ms for the total of
							 * 3000ms
							 */
							if (bossMX4 > bossfireratetime) {

								bossMX4 = zero;

								for (int i = 0; i < boss_total_shots; i++) {
									if (i == 0) {
										EnemyLaser dot = new EnemyLaser(
												basic_enemy_sprites.get(k).getX() + boss_shot1_x_offset,
												basic_enemy_sprites.get(k).getY() + boss_shot4_y_offset, 0);
										Elaser.add(dot);
									}
									if (i == 1) {
										EnemyLaser dot = new EnemyLaser(
												basic_enemy_sprites.get(k).getX() + boss_shot2_x_offset,
												basic_enemy_sprites.get(k).getY() + boss_shot4_y_offset, 0);
										Elaser.add(dot);
									}
									if (i == 2) {
										EnemyLaser dot = new EnemyLaser(
												basic_enemy_sprites.get(k).getX() + boss_shot3_x_offset,
												basic_enemy_sprites.get(k).getY() + boss_shot4_y_offset, 0);
										Elaser.add(dot);
									}
									if (i == 3) {
										EnemyLaser dot = new EnemyLaser(
												basic_enemy_sprites.get(k).getX() + boss_shot4_x_offset,
												basic_enemy_sprites.get(k).getY() + boss_shot4_y_offset, 0);
										Elaser.add(dot);
									}

								}

							}
							/*
							 * Repeat the step 1 and loops through it using the flag value until the boss is
							 * killed
							 */
						} else {
							bossM3 = false;
							boss_fire = false;
							render_boss = true;

						}

					}

				}

			}

		}

		ArrayList<Droid> renemies = new ArrayList<>();
		ArrayList<Sine_enemy> rSenemies = new ArrayList<>();
		ArrayList<Basic_shooter> rBshooter = new ArrayList<>();
		ArrayList<Boss> rBoss = new ArrayList<>();
		ArrayList<Shield> rshield = new ArrayList<>();
		ArrayList<Laser> rlasers = new ArrayList<>();
		ArrayList<ShieldPowerup> rShield = new ArrayList<>();
		ArrayList<SpeedPowerup> rSpeed = new ArrayList<>();

		/*
		 * Steps through the update method for all the sprites that need to move a
		 * certain speeds using the update method in their respective classes.
		 */
		for (Laser laser : this.lasers) {
			laser.update(input, delta);
		}

		for (EnemyLaser elaser : this.Elaser) {
			elaser.update(input, delta);
		}

		for (ShieldPowerup shield : this.powerupS) {
			shield.update(input, delta);
		}

		for (SpeedPowerup speed : this.speedpower) {
			speed.update(input, delta);
		}

		/*
		 * 
		 * Removing the lasers and all different enemiy sprites when they make contact
		 * with each other using the bounding box intersect method. It also kills the
		 * game once the player touches the enemy.
		 * 
		 * 
		 */

		BoundingBox pbox = renderplayer.getBbox();

		for (Sprite droid : this.basic_enemy_sprites) {
			BoundingBox ebox = droid.getBbox();
			ebox.setTop(droid.getY());
			ebox.setLeft(droid.getX());
			for (Laser laser : this.lasers) {
				BoundingBox lbox = laser.getBbox();
				if (lbox.intersects(ebox)) {
					/*
					 * The powerup drops both have a 5% chance when the enemy is removed to appear
					 * in the game. Also the score is update for different types of enemy when they
					 * die. Enemy is only shot once its rendered on the screen. Checking on 5
					 * percent chance for both drops when an each enemy dies.
					 */
					if (droid.getY() > zero && (droid.getImagename() == basic_enemy_image)) {
						score += basic_enemy_score;
						if (1 == rand.nextInt(drop_chance)) {
							ShieldPowerup addshield = new ShieldPowerup(droid.getX(), droid.getY(), 0);
							powerupS.add(addshield);
						}
						if (1 == rand.nextInt(drop_chance)) {
							SpeedPowerup addspeed = new SpeedPowerup(droid.getX(), droid.getY(), 0);
							speedpower.add(addspeed);
						}
						rlasers.add(laser);
						renemies.add((Droid) droid);

					}

					if (droid.getY() > zero && (droid.getImagename() == sine_enemy_image)) {
						score += sine_enemy_score;

						rlasers.add(laser);
						if (1 == rand.nextInt(drop_chance)) {
							ShieldPowerup addshield = new ShieldPowerup(droid.getX(), droid.getY(), 0);
							powerupS.add(addshield);
						}
						if (1 == rand.nextInt(drop_chance)) {
							SpeedPowerup addspeed = new SpeedPowerup(droid.getX(), droid.getY(), 0);
							speedpower.add(addspeed);
						}

						rSenemies.add((Sine_enemy) droid);

					}

					if (droid.getY() > zero && (droid.getImagename() == basic_shooter_image)) {
						score += basic_shooter_score;

						if (1 == rand.nextInt(drop_chance)) {
							ShieldPowerup addshield = new ShieldPowerup(droid.getX(), droid.getY(), 0);
							powerupS.add(addshield);
						}
						if (1 == rand.nextInt(drop_chance)) {
							SpeedPowerup addspeed = new SpeedPowerup(droid.getX(), droid.getY(), 0);
							speedpower.add(addspeed);
						}
						rlasers.add(laser);
						rBshooter.add((Basic_shooter) droid);

					}

					if (droid.getY() > zero && (droid.getImagename() == boss_image)) {
						hit += 1;
						rlasers.add(laser);
						if (hit == boss_hits) {
							if (1 == rand.nextInt(drop_chance)) {
								ShieldPowerup addshield = new ShieldPowerup(droid.getX(), droid.getY(), 0);
								powerupS.add(addshield);
							}
							if (1 == rand.nextInt(drop_chance)) {
								SpeedPowerup addspeed = new SpeedPowerup(droid.getX(), droid.getY(), 0);
								speedpower.add(addspeed);
							}
							score += boss_score;
							rBoss.add((Boss) droid);
						}

					}

				}
			}
			/*
			 * Removes the speedpowerup when the speedpowerup boundingBox makes contact with
			 * the player boundingBox.
			 */
			for (SpeedPowerup speed : this.speedpower) {
				BoundingBox speedbox = speed.getBbox();
				if (speedbox.intersects(pbox)) {
					rSpeed.add(speed);
					render_power(delta);
				}
			}
			/*
			 * Removes the players life when either enemy laser or the enemy makes contact
			 * with the player and starts the 5 second timer for the shield to appear around
			 * the player.
			 */
			if (time == zero) {

				for (EnemyLaser lasers : this.Elaser) {
					BoundingBox ELbox = lasers.getBbox();
					if (ELbox.intersects(pbox)) {
						death(delta);
						flag = true;
						this.lives.remove(lives.size() - 1);
						if (lives.size() == zero) {
							gc.exit();
						}
					}
				}
				/*
				 * If the shields boundbox makes contact with the player, it starts a timer for
				 * 5 seconds for player to have a shield around it.
				 */
				for (ShieldPowerup shield : this.powerupS) {
					BoundingBox sbox = shield.getBbox();
					if (sbox.intersects(pbox)) {
						startshield = true;
						rShield.add(shield);
						flag = true;
						death(delta);
					}
				}
			}

			if (time == zero) {

				if (pbox.intersects(ebox)) {
					flag = true;
					this.lives.remove(lives.size() - 1);
					death(delta);
					if (lives.size() == zero) {
						gc.exit();
					}
				}
			}
		}
		/*
		 * Keep updating the time once the timer has begun for death and wont kill the
		 * player for 5 seconds.
		 */
		if (time != zero) {
			death(delta);
		}
		/*
		 * If the poweruptime flag is true it takes action for 5000ms on the player for
		 * shotspeedPowerup
		 */
		if (poweruptime) {
			render_power(delta);
		}

		/*
		 * Loops through the arraylist for each sprite that needs to be removed
		 * according to its specified behaviour.
		 */

		for (int a = 0; a < rshield.size(); a++) {
			rshield.get(a).setX(renderplayer.getX());
			rshield.get(a).setY(renderplayer.getY());
			if (flag == true) {
				rshield.remove(a);
			}
		}

		for (Sprite droid : renemies) {
			this.basic_enemy_sprites.remove(droid);
		}
		for (Sprite senemy : rSenemies) {
			this.basic_enemy_sprites.remove(senemy);
		}
		for (Sprite Bshooter : rBshooter) {
			this.basic_enemy_sprites.remove(Bshooter);

		}
		for (Sprite Boss : rBoss) {
			this.basic_enemy_sprites.remove(Boss);
		}

		for (Laser laser : rlasers) {
			this.lasers.remove(laser);
		}
		for (ShieldPowerup shieldP : rShield) {
			this.powerupS.remove(shieldP);
		}
		for (SpeedPowerup speed : rSpeed) {

			this.speedpower.remove(speed);
		}

	}

	/**
	 * @param delta
	 */
	public void render_power(int delta) {

		Shot_powerup_timer += delta;
		poweruptime = true;

		if (Shot_powerup_timer > powerups_timer) {
			Shot_powerup_timer = zero;
			poweruptime = false;
		}

	}

	/**
	 * @param delta
	 */
	public void death(int delta) {
		time += delta;
		if (!startshield) {
			if (time > death_shield_timer) {
				flag = false;
				time = zero;
			}

		} else {
			if (time > powerups_timer) {
				flag = false;
				time = zero;
				startshield = false;
			}
		}

	}

	/* Four different timers for the boos's movement at each step */
	/**
	 * @param delta
	 */
	public void render_time(int delta) {
		rendertime += delta;
	}

	/**
	 * @param delta
	 */
	public void render_Boss(int delta) {
		bosstime += delta;

	}

	/**
	 * @param delta
	 */
	public void render_Boss_M2(int delta) {
		bossMX2 += delta;

	}

	/**
	 * @param delta
	 */
	public void render_Boss_M3(int delta) {
		bossMX3 += delta;

	}

	/**
	 * @param delta
	 */
	public void render_Boss_M4(int delta) {
		bossMX4 += delta;

	}

	/* Rendering all the sprites using the Sprite class */
	public void render() {

		background.render();

		for (int i = 0; i < basic_enemy_sprites.size(); i++) {
			if (rendertime > basic_enemy_sprites.get(i).getDelay()) {
				basic_enemy_sprites.get(i).render();
			}

		}
		for (int k = 0; k < lives.size(); k++) {
			lives.get(k).render();
		}
		/*
		 * Only render the shield while the flag is true for the shield to appear arond
		 * the player which sets to false after 5 seconds
		 */
		if (flag == true) {
			shield.render();
		}

		for (Laser dot : this.lasers) {
			dot.render();
		}

		for (EnemyLaser dot : this.Elaser) {
			dot.render();
		}

		for (ShieldPowerup shield : this.powerupS) {
			shield.render();
		}
		for (SpeedPowerup speed : this.speedpower) {
			speed.render();
		}
		renderplayer.render();

	}

}
