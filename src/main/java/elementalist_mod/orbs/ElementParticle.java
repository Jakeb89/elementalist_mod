package elementalist_mod.orbs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ElementParticle {
	
	public static Texture[] texture = new Texture[1];
	
	static {
		texture[0] = ImageMaster.loadImage("img/orbs/synergy_particle.png");
	}
	

	public static void draw(SpriteBatch sb, String type, float x, float y, float scale, float rotation) {
		Texture tex = null;
		switch(type) {
			case("synergy"): tex = texture[0]; break;
		}
		sb.draw(tex, x - 48.0F, y - 48.0F, 48.0F, 48.0F,
				96.0F, 96.0F, scale, scale, rotation, 0, 0, 96, 96, false, false);
	}
}
