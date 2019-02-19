package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.BurningSoulPower;
import elementalist_mod.powers.QuadmirePower;

public class Burning_Soul extends AbstractElementalistCard {

	public static final String ID = "elementalist:Burning_Soul";
	public static final String NAME = "Burning Soul";
	public static String DESCRIPTION = "Gain 2 fire at the start of your turn, then lose 1 HP.";
	private static final int COST = 2;
	private static final int COST_UPGRADED = 1;

	public Burning_Soul() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BURNING_SOUL), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BurningSoulPower(p, p, 1), 1));
	}

	public AbstractCard makeCopy() {
		return new Burning_Soul();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(COST_UPGRADED);
			initializeDescription();
		}
	}
}