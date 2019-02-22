package elementalist_mod.cards.uncommon;

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
import elementalist_mod.powers.WindblightPower;

public class Aero extends AbstractElementalistCard {
	public static final String ID = "elementalist:Aero";
	public static final String NAME = "Aero";
	public static String DESCRIPTION = "Gain !M! elementalist:Windblight. Gain 2 elementalist:Air.";
	private static final int COST = 2;
	private static final int MAGIC = 1;
	private static final int MAGIC_UP = 1;

	public Aero() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREEN_2), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = MAGIC;
		
		//addElementalCost(Element.AIR, 2);
		this.element = Element.AIR;
		this.generatedElementAmount = 2;
		this.generatesElement = true;

		this.keywords.add("windburn");
		this.keywords.add("aerial");
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
			queueAction(new ApplyPowerAction(player, player, new WindblightPower(player, player, this.magicNumber), this.magicNumber));
			return true;
		case (1):
			this.changeElementNow(Element.AIR, 2);
		default:
			return false;
		}
	}

	public AbstractCard makeCopy() {
		return new Aero();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(MAGIC_UP);
			initializeDescription();
		}
	}

}