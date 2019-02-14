package elementalist_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import elementalist_mod.ElementalistMod;

public class MagicCirclePartEffect extends AbstractGameEffect {
	private float x;
	private float y;
	  private float vY;
	private int width = 320;
	private int height = 320;
	

    public static Texture[] magic_circle = new Texture[ElementalistMod.MAGIC_CIRCLE.length];
    
    static {
    	for(int i=0; i<magic_circle.length; i++) {
    		//ElementalistMod.log("Loading texture '"+ElementalistMod.MAGIC_CIRCLE[i]+"'");
    		magic_circle[i] = ImageMaster.loadImage(ElementalistMod.MAGIC_CIRCLE[i]);
    	}
    }

	public MagicCirclePartEffect(float x, float y) {
		ElementalistMod.log("MagicCirclePartEffect(...)");
		this.duration = MathUtils.random(0.5F, 1.0F);
	    this.startingDuration = this.duration;
	    this.x = (x - width / 2 + MathUtils.random(-3.0F, 3.0F) * Settings.scale);
	    this.y = (y - height / 2);
	    this.scale = (Settings.scale * MathUtils.random(1.0F, 3.0F));
	    this.vY = (MathUtils.random(1.0F, 10.0F) * Settings.scale);
	    this.vY *= this.vY;
	    
	    
		this.duration = 0.5F;
		this.x = x;
		this.y = y;
		this.color = Color.WHITE.cpy();
		this.renderBehind = false;
		
	}

	public void update() {
		ElementalistMod.log("MagicCirclePartEffect.update()");
	    this.duration -= Gdx.graphics.getDeltaTime();
	    if (this.duration < 0.0F) {
	      this.isDone = true;
	    }
	    this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.startingDuration);
	    this.y += this.vY * Gdx.graphics.getDeltaTime() * 2.0F;
	}

	@Override
	public void render(SpriteBatch sb) {
	    //sb.setBlendFunction(770, 1);
	    sb.setColor(this.color);

		renderMagicCircle(sb);
		
	    //sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
	    
	    //sb.setBlendFunction(770, 771);
	}
	


	private void renderMagicCircle(SpriteBatch sb) {
		//ElementalistMod.log("MagicCircleEffect.renderMagicCircle(...)");
		//ElementalistMod.log("-> magic_circle.length => "+magic_circle.length);
		//int width = 938;
		float scale = 1f;
		float time = System.nanoTime()/1000f;
		//float drawX = AbstractDungeon.player.drawX + AbstractDungeon.player.animX;
		//float drawY = AbstractDungeon.player.drawY + AbstractDungeon.player.animY + AbstractDungeon.sceneOffsetY;
	    sb.setColor(Color.WHITE.cpy());
		//ElementalistMod.logger.info("-> drawX => "+drawX);
		//ElementalistMod.logger.info("-> drawY => "+drawY);
		for(int i=0; i<magic_circle.length; i++) {
			float rotation = time * (1+i*0.1f) * ((i%2)*2-1);
    		//sb.draw(magic_circle[i], drawX - width/2, drawY - width/2, width/2, width/2, width, width, scale, scale, rotation, 0, 0, width, width, false, false);
			//ElementalistMod.log("-> (x, y) => ("+this.x+", "+this.y+")");
    		sb.draw(magic_circle[i], 
    				this.x, this.y, //screen draw position 
    				160, 160, //rotation origin
    				320, 320, //size of draw 
    				scale, scale, rotation, //scaling and rotation
    				0, 0, //texel space position
    				320, 320, //source size in texels
    				false, false); // horizontal/vertical flip
    	}
	}

	@Override
	public void dispose() {
	}
}
