package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import elementalist_mod.ElementalistMod;
import elementalist_mod.orbs.ElementOrb;

public class WindburnPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Windburn";
	public static final String NAME = "Windburn";
	public static String baseDescription = "Lose HP equal to your Windburn at the end of your turn if any element is higher than Air.";
	public static String DESCRIPTION = baseDescription;

	public WindburnPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();

		this.type = PowerType.DEBUFF;
		this.isTurnBased = true;

		// ElementalistMod.log("WindburnPower()");

		initIcon(ElementalistMod.POWER_WINDBURN, ElementalistMod.POWER_WINDBURN_SMALL);
	}

	public boolean airIsHighest() {
		if (getElement("Air") < getElement("Earth"))
			return false;
		if (getElement("Air") < getElement("Fire"))
			return false;
		if (getElement("Air") < getElement("Water"))
			return false;

		return true;
	}

	public int getElement(String element) {
		return ElementalistMod.getElement(element);
	}

	public void atEndOfTurn(boolean isPlayer) {
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.amount>0) {
			flashWithoutSound();
			if (!airIsHighest() || !owner.isPlayer) {
				AbstractDungeon.actionManager.addToBottom(new PoisonLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));
			}
			this.amount--;
			this.stackPower(0);
		}
		if(this.amount < 1) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}
		updateDescription();
	}

	public void updateDescription() {
		description = baseDescription;
		DESCRIPTION = description;
	}
}
