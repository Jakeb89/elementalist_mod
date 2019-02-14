package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.MultificationPower;

public class Multification extends AbstractElementalistCard {
	public static final String ID = "elementalist:Multification";
	public static final String NAME = "Multification";
	public static String DESCRIPTION = "Double all elemental energy gain this turn.";
	public static String DESCRIPTION_UPGRADED = "Double all elemental energy gain this turn. Gain 1 Energy.";
	private static final int COST = 0;

	public Multification() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.MULTIFICATION), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MultificationPower(p, p, 1), 1));
		if(upgraded) {
			AbstractDungeon.player.gainEnergy(1);
		}
	}

	public AbstractCard makeCopy() {
		return new Multification();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADED;
			initializeDescription();
		}
	}
}