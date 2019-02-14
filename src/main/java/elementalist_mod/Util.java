package elementalist_mod;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Util {

	public static void setBlending(SpriteBatch sb, String mode) {
		switch (mode) {
		case ("normal"):
			sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			break;
		case ("multiply"):
			sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
			break;
		case ("screen"):
			sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
			break;
		case ("overlay"): // not working
			sb.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_COLOR);
			break;
		case ("color dodge"): // not working
			sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
			break;
		case ("linear dodge"):
			sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
			break;
		}
	}
}
