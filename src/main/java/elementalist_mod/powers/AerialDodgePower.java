package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

import elementalist_mod.ElementalistMod;

public class AerialDodgePower extends ElementalPower {
	public static final String POWER_ID = "elementalist:AerialDodge";
	public static final String NAME = "Aerial Dodge";
	public static String baseDescription = "When you have less than 5 extra Block remaining at the end of the enemy turn, gain 1 Dexterity.";
	public static String DESCRIPTION = baseDescription;

	public AerialDodgePower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		//ElementalistMod.log("AerialDodgePower()");
		
		initIcon(ElementalistMod.POWER_AERIAL_DODGE, ElementalistMod.POWER_AERIAL_DODGE_SMALL);
	}
	
	public void atEndOfRound() {
		if (owner.currentBlock < 5 && this.amount>0) {
			flashWithoutSound();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new DexterityPower(owner, 2), 2));
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
