package elementalist_mod.patches;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.Util;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.common.*;
import elementalist_mod.cards.special.*;

public class ElementalCardRenderer {

	// public static Texture fireCostStamp, waterCostStamp, earthCostStamp,
	// airCostStamp;
	public static boolean texturesLoaded = false;

	public static Texture[] fireCostStamp = new Texture[8];
	public static Texture[] waterCostStamp = new Texture[8];
	public static Texture[] earthCostStamp = new Texture[8];
	public static Texture[] airCostStamp = new Texture[8];
	public static Texture[] costElementalStoneStamp = new Texture[4];
	public static Texture[] costElementalGlowStamp = new Texture[4];
	public static Texture[] costElementalStarStamp = new Texture[4];

	public static Texture[] fireUpStamp = new Texture[2];
	public static Texture[] waterUpStamp = new Texture[2];
	public static Texture[] earthUpStamp = new Texture[2];
	public static Texture[] airUpStamp = new Texture[2];

	public static Texture[] energyStamp = new Texture[4];

	public static Texture elementUpStoneStamp, elementUpGlowStamp;

	public static Texture[] emblemBG = new Texture[3];

	public static HashMap<String, Texture> emblems = new HashMap<String, Texture>();

	private static BitmapFont lastTextFont = null;
	private static float lastTextFontScale = 0f;

	private static void loadTextures() {
		texturesLoaded = true;

		emblems.put(new Fire_Emblem().emblemID, ImageMaster.loadImage("img/cards/512/fire_emblem.png"));
		emblems.put(new Water_Emblem().emblemID, ImageMaster.loadImage("img/cards/512/water_emblem.png"));
		emblems.put(new Air_Emblem().emblemID, ImageMaster.loadImage("img/cards/512/air_emblem.png"));
		emblems.put(new Earth_Emblem().emblemID, ImageMaster.loadImage("img/cards/512/earth_emblem.png"));

		emblems.put(new RelicCard_RedRibbon().emblemID, ImageMaster.loadImage("img/cards/512/red_ribbon.png"));
		emblems.put(new RelicCard_BlueRibbon().emblemID, ImageMaster.loadImage("img/cards/512/blue_ribbon.png"));
		emblems.put(new RelicCard_GreenCirclet().emblemID, ImageMaster.loadImage("img/cards/512/green_circlet.png"));
		emblems.put(new RelicCard_YellowCirclet().emblemID, ImageMaster.loadImage("img/cards/512/yellow_circlet.png"));
		//emblems.put(new Check_The_Pins().emblemID, ImageMaster.loadImage("img/cards/512/check_the_pins.png"));

		for (int i = 0; i < 2; i++) {
			fireUpStamp[i] = ImageMaster.loadImage("img/stamps/gain_fire_" + i + ".png");
			earthUpStamp[i] = ImageMaster.loadImage("img/stamps/gain_earth_" + i + ".png");
			waterUpStamp[i] = ImageMaster.loadImage("img/stamps/gain_water_" + i + ".png");
			airUpStamp[i] = ImageMaster.loadImage("img/stamps/gain_air_" + i + ".png");
		}

		elementUpStoneStamp = ImageMaster.loadImage("img/stamps/gain_element_stone.png");
		elementUpGlowStamp = ImageMaster.loadImage("img/stamps/gain_element_glow.png");

		for (int i = 0; i < 4; i++) {
			fireCostStamp[i * 2] = ImageMaster.loadImage("img/stamps/cost_fire_" + i + "_0.png");
			fireCostStamp[i * 2 + 1] = ImageMaster.loadImage("img/stamps/cost_fire_" + i + "_1.png");

			waterCostStamp[i * 2] = ImageMaster.loadImage("img/stamps/cost_water_" + i + "_0.png");
			waterCostStamp[i * 2 + 1] = ImageMaster.loadImage("img/stamps/cost_water_" + i + "_1.png");

			earthCostStamp[i * 2] = ImageMaster.loadImage("img/stamps/cost_earth_" + i + "_0.png");
			earthCostStamp[i * 2 + 1] = ImageMaster.loadImage("img/stamps/cost_earth_" + i + "_1.png");

			airCostStamp[i * 2] = ImageMaster.loadImage("img/stamps/cost_air_" + i + "_0.png");
			airCostStamp[i * 2 + 1] = ImageMaster.loadImage("img/stamps/cost_air_" + i + "_1.png");

			costElementalStoneStamp[i] = ImageMaster.loadImage("img/stamps/cost_elemental_stone_" + i + ".png");
			costElementalGlowStamp[i] = ImageMaster.loadImage("img/stamps/cost_elemental_glow_" + i + ".png");
			costElementalStarStamp[i] = ImageMaster.loadImage("img/stamps/cost_elemental_star_" + i + ".png");

			energyStamp[i] = ImageMaster.loadImage("img/stamps/cost_energy_" + i + ".png");
		}

		emblemBG[0] = ImageMaster.loadImage("img/512/bg_emblem.png");
		emblemBG[1] = ImageMaster.loadImage("img/512/bg_emblem_shadow.png");
		emblemBG[2] = ImageMaster.loadImage("img/512/bg_emblem_banner.png");
	}

