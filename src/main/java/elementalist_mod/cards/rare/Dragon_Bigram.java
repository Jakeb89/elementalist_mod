package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.AethericShieldPower;
import elementalist_mod.powers.BurningSoulPower;
import elementalist_mod.powers.DragonBigramPower;
import elementalist_mod.powers.MirrorCircletPower;
import elementalist_mod.powers.QuadmirePower;
import elementalist_mod.powers.TsunamiPower;

public class Dragon_Bigram extends AbstractElementalistCard {

	public static final String ID = "elementalist:Dragon_Bigram";
	public static final String NAME = "Dragon Bigram";
	public static String DESCRIPTION = "When you elementalist:Earthcast, gain 1 elementalist:Air. When you elementalist:Aircast, gain 1 elementalist:Earth.";
	public static String DESCRIPTION_UPGRADED = "Innate. NL When you elementalist:Earthcast, gain 1 elementalist:Air. When you elementalist:Aircast, gain 1 elementalist:Earth.";
	private static final int COST = 2;

	public Dragon_Bigram() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_POWER_GREY_2), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DragonBigramPower(p, p, 1), 1));
	}

	public AbstractCard makeCopy() {
		return new Dragon_Bigram();
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