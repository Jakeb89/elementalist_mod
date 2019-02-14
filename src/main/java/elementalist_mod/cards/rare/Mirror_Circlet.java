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

public class Mirror_Circlet extends AbstractElementalistCard {

	public static final String ID = "elementalist:Mirror_Circlet";
	public static final String NAME = "Mirror Circlet";
	public static String DESCRIPTION = "Put a copy of the first card you exhaust each turn on top of your deck. It costs 1 more to play.";
	private static final int COST = 2;
	private static final int COST_UPGRADED = 1;

	public Mirror_Circlet() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_POWER_GREY_4), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MirrorCircletPower(p, p, 1), 1));
	}

	public AbstractCard makeCopy() {
		return new Mirror_Circlet();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(COST_UPGRADED);
			initializeDescription();
		}
	}
}