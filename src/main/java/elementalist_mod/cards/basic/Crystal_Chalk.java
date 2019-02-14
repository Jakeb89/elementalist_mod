package elementalist_mod.cards.basic;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.special.*;
import elementalist_mod.patches.*;

public class Crystal_Chalk extends AbstractElementalistCard {

	public static final String ID = "elementalist:Crystal_Chalk";
	public static final String NAME = "Crystal Chalk";
	public static String DESCRIPTION = "Gain the following Rune cards: NL Zephyrsoul, Aquamind, Terraheart, Igniseye. NL Exhaust.";
	public static String UPGRADED_DESCRIPTION = "Gain the following Rune cards: NL Zephyrsoul, Aquamind, Terraheart, Igniseye. NL Tiring.";
	private static final int COST = 0;

	public Crystal_Chalk() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.CRYSTAL_CHALK), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.BASIC,
				AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);


		AbstractCard c1 = new Zephyr_Soul().makeCopy();
		AbstractCard c2 = new Aqua_Mind().makeCopy();
		AbstractCard c3 = new Terra_Heart().makeCopy();
		AbstractCard c4 = new Ignis_Eye().makeCopy();
		
		/*if(this.upgraded){
			c1.upgrade();
			c2.upgrade();
			c3.upgrade();
			c4.upgrade();
		}*/
		
	    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c1, 1));
	    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c2, 1));
	    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c3, 1));
	    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c4, 1));
	}

	public AbstractCard makeCopy() {
		return new Crystal_Chalk();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = UPGRADED_DESCRIPTION;
			this.exhaust = false;
			initializeDescription();
		}
	}
}
