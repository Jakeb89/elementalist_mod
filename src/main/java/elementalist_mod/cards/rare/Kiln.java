package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.AccelerantAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Kiln extends AbstractElementalistCard {

	public static final String ID = "elementalist:Kiln";
	public static final String NAME = "Kiln";
	public static String DESCRIPTION = "elementalist:Tiring. NL elementalist:Firecast 1: Gain !M! Strength. NL elementalist:Earthcast 1: Gain !M! Plated Armor.";
	private static final int COST = 1;
	private static final int MAGIC_NUM = 2;
	

	public Kiln() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_3), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		
		//this.keywords.add("tiring");
		
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		addElementalCost(Element.FIRE, 1);
		addElementalCost(Element.EARTH, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast(Element.FIRE, 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
		}

		if (cast(Element.EARTH, 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
		}
	}

	public AbstractCard makeCopy() {
		return new Kiln();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			//editElementalCost(0, "Water", MAGIC_NUM);
		}
	}
}