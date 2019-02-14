package elementalist_mod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;

import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import elementalist_mod.ElementalistMod;
import elementalist_mod.Util;

public class SpellCastEffect extends AbstractGameEffect {
	//private ArrayList<Texture> imgs = null;
	public Texture img = null;
	private static final int W = 256;
	private static final float DUR = 0.5F;
	private static boolean alternator = true;
	private boolean flipped = false;
	private Hitbox nodeHb;
	private float offsetX;
	private float offsetY;
	private boolean canRotate = true;
	public boolean isSudden = false;
	private static HashMap<String, Texture> textureMap = new HashMap<String, Texture>(); 
	
	static {
		addTexture("circle", "circle_512.png");
		addTexture("circle fancy", "circle_fancy_512.png");
		addTexture("cross", "cross_512.png");
		addTexture("flowery text circle", "flowery_text_circle_512.png");
		addTexture("indignation circle", "flowery_text_circle_indignation_512.png");
		addTexture("flowery text circle", "flowery_text_circle2_512.png");
		addTexture("lorem ipsum circle simple", "loremipsum_512.png");
		addTexture("lorem ipsum circle complex", "fuck_you_im_lorem_ipsum_bitch_circle_512.png");
		addTexture("lorem ipsum circle runic", "loremipsum_runic512.png");
		addTexture("lorem ipsum circle runic 2", "loremipsum_runic2_512.png");
		addTexture("circle innards triangle", "magic_circle_1.png");
		addTexture("circle innards square", "magic_circle_2.png");
		addTexture("circle innards pentagon", "magic_circle_3.png");
		addTexture("circle innards hexagon", "magic_circle_4.png");
		addTexture("circle innards textured circle", "magic_circle_5.png");
		addTexture("circle innards elemental symbols", "magic_circle_6.png");
		addTexture("orokin circle", "orokin_circle_512.png");
		addTexture("pointy circle with script", "pointy_circle_script_512.png");
		addTexture("divine lightning", "divine_lightning_512.png");
		
		//addTexture("", "");
	}

	private static void addTexture(String name, String filename ) {
		textureMap.put(name, ImageMaster.loadImage("img/effects/"+filename));
	}
	

	public SpellCastEffect(String spell, float scale) {
		Hitbox hitbox = createHitbox();
		init(spell, hitbox, scale);
	}

	public SpellCastEffect(String spell, Hitbox hb) {
		init(spell, hb, scale);
	}
	
	public SpellCastEffect(String spell, float scale, boolean canRotate) {
		Hitbox hitbox = createHitbox();
		this.canRotate = canRotate;
		init(spell, hitbox, scale);
	}
	
	private Hitbox createHitbox() {
		int xSum = 0;
		int ySum = 0;
		int count = 0;
		for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
			xSum += monster.hb.cX;
			ySum += monster.hb.cY;
			count++;
		}
		Hitbox hitbox = new Hitbox(1, 1);
		hitbox.cX = xSum/count;
		hitbox.cY = ySum/count;
		return hitbox;
	}

	public void setDuration(float duration) {
		this.startingDuration = duration;
		this.duration = duration;
	}

	private void init(String spell, Hitbox hb, float scale) {
		ElementalistMod.log("SpellCastEffect.init("+spell+", "+hb+", "+scale+")");
		if(textureMap.containsKey(spell)) {
			this.img = textureMap.get(spell);
			ElementalistMod.log(spell);
		}else {
			switch(spell) {
			case("Heavensfall"):
				ElementalistMod.log("indignation circle");
				this.img = textureMap.get("indignation circle");
				break;
				default:
					ElementalistMod.log("circle");
					this.img = textureMap.get("circle");
					
			}
		}
		
		
		this.nodeHb = hb;

		this.startingDuration = 5F;
		this.duration = 5F;

		this.scale = scale * Settings.scale; //(MathUtils.random(0.9F, 1.3F) * Settings.scale);
		this.rotation = (canRotate ? MathUtils.random(-30.0F, 30.0F) : 0);
		this.offsetX = 0f;//(MathUtils.random(0.0F, 8.0F) * Settings.scale);
		this.offsetY = 0f;//(MathUtils.random(-3.0F, 14.0F) * Settings.scale);
		alternator = !alternator;
		this.flipped = alternator;
		if (!alternator) {
			this.offsetX = (-this.offsetX);
		}
		this.color = new Color(1f, 1f, 1f, 1.0F);
		this.color = this.color.cpy();
	}

	public void update() {
		this.color.a = getAlpha(duration, startingDuration);
		this.color.r = this.color.a;
		this.color.g = this.color.a;
		this.color.b = this.color.a;
		if(canRotate) {
			this.rotation += Gdx.graphics.getDeltaTime() * (this.flipped? -1 : 1);
		}
		this.duration -= Gdx.graphics.getDeltaTime();
		if (this.duration < 0.0F) {
			this.isDone = true;
		}
	}
	
	private float getAlpha(float duration, float startingDuration) {
		float a = (this.duration / this.startingDuration);
		float b = 10*(this.startingDuration-this.duration) / this.startingDuration; 
		if(isSudden) {
			return a;
		}else {
			return Math.min(a, b);
		}
	}

	public void render(SpriteBatch sb, float s) {
		//ElementalistMod.log("SpellCastEffect.render(SpriteBatch, "+s+")");
		sb.setColor(this.color);
		int w = 512;
		if (this.img != null) {
			Util.setBlending(sb, "linear dodge");
			//drawlog(this.img+"", (this.nodeHb.cX - w/2 + this.offsetX)+"", (this.nodeHb.cY - w/2 + this.offsetY)+"", s * this.scale+"",
				//this.rotation+"", this.flipped+"");
			sb.draw(this.img, this.nodeHb.cX - w/2 + this.offsetX, this.nodeHb.cY - w/2 + this.offsetY, w/2, w/2, w, w, s * this.scale, s * this.scale,
				this.rotation, 0, 0, w, w, this.flipped, false);
			Util.setBlending(sb, "normal");
		}
	}
	
	public void drawlog(String s1, String s2, String s3, String s4, String s5, String s6) {
		//ElementalistMod.log(s1+", "+s2+", "+s3+", "+s4+", "+s5+", "+s6);
	}

	public void render(SpriteBatch sb) {
		//ElementalistMod.log("SpellCastEffect.render(SpriteBatch)");
		render(sb, 1);
	}

	public void dispose() {
		//Not really necessary since the textures are all held static.
		
		/*for (Texture t : this.imgs) {
			t.dispose();
		}*/
	}
}