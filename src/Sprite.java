import org.newdawn.slick.Input;

import org.newdawn.slick.SlickException;

import utilities.BoundingBox;

import java.util.Random;

import org.newdawn.slick.Image;

/**
 * @author parnak Handles all the Sprites that need to be set according to the
 *         specification.
 * 
 *
 */
public class Sprite {

	private Image image = null;
	private String imagename;
	private float x;
	private float y;
	private float staticx;
	private BoundingBox bbox;
	private float delay;
	private static int min = 48;
	private static int max = 464;

	private Random rand = new Random();
	private final int num = rand.nextInt((max - min + 1) + min);
	private boolean shootflag;
	private int rendertime;

	/**
	 * @param imageSrc
	 * @param x
	 * @param y
	 * @param delay
	 */
	public Sprite(String imageSrc, float x, float y, float delay) {

		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		// System.out.println(x);
		this.setImage_name(imageSrc);
		this.setStaticx(x);
		this.setX(x);
		this.setY(y);
		this.setBbox(new BoundingBox(this.getImage(), y, x));
		this.setDelay(delay);

		// Why would the constructor need a path to an image, and a coordinate?
	}

	/**
	 * @param input
	 * @param delta
	 */
	public void update(Input input, int delta) {
		rendertime += delta;
		// How can this one method deal with different types of sprites?
	}

	public void render() {
		image.draw(x, y);
	}

	public Image getImage() {
		return image;
	}

	/**
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	public String getImagename() {
		return imagename;
	}

	/**
	 * @param imagename
	 */
	public void setImage_name(String imagename) {
		this.imagename = imagename;
	}

	public float getX() {
		return x;
	}

	/**
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	public BoundingBox getBbox() {
		return bbox;
	}

	/**
	 * @param bbox
	 */
	public void setBbox(BoundingBox bbox) {
		this.bbox = bbox;
	}

	public float getDelay() {
		return delay;
	}

	/**
	 * @param delay
	 */
	public void setDelay(float delay) {
		this.delay = delay;
	}

	public float getStaticx() {
		return staticx;
	}

	/**
	 * @param staticx
	 */
	public void setStaticx(float staticx) {
		this.staticx = staticx;
	}

	public int getNum() {
		return num;
	}

	public boolean isShootflag() {
		return shootflag;
	}

	/**
	 * @param shootflag
	 */
	public void setShootflag(boolean shootflag) {
		this.shootflag = shootflag;
	}

	public int getRendertime() {
		return rendertime;
	}

	/**
	 * @param rendertime
	 */
	public void setRendertime(int rendertime) {
		this.rendertime = rendertime;
	}

}
