package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Mist extends AbstractElementalistCard {
	public static final String ID = "elementalist:Mist";
	public static final String NAME = "Mist";
	public static String DESCRIPTION = "Watercast 1: Each enemy that intends to attack gains 2 weakness. Draw 1 card.";
	public static String UPGRADE_DESCRIPTION = "Innate. NL Watercast 1: Each enemy that intends to attack gains 2 weakness. Draw 1 card.";
	private static final int COST = 0;

	public Mist() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.MIST), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.NONE);
		
		addElementalCost("Water", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast("Water", 1)) {

			for(AbstractMonster enemy : this.getAllLivingEnemies()) {
				if(intentContainsAttack(enemy.intent)) {
		            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(enemy, p, new WeakPower(enemy, 2, false), 1, true, AbstractGameAction.AttackEffect.NONE));
				}
			}
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
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