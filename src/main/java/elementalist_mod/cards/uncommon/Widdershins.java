package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.*;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Widdershins extends AbstractElementalistCard {

	public static final String ID = "elementalist:Widdershins_Conversion";
	public static final String NAME = "Widdershins";
	public static String DESCRIPTION = "Transmute the elements Fire to Earth, Earth to Water, Water to Air, and Air to Fire simultaniously.";
	public static String DESCRIPTION_UPGRADED = "Mercurial. NL Transmute the elements Fire to Earth, Earth to Water, Water to Air, and Air to Fire simultaniously.";
	private static final int COST = 0;

	public Widdershins() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.WIDDERSHINS_CONVERSION), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);

		//addElementalCost("Air", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		//if(cast("Air", 1)) {
	    //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
		//}

		AbstractDungeon.actionManager.addToBottom(new WiddershinsAction(this, null));
	}
	
	public AbstractCard makeCopy() {
		return new Widdershins();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.isMercurial = true;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}
