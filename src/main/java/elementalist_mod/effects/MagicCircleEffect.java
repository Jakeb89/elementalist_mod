package elementalist_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import elementalist_mod.ElementalistMod;

public class MagicCircleEffect extends AbstractGameEffect {
	private static final float X_RADIUS = 320F * Settings.scale;
	private static final float Y_RADIUS = 320F * Settings.scale;
	private boolean flashedBorder = true;
	private Vector2 v = new Vector2(0.0F, 0.0F);
	private float x;
	private float y;
	

    public static final Texture[] magic_circle = new Texture[ElementalistMod.MAGIC_CIRCLE.length];
    
    static {
    	for(int i=0; i<magic_circle.length; i++) {
    		magic_circle[i] = ImageMaster.loadImage(ElementalistMod.MAGIC_CIRCLE[i]);
    	}
    }

	public MagicCircleEffect(float x, float y) {
		ElementalistMod.log("MagicCircleEffect(...)");
		this.duration = 0.5F;
		this.x = x;
		this.y = y;
		this.renderBehind = false;
	}

	public void update() {
		ElementalistMod.log("MagicCircleEffect.update()");
		
		if (this.flashedBorder) {
			// CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);
			this.flashedBorder = false;
			//AbstractDungeon.effectsQueue.add(new BorderFlashEffect(new Color(1.0F, 1.0F, 0.1F, 1.0F)));
			//AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.4F, 0.1F, 1.0F)));
		}
		float tmp = Interpolation.fade.apply(-209.0F, 30.0F, this.duration / 0.5F) * 0.017453292F;
		this.v.x = (MathUtils.cos(tmp) * X_RADIUS);
		this.v.y = (-MathUtils.sin(tmp) * Y_RADIUS);

		AbstractDungeon.effectsQueue.add(new MagicCirclePartEffect(this.x, this.y));
		
		
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration < 0.0F) {
			this.isDone = true;
		}
	}

	public void dispose() {
	}

	@Override
	public void render(SpriteBatch arg0) {
		// TODO Auto-generated method stub
		
	}
}
