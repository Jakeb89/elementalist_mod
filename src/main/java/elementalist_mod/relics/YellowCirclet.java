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

public class YellowCirclet extends CustomRelic {

	public static final String ID = "elementalistmod:YellowCirclet";
	public static final String IMG = ElementalistMod.YELLOW_CIRCLET;
	public static final String OUTLINE_IMG = ElementalistMod.YELLOW_CIRCLET_OUTLINE;

	public YellowCirclet() {
		super(ID, new Texture(IMG), new Texture(OUTLINE_IMG), RelicTier.SPECIAL, LandingSound.FLAT);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	
	public void updateDescription() {
		this.description = "A curious and familiar yellow circlet. I looks exactly like the one on your staff...";
		this.DESCRIPTIONS[0] = this.description;
	}
	
	public void atBattleStart() {
		ElementalistMod.changeElement(Element.EARTH, 1, this.ID);
	}

	@Override
	public AbstractRelic makeCopy() {
		return new YellowCirclet();
	}
}
