package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Mist extends AbstractElementalistCard {
	public static final String ID = "elementalist:Mist";
	public static final String NAME = "Mist";
	public static String DESCRIPTION = "elementalist:Watercast 1: Apply 2 Weak to each enemy that intends to attack. Draw 1 card.";
	public static String UPGRADE_DESCRIPTION = "Innate. NL elementalist:Watercast 1: Apply 2 Weak to each enemy that intends to attack. Draw 1 card.";
	private static final int COST = 0;

	public Mist() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.MIST), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.NONE);
		
		addElementalCost(Element.WATER, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		/*
		if (cast(Element.WATER, 1)) {
			for(AbstractMonster enemy : this.getAllLivingEnemies()) {
				if(intentContainsAttack(enemy.intent)) {
		            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(enemy, p, new WeakPower(enemy, 2, false), 1, true, AbstractGameAction.AttackEffect.NONE));
				}
			}
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
		}
		*/
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			return castNow(Element.WATER, 1);
		case (1):
			for(AbstractMonster enemy : this.getAllLivingEnemies()) {
				if(intentContainsAttack(enemy.intent)) {
					queueAction(new ApplyPowerAction(enemy, player, new WeakPower(enemy, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
				}
			}
			return true;
		case (2):
			queueAction(new DrawCardAction(player, 1));
		default:
			return false;
		}
	}

	public AbstractCard makeCopy() {
		return new Mist();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.isInnate = true;
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

}