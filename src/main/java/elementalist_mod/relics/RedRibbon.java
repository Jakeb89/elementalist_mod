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
import elementalist_mod.ElementalistMod.Element;

public class RedRibbon extends CustomRelic {

	public static final String ID = "elementalistmod:RedRibbon";
	public static final String IMG = ElementalistMod.RED_RIBBON;
	public static final String OUTLINE_IMG = ElementalistMod.RED_RIBBON_OUTLINE;

	public RedRibbon() {
		super(ID, new Texture(IMG), new Texture(OUTLINE_IMG), RelicTier.SPECIAL, LandingSound.FLAT);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	
	public void updateDescription() {
		this.description = "A curious and familiar red ribbon. I looks exactly like the one on your staff...";
		this.DESCRIPTIONS[0] = this.description;
	}
	
	public void atBattleStart() {
		ElementalistMod.changeElement(Element.FIRE, 1, this.ID);
	}

	@Override
	public AbstractRelic makeCopy() {
		return new RedRibbon();
	}
}
