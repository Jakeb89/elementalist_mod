package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.QuadmirePower;

public class Quadmire extends AbstractElementalistCard {

	public static final String ID = "elementalist:Quadmire";
	public static final String NAME = "Quadmire";
	public static String DESCRIPTION = "When you Earthcast, gain !M! Plated Armor.";
	private static final int COST = 2;
	private static final int MAGIC = 1;
	private static final int MAGIC_UPGRADE = 1;

	public Quadmire() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.QUADMIRE), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = MAGIC;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new QuadmirePower(p, p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Quadmire();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			//this.upgradeBaseCost(1);
			this.upgradeMagicNumber(MAGIC_UPGRADE);
			initializeDescription();
		}
	}
}