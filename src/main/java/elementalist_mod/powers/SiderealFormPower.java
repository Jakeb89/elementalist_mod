package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import elementalist_mod.ElementalistMod;

public class SiderealFormPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:SiderealForm";
	public static final String NAME = "Sidereal Form";
	public static String[] DESCRIPTION = {"The first time you cast an element each turn, gain ", " Air."};
	public boolean active = true;
	
	public SiderealFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		
		initIcon(ElementalistMod.POWER_SIDEREALFORM, ElementalistMod.POWER_SIDEREALFORM_SMALL);
	}
	
	public void atStartOfTurn() {
		super.atStartOfTurn();
		active = true;
	}
	
	public void onElementalCast(String element) {
		if(active) {
			active = false;
			this.flash();
			ElementalistMod.changeElement("Air", this.amount);
		}
	}
	
	public void updateDescription() {
		this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
	}
}
