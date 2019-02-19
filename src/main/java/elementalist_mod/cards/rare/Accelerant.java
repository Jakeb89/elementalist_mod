package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.AccelerantAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Accelerant extends AbstractElementalistCard {

	public static final String ID = "elementalist:Accelerant";
	public static final String NAME = "Accelerant";
	public static String DESCRIPTION = "Tiring. NL Aircast 1: Target gains !M! Windburn. NL NL Firecast 1: Target takes !D! damage for each Windburn it has.";
	private static final int COST = 1;
	private static final int MAGIC_NUM = 2;
	private static final int DAMAGE = 3;
	private static final int DAMAGE_UP = 2;
	

	public Accelerant() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.ACCELERANT), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ENEMY);
		
		//this.keywords.add("tiring");
		
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;
		this.baseDamage = DAMAGE;
		this.damage = DAMAGE;

		addElementalCost("Air", 1);
		addElementalCost("Fire", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		ElementalistMod.log("Accelerant.use(...) -> damage => " + this.damage);

		if (cast("Air", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WindburnPower(m, p, this.magicNumber), this.magicNumber));
		}

		if (cast("Fire", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new AccelerantAction(p, m, this.damage));
		}
	}

	public AbstractCard makeCopy() {
		return new Accelerant();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			this.upgradeDamage(DAMAGE_UP);
			//editElementalCost(0, "Water", MAGIC_NUM);
		}
	}
}