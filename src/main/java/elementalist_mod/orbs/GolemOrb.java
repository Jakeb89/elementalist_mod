package elementalist_mod.orbs;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.Util;
import elementalist_mod.cards.uncommon.Concentricity;
import elementalist_mod.powers.AstralFormPower;
import elementalist_mod.powers.ConfluencePower;
import elementalist_mod.relics.MagusStaff;

public class GolemOrb extends AbstractOrb {
	public static final String ID = "Elementalist:GolemOrb";

	private float vfxTimer = 1.0F;
	private float vfxIntervalMin = 0.2F;
	private float vfxIntervalMax = 0.7F;

	protected boolean showChannelValue = true;

	public int amount = 0;
	public boolean shattering = false;

	public String[] descriptions;
	public Element element = null;
	public float rotation = 0f;
	public float particleDelta = 0f;
	// public ArrayList<FloatPair> starPosition = new ArrayList<FloatPair>();
	// public ArrayList<FloatPair> starTarget = new ArrayList<FloatPair>();
	public static ArrayList<Texture> octohedron = new ArrayList<Texture>();
	public static ArrayList<Texture> octohedron_frame = new ArrayList<Texture>();
	public static HashMap<Element, Texture> elementSymbol = new HashMap<Element, Texture>();
	public static Texture golem_ring_outer = null;
	public static Texture golem_ring_inner = null;
	public static Texture elemental_slot = null;
	public int elementSlots = 1;
	public ArrayList<Element> elements = new ArrayList<Element>();
	public float octohedronSubimg = 0f;
	public int octWidth = 128;
	public int symbolWidth = 96;

	static {
		if (octohedron.size() == 0) {
			for (int i = 0; i < 25; i++) {
				String number = i + "";
				if (number.length() < 2)
					number = "0" + number;
				octohedron.add(ImageMaster.loadImage("img/orbs/spinning_octohedron/spinning_octahedron" + number + ".png"));
				octohedron_frame.add(ImageMaster.loadImage("img/orbs/spinning_octohedron/spinning_octahedron_frame" + number + ".png"));
			}
			elementSymbol.put(Element.FIRE, ImageMaster.loadImage("img/orbs/elementalist_fireOrb_back5_black.png"));
			elementSymbol.put(Element.WATER, ImageMaster.loadImage("img/orbs/elementalist_waterOrb_back5_black.png"));
			elementSymbol.put(Element.EARTH, ImageMaster.loadImage("img/orbs/elementalist_earthOrb_back5_black.png"));
			elementSymbol.put(Element.AIR, ImageMaster.loadImage("img/orbs/elementalist_airOrb_back5_black.png"));
		}
		golem_ring_outer = ImageMaster.loadImage("img/orbs/golem_ring_outer.png");
		golem_ring_inner = ImageMaster.loadImage("img/orbs/golem_ring_inner.png");
		elemental_slot = ImageMaster.loadImage("img/orbs/elemental_slot.png");
	}

	public GolemOrb() {
		this.channelAnimTimer = 0.5F;
		this.scale = 1f;
		this.name = "Golem Orb";

		this.elementSlots = 1 + (int) (Math.random() * 2);

		updateDescription();
	}

	public void onElementCast(Element element) {
		if (this.elements.size() < this.elementSlots) {
			this.elements.add(element);
		} else {
			this.onEvoke();
			this.elements.remove(0);
			this.elements.add(element);
		}
		updateDescription();
	}

