package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.AerialDodgePower;

public class Breeze extends AbstractElementalistCard {
	public static final String ID = "elementalist:Breeze";
	public static final String NAME = "Breeze";
	public static String DESCRIPTION = "Exhaust a random Status or Curse in your hand. NL NL elementalist:Aircast 1:  Gain !M! elementalist:Aerial elementalist:Dodge. Exhaust.";
	private static final int COST = 1;
	private static final int MAGIC = 2;
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

		/*
		if (cast(Element.AIR, 1)) {
			AbstractCard card = getRandomCard(getAllStatusOrCurse(p.hand));
			if(card != null) {
				AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
				AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
			}
		}
		*/
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			AbstractCard card = getRandomCard(getAllStatusOrCurse(player.hand));
			if(card != null) {
				queueAction(new ExhaustSpecificCardAction(card, player.hand));
			}
		case (1):
			return castNow(Element.AIR, 1);
		case (2):
			queueAction(new ApplyPowerAction(player, player, new AerialDodgePower(player, player, this.magicNumber), this.magicNumber));
			this.exhaust = true;
			queueAction(new ExhaustSpecificCardAction(this, AbstractDungeon.player.limbo));
		default:
			return false;
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