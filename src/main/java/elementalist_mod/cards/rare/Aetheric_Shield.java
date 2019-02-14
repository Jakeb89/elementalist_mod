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
import elementalist_mod.powers.MirrorCircletPower;
import elementalist_mod.powers.QuadmirePower;
import elementalist_mod.powers.TsunamiPower;

public class Aetheric_Shield extends AbstractElementalistCard {

	public static final String ID = "elementalist:Aetheric_Shield";
	public static final String NAME = "Aetheric Shield";
	public static String DESCRIPTION = "When you gain elemental energy, gain !M! Block.";
	private static final int COST = 2;
	private static final int MAGIC = 2;
	private static final int MAGIC_UPGRADE = 1;

	public Aetheric_Shield() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_POWER_GREY_3), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = MAGIC;
		this.magicNumber = MAGIC;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AethericShieldPower(p, p, magicNumber), magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Aetheric_Shield();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(MAGIC_UPGRADE);
			initializeDescription();
		}
	}
}