package elementalist_mod.cards.special;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class RelicCard_BlueRibbon extends AbstractElementalistCard {
	public static final String ID = "elementalist:RelicCard_BlueRibbon";
	public static final String NAME = "Blue Ribbon";
	public static String DESCRIPTION = "Start battles with 1 Water. You are more likely to find Water-based cards.";
	private static final int COST = -2;
	

	public RelicCard_BlueRibbon() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREEN_1), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
		this.emblemID = this.ID;
		this.isFakeCard = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		//Should never occur.
	}

	public AbstractCard makeCopy() {
		return new RelicCard_BlueRibbon();
	}

}