package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import elementalist_mod.ElementalistMod;

public class LunarFormPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:LunarForm";
	public static final String NAME = "Lunar Form";
	public static String[] DESCRIPTION = {"When you cast any element, gain ", " Water."};
	
	public LunarFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		
		initIcon(ElementalistMod.POWER_LUNARFORM, ElementalistMod.POWER_LUNARFORM_SMALL);
	}
	
	public void onElementalCast(String element) {
		this.flash();
		ElementalistMod.changeElement("Water", this.amount);
	}
	
	public void updateDescription() {
		this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
	}
}
