package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Breeze extends AbstractElementalistCard {
	public static final String ID = "elementalist:Breeze";
	public static final String NAME = "Breeze";
	public static String DESCRIPTION = "elementalist:Aircast 1: Exhaust a random status or curse in your hand. If you had one, draw !M! card(s).";
	private static final int COST = 0;
	private static final int MAGIC = 1;
	private static final int MAGIC_UP = 2;

	public Breeze() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BREEZE), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = MAGIC;
		
		addElementalCost(Element.AIR, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast(Element.AIR, 1)) {

			AbstractCard card = getRandomCard(getAllStatusOrCurse(p.hand));
			if(card != null) {
				AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
				AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
			}
			
		}
	}

	public AbstractCard makeCopy() {
		return new Breeze();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(MAGIC_UP);
			initializeDescription();
		}
	}

}