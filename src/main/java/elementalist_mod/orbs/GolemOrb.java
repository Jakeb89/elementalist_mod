package elementalist_mod.orbs;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.Util;
import elementalist_mod.cards.uncommon.Concentricity;
import elementalist_mod.powers.AstralFormPower;
import elementalist_mod.powers.ConfluencePower;
import elementalist_mod.relics.MagusStaff;

public class GolemOrb extends AbstractOrb {
	private float vfxTimer = 1.0F;
	private float vfxIntervalMin = 0.2F;
	private float vfxIntervalMax = 0.7F;
	private static final float ORB_WAVY_DIST = 0.04F;
	private static final float PI_4 = 12.566371F;

	protected boolean showChannelValue = true;

	public int amount = 0;
	public boolean shattering = false;

	public String[] descriptions;
	public Element element = null;
	private Texture img2, img3, img4, img5, imgExtra, imgBevel;
	public float rotation = 0f;
	public float particleDelta = 0f;
	public ArrayList<FloatPair> starPosition = new ArrayList<FloatPair>();
	public ArrayList<FloatPair> starTarget = new ArrayList<FloatPair>();
	public static Texture orbTexture = null;
	private float highlightScale = 0;
	public boolean isHovered = false;
	static Hitbox mouseHitbox = new Hitbox(InputHelper.mX, InputHelper.mY, 1, 1);

	static {
		if (orbTexture == null) {
			orbTexture = ImageMaster.loadImage("img/orbs/simple_hexagon.png");
		}
	}

	public GolemOrb() {
		this.ID = "elementalist:golemOrb";

		this.channelAnimTimer = 0.5F;
		this.scale = 1f;

		updateDescription();
	}

	public void onStartOfTurn() {
		// this.decrementAmount();
	}

	public void decrementAmount() {
		this.amount--;
		if (this.amount < 0)
			this.amount = 0;
		// checkShatter();
	}

	public void changeAmount(int delta) {
		this.amount += delta;
		if (this.amount < 0)
			this.amount = 0;
	}

	@Override
	public void onEvoke() {
		return;
	}

	@Override
	public void triggerEvokeAnimation() {
		CardCrawlGame.sound.play("DUNGEON_TRANSITION", 0.1F);
		AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
	}

	@Override
	public void updateDescription() {
		applyFocus();
		
		this.description = "A simple golem.";
	}

	@Override
	public void applyFocus() {
		AbstractPower power = AbstractDungeon.player.getPower("Focus");
		if (power == null) {
			return;
		}

		// this.timer = Math.max(0, this.baseTimer + power.amount - this.timeElapsed);
	}

	public void activateEffect() {
		/*
		 * float speedTime = 0.6F / AbstractDungeon.player.orbs.size(); if
		 * (Settings.FAST_MODE) { speedTime = 0.0F; } AbstractDungeon.effectList.add(new
		 * OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA)); //
		 * AbstractDungeon.actionManager.addToBottom(new VFXAction(new //
		 * OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), speedTime));
		 * 
		 */
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
	}

	@Override
	public void render(SpriteBatch sb) {
		this.scale = Settings.scale;
		this.updateAnimation();

		sb.setColor(Color.WHITE.cpy());
		Util.setBlending(sb, "normal");

		sb.draw(orbTexture, this.cX - 64.0F + this.bobEffect.y / 4.0F, this.cY - 64.0F + this.bobEffect.y / 4.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, rotation, 0,
			0, 128, 128, false, false);

		rotation += 0.5f;

		renderText(sb);
		this.hb.render(sb);

		particleDelta += 0.1f;
	}

	@Override
	public void updateAnimation() {
		// super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 45.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
			AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
	}

	@Override
	protected void renderText(SpriteBatch sb) {

		Color textColor, elementColor = Color.WHITE.cpy();

		if (amount == 0)
			textColor = Color.GRAY.cpy();
		// else if(amount > 12) textColor = Color.BLUE.cpy();
		else {
			textColor = Color.WHITE.cpy();
			textColor.lerp(elementColor, amount / 12f);
		}

		if (this.showChannelValue && !this.showEvokeValue) {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.amount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
				textColor, this.fontScale);
		}
		/*
		 * // Render an upgraded green + on the other side, for upgraded replicas only.
		 * if (this.upgraded) { FontHelper.renderFontCentered(sb,
		 * FontHelper.cardEnergyFont_L, "+", this.cX - NUM_X_OFFSET, this.cY +
		 * this.bobEffect.y / 2.0F + NUM_Y_OFFSET, Color.GREEN.cpy(), this.fontScale); }
		 */
	}

	private Color getElementColor(Element element2) {
		switch (element2) {
		case FIRE:
			return Color.RED.cpy();
		case EARTH:
			return Color.YELLOW.cpy();
		case WATER:
			return Color.BLUE.cpy();
		case AIR:
			return Color.GREEN.cpy();
		}
		return null;
	}

	@Override
	public AbstractOrb makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}
}