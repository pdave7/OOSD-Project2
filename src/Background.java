import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author parnak Handles the Background rendering
 */
public class Background {
	private Image[][] images;
	private static int NUMROWS = 3;
	private static int NUMCOLS = 2;
	private static double backgroundspeed = 0.2;
	private float[][] renderXs;
	private float[][] renderYs;
	private static int first_image = 0;

	/**
	 * @param imageName
	 */
	public Background(String imageName) {
		/* Creating 6 images for the background to render */
		this.images = new Image[NUMROWS][];
		this.renderXs = new float[NUMROWS][];
		this.renderYs = new float[NUMROWS][];

		int currX = 0, currY = -1;
		/*
		 * Updating the background images according to their current X and Y positions
		 */
		for (int i = 0; i < images.length; i++) {
			currX = 0;
			images[i] = new Image[NUMCOLS];
			renderXs[i] = new float[NUMCOLS];
			renderYs[i] = new float[NUMCOLS];
			for (int j = 0; j < images[i].length; j++) {
				try {
					images[i][j] = new Image(imageName);
					renderXs[i][j] = currX * images[i][j].getWidth();
					renderYs[i][j] = currY * images[i][j].getHeight();
					currX += 1;
					if (j == images[i].length - 1) {
						currY += 1;
					}

				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/* Updating the background when the image goes past the Screen_Height */

	/**
	 * @param input
	 * @param delta
	 */
	public void update(Input input, int delta) {
		for (int i = 0; i < renderXs.length; i++) {
			for (int j = 0; j < renderYs[first_image].length; j++) {
				renderYs[i][j] += delta * backgroundspeed;
				if (renderYs[i][j] >= App.SCREEN_HEIGHT) {
					for (int k = i; k >= 0; k--) {
						if (k == 0) {
							renderYs[k][j] = renderYs[k + 1][j] - images[i][k].getHeight();
							continue;
						}
						renderYs[k][j] = renderYs[k - 1][j];
					}
				}
			}
		}
	}

	/*
	 * Rendering of the background ocne the positions are updated for the background
	 * X coords
	 */
	public void render() {
		for (int i = 0; i < renderXs.length; i++) {
			for (int j = 0; j < renderYs[first_image].length; j++) {
				images[i][j].draw(renderXs[i][j], renderYs[i][j]);

			}
		}
	}

}
