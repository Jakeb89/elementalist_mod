package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;

public class MomentumPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Momentum";
	public static final String NAME = "Momentum";
	public static String baseDescription = "The second time you gain elemental energy from a card during your turn while having Momentum, gain 1 extra point.";
	public static String DESCRIPTION = baseDescription;
	public int elementGainCount = 0;

	public MomentumPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		//ElementalistMod.log("ConfluencePower()");
		
		initIcon(ElementalistMod.POWER_MOMENTUM, ElementalistMod.POWER_MOMENTUM_SMALL);
	}
	
	public void atStartOfTurn() {
    	elementGainCount = 0;
	}
	
	public int onAddElement(Element element, int amount, String sourceType) {
		if(amount > 0 && sourceType == "card") {
			if(elementGainCount == 1) {
				amount += this.amount;
				this.flash();
			}
			elementGainCount++;
		}
		return amount;
	}
	
	public void updateDescription() {
		description = baseDescription;
		DESCRIPTION = description;
	}
}
