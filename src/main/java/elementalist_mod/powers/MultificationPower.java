package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import elementalist_mod.ElementalistMod;

public class MultificationPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Multification";
	public static final String NAME = "Multification";
	public static String DESCRIPTION = "Double all elemental energy gain this turn.";

	public MultificationPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		
		initIcon(ElementalistMod.POWER_MULTIFICATION, ElementalistMod.POWER_MULTIFICATION_SMALL);
	}
	

	public void atEndOfTurn(boolean isPlayer) {
		AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
	}
	
	public void updateDescription() {
		this.description = DESCRIPTION;
	}
	
}
