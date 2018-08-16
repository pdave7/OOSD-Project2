
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*Returns a different enemy type according the the name read in the text file that is passed*/
/**
 * @author parnak Handles all the loading of the sprites read in from the loader
 *         file.
 */
public class Loader {
	private static Sprite createSprite(String name, float x, float delay) {
		switch (name) {
		case "BasicEnemy":
			return new Droid(x, delay);
		case "SineEnemy":
			return new Sine_enemy(x, delay);
		case "BasicShooter":
			return new Basic_shooter(x, delay);
		case "Boss":
			return new Boss(x, delay);

		}
		return null;
	}

	/**
	 * @param filename
	 * @return
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
		ArrayList<Sprite> list = new ArrayList<>();

		// Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = null;

			String[] parts;

			// Loop over every line of the file
			while ((line = reader.readLine()) != null) {
				String name;
				float x, delay;
				if (line.contains("#")) {
					continue;
				}
				// Split the line into parts
				parts = line.split(",");
				name = parts[0];
				x = Integer.parseInt(parts[1]);
				delay = Integer.parseInt(parts[2]);

				// Create the sprite
				list.add(createSprite(name, x, delay));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
