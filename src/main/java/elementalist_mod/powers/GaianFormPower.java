package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import elementalist_mod.ElementalistMod;

public class GaianFormPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:GaianForm";
	public static final String NAME = "Gaian Form";
	public static String[] DESCRIPTION = {"The first time you cast an element each turn, gain ", " Earth."};
	public boolean active = true;
	
	public GaianFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		
		initIcon(ElementalistMod.POWER_GAIANFORM, ElementalistMod.POWER_GAIANFORM_SMALL);
	}
	
	public void atStartOfTurn() {
		super.atStartOfTurn();
		active = true;
	}
	
	public void onElementalCast(String element) {
		if(active) {
			active = false;
			this.flash();
			ElementalistMod.changeElement("Earth", this.amount);
		}
	}
	
	public void updateDescription() {
		this.description = DESCRIPTION[0] + this.amount + DESCRIPTION[1];
	}
}
