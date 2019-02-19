package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;

public class SolarFormPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:SolarForm";
	public static final String NAME = "Solar Form";
	public static String[] DESCRIPTION = {"The first time you cast an element each turn, gain ", " Fire."};
	public boolean active = true;
	
	public SolarFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		
		initIcon(ElementalistMod.POWER_SOLARFORM, ElementalistMod.POWER_SOLARFORM_SMALL);
	}
	
	public void atStartOfTurn() {
		super.atStartOfTurn();
		active = true;
	}
	
	public void onElementalCast(Element element) {
		if(active) {
			active = false;
			this.flash();
			ElementalistMod.changeElement(Element.FIRE, this.amount);
		}
	}
	
	public void updateDescription() {
		this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
	}
}