	public static void RenderPostfix(AbstractCard obj, final SpriteBatch sb, boolean selected) {

		if (!texturesLoaded)
			loadTextures();

		// ElementalistMod.logger.info("ElementalCardPatch.PostFix()");

		// float dx = -128f;// + -48f;
		// float dy = -128f;// + -128f;
		if (obj.isFlipped)
			return;

		if (obj instanceof AbstractElementalistCard) {
			AbstractElementalistCard card = (AbstractElementalistCard) obj;

			if (card.isEmblem || card.isFakeCard) {
				//drawEmblemCard(sb, card);
				return;
			}

			if (card.element != null) {
				drawStampElementUp(sb, card, card.element, 0, 0);
			}

			for (int i = 0; i < card.costElement.size(); i++) {
				int usedAlready = 0;
				for (int j = 0; j < i; j++) {
					if (card.costElement.get(i) == card.costElement.get(j)) {
						usedAlready += card.costElementAmount.get(j);
					}
				}
				boolean isReady = (card.costElementAmount.get(i) + usedAlready <= ElementalistMod.getElement(card.costElement.get(i))) ? true : false;
				if (card.costElementAmount.get(i) < 0)
					isReady = true;
				drawElementalCostStamp(sb, card, i, isReady, 0, 0);
				drawElementalCostText(sb, card, i, isReady, 0, -56 * i);
			}

			boolean isReady;
			if (AbstractDungeon.player != null && AbstractDungeon.player.orbs != null) {
				isReady = card.hasEnoughEnergy();
			} else {
				isReady = true;
			}

			drawCostStamp(sb, card, isReady, 0, 0);
			drawCostText(sb, card, isReady, 0, 0);

			/*
			 * Old code. Left here as reference. if (card.costElement != "") {
			 * drawElementalCostStamp(obj, sb, selected, card.costElement, -256f, -256f);
			 * drawElementalCostText(obj, sb, card.costElement, card.costElementAmount); }
			 */
		}
	}

	public static void RenderTitlePrefix(AbstractCard obj, SpriteBatch sb) {
		if (!texturesLoaded)
			loadTextures();

		if (obj.isFlipped)
			return;

		if (obj instanceof AbstractElementalistCard) {
			AbstractElementalistCard card = (AbstractElementalistCard) obj;

			if (card.isEmblem || card.isFakeCard) {
				drawEmblemCard(sb, card);
			}
		}
	}

	private static void drawEmblemCard(SpriteBatch sb, AbstractElementalistCard card) {
		// ElementalistMod.log("drawEmblemCard()");

		Color colWhite = Color.WHITE.cpy();

		sb.setColor(colWhite);

		Util.setBlending(sb, "normal");

		float scale = card.drawScale * Settings.scale;

		sb.draw(emblemBG[0], card.current_x - 256f, card.current_y - 256f, 256f, 256f, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);

		Texture texture = emblems.get(card.emblemID);
		if (texture != null) {
			//ElementalistMod.log("emblem texture not null");
			sb.draw(texture, card.current_x - 256f, card.current_y - 256f , 256f, 256f, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);
		}

		sb.draw(emblemBG[1], card.current_x - 256f, card.current_y - 256f, 256f, 256f, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);
		sb.draw(emblemBG[2], card.current_x - 256f, card.current_y - 256f, 256f, 256f, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);
		
		// ElementalistMod.log("drawEmblemCard() end");
	}

	public static void drawCostStamp(final SpriteBatch sb, AbstractElementalistCard card, boolean isReady, float dx, float dy) {

		if (card.cost < 0) {
			return;
		}

		Util.setBlending(sb, "normal");
		float scale = card.drawScale * Settings.scale;

		if (!isReady) {
			sb.draw(energyStamp[0], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);
		} else {
			sb.draw(energyStamp[1], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);

			Util.setBlending(sb, "linear dodge");
			sb.draw(energyStamp[2], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);

			sb.draw(energyStamp[3], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);
		}

		Util.setBlending(sb, "normal");
	}