	public void update() {
		this.hb.update();
		if (this.hb.hovered) {
			TipHelper.renderGenericTip(this.tX + 96.0F * Settings.scale, this.tY + 64.0F * Settings.scale, this.name, this.description);
		}
		this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
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

	public int[] getMultidamage(int damage) {
		int[] multidamage = new int[AbstractDungeon.getMonsters().monsters.size()];
		for (int i = 0; i < multidamage.length; i++) {
			multidamage[i] = damage;
		}
		return multidamage;
	}

	@Override
	public void onEndOfTurn() {
		super.onEndOfTurn();
		onPassive();
	}

	public void onPassive() {
		float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
		if (Settings.FAST_MODE) {
			speedTime = 0.0F;
		}

		if (elements.size() == 0)
			return;
		if (elements.size() == 1) {
			switch (elements.get(0)) {
			case FIRE:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("CARD_BURN"));
				AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, getMultidamage(1), DamageType.NORMAL, AttackEffect.FIRE));
				break;
			case WATER:
				ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
				for (AbstractPower power : AbstractDungeon.player.powers) {
					if (power.type == AbstractPower.PowerType.DEBUFF && power.amount > 0) {
						debuffs.add(power);
					}
				}
				if (debuffs.size() > 0) {
					AbstractPower chosenPower = debuffs.get((int) (Math.random() * debuffs.size()));
					AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
			        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_FROST_PASSIVE"));
					AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, chosenPower, 1));
				}
				break;
			case EARTH:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_DARK_CHANNEL"));
				AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 2));
				break;
			case AIR:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_PASSIVE"));
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onEvoke() {
		float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
		if (Settings.FAST_MODE) {
			speedTime = 0.0F;
		}

		if (elements.size() == 0)
			return;
		if (elements.size() == 1) {
			switch (elements.get(0)) {
			case FIRE:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FIRE"));
				AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, getMultidamage(3), DamageType.NORMAL, AttackEffect.FIRE));
				break;
			case WATER:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_FROST_EVOKE"));
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 2), 2));
				break;
			case EARTH:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_DARK_EVOKE"));
				AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 5));
				break;
			case AIR:
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
		        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
				break;
			default:
				break;
			}
		}
	}

	public String getElementComboPassiveDescription() {
		if (elements.size() == 0)
			return "Does nothing.";
		if (elements.size() == 1) {
			switch (elements.get(0)) {
			case FIRE:
				return "Deals 1 damage to all enemies.";
			case WATER:
				return "Lowers the strength or duration of a random debuff by 1.";
			case EARTH:
				return "Gains 2 Block.";
			case AIR:
				return "Draws 1 card.";
			default:
				return "?";
			}
		}
		return "-";
	}

	public String getElementComboEvokeDescription() {
		if (elements.size() == 0)
			return "Does nothing.";
		if (elements.size() == 1) {
			switch (elements.get(0)) {
			case FIRE:
				return "Deals 3 damage to all enemies.";
			case WATER:
				return "Gain Regen 2.";
			case EARTH:
				return "Gains 5 Block.";
			case AIR:
				return "Draws 2 cards.";
			default:
				return "?";
			}
		}
		return "-";
	}

	@Override
	public void triggerEvokeAnimation() {
		CardCrawlGame.sound.play("DUNGEON_TRANSITION", 0.1F);
		AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
	}

	@Override
	public void updateDescription() {
		applyFocus();

		this.description = "A golem orb with " + Util.pluralize(this.elementSlots, "slot") + ".";
		for (int i = 0; i < this.elementSlots; i++) {
			if (i < this.elements.size() && this.elements.get(i) != null) {
				this.description += " NL [" + this.elements.get(i) + "]";
			} else {
				this.description += " NL [ (Empty) ]";
			}
		}
		this.description += " NL ";
		this.description += " NL Passive: " + getElementComboPassiveDescription();
		this.description += " NL Evoke: " + getElementComboEvokeDescription();
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
		Color col = Color.WHITE.cpy();
		col.r = col.g = col.b = 0.25f;

		float octScale = this.scale;
		float symbolScale = this.scale * 0.75f;

		// render golem control rings
		sb.setColor(Color.WHITE.cpy());
		Util.setBlending(sb, "normal");
		sb.draw(golem_ring_outer, this.cX - octWidth / 2 + this.bobEffect.y / 4.0F, this.cY - octWidth / 2 + this.bobEffect.y / 4.0F, octWidth / 2, octWidth / 2, octWidth,
			octWidth, octScale, octScale, rotation / 4, 0, 0, octWidth, octWidth, false, false);
		sb.draw(golem_ring_inner, this.cX - octWidth / 2 + this.bobEffect.y / 4.0F, this.cY - octWidth / 2 + this.bobEffect.y / 4.0F, octWidth / 2, octWidth / 2, octWidth,
			octWidth, octScale, octScale, -rotation / 3, 0, 0, octWidth, octWidth, false, false);

		// render octohedron back face
		sb.setColor(col);
		Util.setBlending(sb, "screen");
		sb.draw(octohedron.get((int) octohedronSubimg), this.cX - octWidth / 2 + this.bobEffect.y / 4.0F, this.cY - octWidth / 2 + this.bobEffect.y / 4.0F, octWidth / 2,
			octWidth / 2, octWidth, octWidth, octScale, octScale, rotation, 0, 0, octWidth, octWidth, false, false);

		// render elemental energies inside octohedron
		sb.setColor(Color.WHITE.cpy());
		Util.setBlending(sb, "screen");

		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);

			float dir = rotation / 6;
			if (elements.size() > 0)
				dir += 360 * i / elements.size();

			float dx = (float) (Math.cos(2 * 3.14 / 360 * dir) * octWidth * (-0.02));
			float dy = (float) (Math.sin(2 * 3.14 / 360 * dir) * octWidth * (-0.02));

			if (element != null && elementSymbol.containsKey(element)) {
				sb.draw(elementSymbol.get(element), this.cX - symbolWidth / 2 + this.bobEffect.y / 4.0F, this.cY - symbolWidth / 2 + this.bobEffect.y / 4.0F, symbolWidth / 2,
					symbolWidth / 2, symbolWidth, symbolWidth, symbolScale, symbolScale, 0, 0, 0, symbolWidth, symbolWidth, false, false);
			}
		}

		// render octohedron front face
		sb.setColor(col);
		Util.setBlending(sb, "screen");
		sb.draw(octohedron.get((int) octohedronSubimg), this.cX - octWidth / 2 + this.bobEffect.y / 4.0F, this.cY - octWidth / 2 + this.bobEffect.y / 4.0F, octWidth / 2,
			octWidth / 2, octWidth, octWidth, octScale, octScale, rotation, 0, 0, octWidth, octWidth, false, false);

		// render octohedron frame
		sb.setColor(Color.WHITE.cpy());
		Util.setBlending(sb, "normal");
		sb.draw(octohedron_frame.get((int) octohedronSubimg), this.cX - octWidth / 2 + this.bobEffect.y / 4.0F, this.cY - octWidth / 2 + this.bobEffect.y / 4.0F, octWidth / 2,
			octWidth / 2, octWidth, octWidth, octScale, octScale, rotation, 0, 0, octWidth, octWidth, false, false);

		// render elemental energies around outside edge
		sb.setColor(Color.WHITE.cpy());

		for (int i = 0; i < this.elementSlots; i++) {
			Element element = null;
			if (elements.size() > i)
				element = elements.get(i);

			float dir = -rotation / 2;
			if (elementSlots > 0)
				dir += 360 * i / elementSlots;

			float dx = (float) (Math.cos(2 * 3.14 / 360 * dir) * octWidth * 0.3);
			float dy = (float) (Math.sin(2 * 3.14 / 360 * dir) * octWidth * 0.3);

			Util.setBlending(sb, "normal");
			sb.draw(elemental_slot, this.cX - octWidth / 2 + this.bobEffect.y / 4.0F + dx, this.cY - octWidth / 2 + this.bobEffect.y / 4.0F + dy, octWidth / 2, octWidth / 2,
				octWidth, octWidth, symbolScale, symbolScale, 0, 0, 0, octWidth, octWidth, false, false);

			Util.setBlending(sb, "screen");
			if (element != null && elementSymbol.containsKey(element)) {
				sb.draw(elementSymbol.get(element), this.cX - symbolWidth / 2 + this.bobEffect.y / 4.0F + dx, this.cY - symbolWidth / 2 + this.bobEffect.y / 4.0F + dy,
					symbolWidth / 2, symbolWidth / 2, symbolWidth, symbolWidth, symbolScale * 0.75f, symbolScale * 0.75f, 0, 0, 0, symbolWidth, symbolWidth, false, false);
			}
		}

		// reset rendering stuff
		sb.setColor(Color.WHITE.cpy());
		Util.setBlending(sb, "normal");

		rotation += 0.5f;

		// renderText(sb);
		this.hb.render(sb);

		particleDelta += 0.1f;
	}

	@Override
	public void updateAnimation() {
		// super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 45.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			// AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
			// AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}

		octohedronSubimg += Gdx.graphics.getDeltaTime() * 10;
		while (octohedronSubimg > octohedron.size()) {
			octohedronSubimg -= octohedron.size();
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
		return new GolemOrb();
	}
}