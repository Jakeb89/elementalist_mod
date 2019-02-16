package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.ChooseCardFromPileAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.special.Air_Emblem;
import elementalist_mod.cards.special.Earth_Emblem;
import elementalist_mod.cards.special.Fire_Emblem;
import elementalist_mod.cards.special.Water_Emblem;
import elementalist_mod.patches.*;
import elementalist_mod.powers.MomentumPower;

public class Momentum extends AbstractElementalistCard {

	public static final String ID = "elementalist:Momentum";
	public static final String NAME = "Momentum";
	public static String DESCRIPTION = "Choose an Emblem to add to your draw pile. Exhaust.";
	public static String DESCRIPTION_UPGRADED = "Innate. NL Choose an Emblem to add to your draw pile. Exhaust.";
	private static final int COST = 1;

	public Momentum() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.MOMENTUM), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
		group.addToBottom(new Air_Emblem());
		group.addToBottom(new Water_Emblem());
		group.addToBottom(new Earth_Emblem());
		group.addToBottom(new Fire_Emblem());
		this.exhaust = true;

		AbstractDungeon.actionManager.addToBottom(new ChooseCardFromPileAction(this, group, generatedElementAmount));
	}
	
	@Override
	public void actionCallback(AbstractCard card) {
		ElementalistMod.log("Momentum.actionCallback(...)");
		if(card != null) {
			ElementalistMod.log("card.name => " + card.name);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
		}
	}

	public AbstractCard makeCopy() {
		return new Momentum();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.isInnate = true;
			this.rawDescription = DESCRIPTION_UPGRADED;
			initializeDescription();
		}
	}
}
