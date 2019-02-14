package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Rising_Vapors extends AbstractElementalistCard {

	public static final String ID = "elementalist:Rising_Vapors";
	public static final String NAME = "Rising Vapors";
	public static String DESCRIPTION = "Tiring. NL Watercast 1: Gain !M! Regen. NL NL Aircast 1: Gain !M! Dexterity.";
	private static final int COST = 1;
	private static final int MAGIC_NUM = 2;
	

	public Rising_Vapors() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.RISING_VAPORS), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		
		//this.keywords.add("tiring");
		
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		addElementalCost("Water", 1);
		addElementalCost("Air", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast("Water", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, this.magicNumber), this.magicNumber));
		}

		if (cast("Air", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		}
	}

	public AbstractCard makeCopy() {
		return new Rising_Vapors();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			//editElementalCost(0, "Water", MAGIC_NUM);
		}
	}
}