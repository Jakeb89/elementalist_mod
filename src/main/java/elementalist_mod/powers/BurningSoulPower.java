package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;

public class BurningSoulPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:BurningSoul";
	public static final String NAME = "Burning Soul";
	public static String baseDescription;
	public static String DESCRIPTION;

	public BurningSoulPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		initIcon(ElementalistMod.POWER_BURNING_SOUL, ElementalistMod.POWER_BURNING_SOUL_SMALL);
	}
	
	public void atStartOfTurn() {
		ElementalistMod.changeElement(Element.FIRE, amount*2);
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(owner, source, amount));
	}
	
	public void updateDescription() {
		baseDescription = "Gain "+(amount*2)+" Fire at the start of your turn, then lose "+amount+" HP.";
		description = baseDescription;
		DESCRIPTION = description;
	}
}