	public static void drawCostText(SpriteBatch sb, AbstractElementalistCard card, boolean isReady, float dx, float dy) {
		// String element = card.costElement.get(slot);
		int energyCost = card.cost;

		if ((energyCost <= -2) || (card.isLocked) || (!card.isSeen)) {
			return;
		}

		if (card.isCostModifiedForTurn) {
			energyCost = card.costForTurn;
		}

		Color costColor = Color.WHITE.cpy();

		String text = energyCost + "";
		if (energyCost == -1)
			text = "X";

		if (!isReady) {
			costColor = Color.DARK_GRAY.cpy();
		}

		costColor.a = card.transparency;

		FontHelper.renderRotatedText(sb, getEnergyFont(card), text, card.current_x, card.current_y, (dx - 132.0F) * card.drawScale * Settings.scale,
			(dy + 194.0F) * card.drawScale * Settings.scale, card.angle, false, costColor);
	}

	public static void drawElementalCostStamp(final SpriteBatch sb, AbstractElementalistCard card, int slot, boolean isReady, float dx, float dy) {
		Element element = card.costElement.get(slot);

		Texture baseTexture = null;
		switch (element) {
		case FIRE:
			baseTexture = fireCostStamp[slot * 2 + (isReady ? 1 : 0)];
			break;
		case EARTH:
			baseTexture = earthCostStamp[slot * 2 + (isReady ? 1 : 0)];
			break;
		case WATER:
			baseTexture = waterCostStamp[slot * 2 + (isReady ? 1 : 0)];
			break;
		case AIR:
			baseTexture = airCostStamp[slot * 2 + (isReady ? 1 : 0)];
			break;
		}

		float scale = card.drawScale * Settings.scale;
		Util.setBlending(sb, "normal");
		sb.draw(baseTexture, card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512, false, false);

		if (!isReady) {
			Util.setBlending(sb, "multiply");
			sb.draw(costElementalStoneStamp[slot], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512,
				false, false);
		} else {
			Util.setBlending(sb, "linear dodge");
			sb.draw(costElementalGlowStamp[slot], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512,
				false, false);
			sb.draw(costElementalStarStamp[slot], card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, scale, scale, card.angle, 0, 0, 512, 512,
				false, false);
		}

		Util.setBlending(sb, "normal");
	}

	public static void drawElementalCostText(SpriteBatch sb, AbstractElementalistCard card, int slot, boolean isReady, float dx, float dy) {
		// String element = card.costElement.get(slot);
		int elementCost = card.costElementAmount.get(slot);

		if ((elementCost <= -2) || (card.isLocked) || (!card.isSeen)) {
			return;
		}

		Color costColor = Color.WHITE.cpy();

		String text = elementCost + "";
		if (elementCost == -1)
			text = "X";

		if (!isReady) {
			costColor = Color.DARK_GRAY.cpy();
		}

		costColor.a = card.transparency;

		FontHelper.renderRotatedText(sb, getEnergyFont(card), text, card.current_x, card.current_y, (dx - 132.0F) * card.drawScale * Settings.scale,
			(dy + (192.0F - 55)) * card.drawScale * Settings.scale, card.angle, false, costColor);
	}

	public static void drawStampElementUp(final SpriteBatch sb, AbstractElementalistCard card, Element element, float dx, float dy) {

		Texture texture = null;
		int readyIndex = card.willSuccessfullyGainElement() ? 0 : 1;
		switch (element) {
		case FIRE:
			texture = fireUpStamp[readyIndex];
			break;
		case EARTH:
			texture = earthUpStamp[readyIndex];
			break;
		case WATER:
			texture = waterUpStamp[readyIndex];
			break;
		case AIR:
			texture = airUpStamp[readyIndex];
			break;
		}

		Util.setBlending(sb, "normal");
		sb.draw(texture, card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, card.drawScale * Settings.scale, card.drawScale * Settings.scale,
			card.angle, 0, 0, 512, 512, false, false);

		if (!card.willSuccessfullyGainElement()) {
			// stone
			Util.setBlending(sb, "multiply");
			sb.draw(elementUpStoneStamp, card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, card.drawScale * Settings.scale,
				card.drawScale * Settings.scale, card.angle, 0, 0, 512, 512, false, false);
		} else {
			// glow
			Util.setBlending(sb, "linear dodge");
			sb.draw(elementUpGlowStamp, card.current_x - 256f, card.current_y - 256f, 256f + dx, 256f + dy, 512.0f, 512.0f, card.drawScale * Settings.scale,
				card.drawScale * Settings.scale, card.angle, 0, 0, 512, 512, false, false);
		}

		Util.setBlending(sb, "normal");
	}

	private static BitmapFont getEnergyFont(AbstractCard obj) {
		if (lastTextFont == null || obj.drawScale != lastTextFontScale) {
			FontHelper.cardEnergyFont_L.getData().setScale(obj.drawScale);
			lastTextFont = FontHelper.cardEnergyFont_L;
			lastTextFontScale = obj.drawScale;
		}
		return lastTextFont;
	}

}
