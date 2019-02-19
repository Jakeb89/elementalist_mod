package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.BurningSoulPower;
import elementalist_mod.powers.MirrorCircletPower;
import elementalist_mod.powers.QuadmirePower;
import elementalist_mod.powers.TsunamiPower;

public class Tsunami extends AbstractElementalistCard {

	public static final String ID = "elementalist:Tsunami";
	public static final String NAME = "Tsunami";
	public static String DESCRIPTION = "elementalist:Mercurial. NL Gain temporary Strength each turn equal to the amount of elementalist:Watercast in your hand.";
	private static final int COST = 3;
	private static final int COST_UPGRADED = 2;

	public Tsunami() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_POWER_BLUE_4), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.isMercurial = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TsunamiPower(p, p, 1), 1));
	}

	public AbstractCard makeCopy() {
		return new Tsunami();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(COST_UPGRADED);
			initializeDescription();
		}
	}
}