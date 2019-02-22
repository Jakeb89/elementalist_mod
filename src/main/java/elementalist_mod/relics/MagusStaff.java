package elementalist_mod.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import elementalist_mod.ElementalistMod;

public class MagusStaff extends CustomRelic {

	public static final String ID = "elementalistmod:MagusStaff";
	public static final String IMG = ElementalistMod.MAGUS_STAFF;
	public static final String OUTLINE_IMG = ElementalistMod.MAGUS_STAFF_OUTLINE;
	public static final String INNERGLOW_IMG = ElementalistMod.MAGUS_STAFF_INNERGLOW;
	public static final String ORB_IMG = ElementalistMod.MAGUS_STAFF_ORB;
	// public static String DESCRIPTION = "[MagusStaff Description]";
	public static boolean active = true;

	public static Texture innerglow, orb_img;
	public int counter;
	public static int charges = 0;
	public static int cardsPlayed = 0;
	public static MagusStaff staff;
	public int offsetX = 0;

	public MagusStaff() {
		// super(ID, new Texture(IMG),new Texture(OUTLINE_IMG), RelicTier.RARE,
		// LandingSound.FLAT);
		super(ID, new Texture(IMG), new Texture(OUTLINE_IMG), RelicTier.STARTER, LandingSound.FLAT);
		// this.setCounter(3);
		// this.updateDescription(AbstractDungeon.player.chosenClass);
		innerglow = ImageMaster.loadImage(INNERGLOW_IMG);
		orb_img = ImageMaster.loadImage(ORB_IMG);
		counter = 0;
		staff = this;

		// this.relicStrings = {"[MagusStaff Description]"};
		// this.DESCRIPTIONS[0] = "[MagusStaff Description]";
	}

	@Override
	public String getUpdatedDescription() {
		// return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1]; // DESCRIPTIONS pulls
		// from your localization file
		return DESCRIPTIONS[0];
	}

	@Override
	public void atBattleStart() {
		charges = 1;
		cardsPlayed = 0;

		active = true;
		this.pulse = true;
		beginPulse();
		updateDescription();
	}

	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster monster) {
		MagusStaff.cardsPlayed++;
		while (MagusStaff.cardsPlayed >= 5) {
			MagusStaff.cardsPlayed -= 5;
			charges++;
			active = true;
			this.pulse = true;
			beginPulse();
		}
		updateDescription();
	}

	public static void onActivate() {
		charges--;
		staff.flash();
		if (charges < 1) {
			active = false;
			staff.pulse = false;
		}
		staff.updateDescription();
	}

	public void registerUse() {
		// active = false;
		flash();
		this.pulse = false;
		updateDescription();
	}

	@Override
	public void renderInTopPanel(SpriteBatch sb) {
		this.scale = Settings.scale;
		counter++;
		if (Settings.hideRelics) {
			return;
		}
		renderOutline(sb, true);
		sb.setColor(Color.WHITE);
		float offsetX = 0f;
		float rotation = 0f;
		sb.draw(this.img, this.currentX - 64.0F + offsetX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, rotation, 0, 0, 128, 128, false, false);

		if (active) {
			sb.draw(MagusStaff.innerglow, this.currentX - 64.0F + offsetX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, rotation, 0, 0, 128, 128,
				false, false);
			float yDisplacement = (float) (2 * Math.cos(counter * 3.14f / 20f)) * this.scale;
			sb.draw(MagusStaff.orb_img, this.currentX - 64.0F + offsetX, this.currentY - 64.0F + yDisplacement, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, rotation, 0,
				0, 128, 128, false, false);

		}

		renderCounter(sb, true);
		renderFlash(sb, true);
		// renderText(sb);
		renderText2(sb, false);
		this.hb.render(sb);
	}

	public void renderText2(SpriteBatch sb, boolean inTopPanel) {
		if (this.counter > -1) {
			Color color1 = Color.DARK_GRAY.cpy();
			Color color2 = Color.WHITE.cpy();
			if (MagusStaff.charges == 0) {
				color2 = Color.DARK_GRAY.cpy();
			}
			if (inTopPanel) {
				FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont,
					Integer.toString(MagusStaff.cardsPlayed), offsetX + this.currentX + 30.0F * Settings.scale, this.currentY + 28.0F * Settings.scale, color1);
				FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont,
					Integer.toString(MagusStaff.charges), offsetX + this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale, color2);
			} else {
				FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont,
					Integer.toString(MagusStaff.cardsPlayed), this.currentX + 30.0F * Settings.scale, this.currentY + 28.0F * Settings.scale, color1);
				FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont,
					Integer.toString(MagusStaff.charges), this.currentX + 30.0F * Settings.scale, this.currentY - 7.0F * Settings.scale, color2);
			}
		}
	}

	protected void renderText(SpriteBatch sb) {

		Color color1 = Color.DARK_GRAY.cpy();

		Color color2 = Color.WHITE.cpy();
		if (MagusStaff.charges == 0) {
			color2 = Color.DARK_GRAY.cpy();
		}

		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(MagusStaff.cardsPlayed), this.currentX + 16, this.currentY + 16, color1,
			this.scale * 0.75f);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(MagusStaff.charges), this.currentX + 16, this.currentY - 24, color2, this.scale * 0.75f);
	}

	public void updateDescription() {
		this.description = "Gains a charge for every 5 cards played. Click an elemental orb to convert a charge into one point of that element. Starts each battle with 1 charge.";
		/*
		this.description += " NL NL ";
		this.description += "You have played " + MagusStaff.cardsPlayed + " cards. The 5th card will create a Magus Staff charge.";
		this.description += " NL The staff has " + MagusStaff.charges + " charges.";
		*/
		this.DESCRIPTIONS[0] = this.description;
	}

	public void onVictory() {
		this.pulse = false;
	}

	@Override
	public AbstractRelic makeCopy() {
		return new MagusStaff();
	}
}
