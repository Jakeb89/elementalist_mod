package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.ElementAddAction;
import elementalist_mod.orbs.AirOrb;
import elementalist_mod.orbs.EarthOrb;
import elementalist_mod.orbs.ElementOrb;
import elementalist_mod.orbs.FireOrb;
import elementalist_mod.orbs.WaterOrb;

public class ConfluencePower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Confluence";
	public static final String NAME = "Confluence";
	public static String baseDescription = "At the beginning of your turn: NL Gain 1 in each synergizing element.";
	public static String DESCRIPTION = baseDescription;

	public ConfluencePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		//ElementalistMod.log("ConfluencePower()");
		
		initIcon(ElementalistMod.POWER_CONFLUENCE, ElementalistMod.POWER_CONFLUENCE_SMALL);
	}
	

    public void atStartOfTurn() {
    	/*
    	int fire = getElement("Fire");
    	int earth = getElement("Earth");
    	int water = getElement("Water");
    	int air = getElement("Air");
    	*/
    	
    	//Because changeElement queues up an action, these checks shouldn't interfere with each other.
    	if(ElementalistMod.hasSynergy("Fire")) changeElement("Fire", this.amount, "confluence");
    	if(ElementalistMod.hasSynergy("Earth")) changeElement("Earth", this.amount, "confluence");
    	if(ElementalistMod.hasSynergy("Water")) changeElement("Water", this.amount, "confluence");
    	if(ElementalistMod.hasSynergy("Air")) changeElement("Air", this.amount, "confluence");
    	updateDescription();
    }
    

	
	public void changeElement(String element, int delta, String sourceType) {
		AbstractDungeon.actionManager.addToBottom(new ElementAddAction(makeOrb(element, delta), sourceType));
	}
	

	
	public ElementOrb makeOrb(String element, int amount) {
		switch(element) {
			case("Fire"): 
				return new FireOrb(amount);
			case("Water"): 
				return new WaterOrb(amount);
			case("Earth"): 
				return new EarthOrb(amount);
			case("Air"): 
				return new AirOrb(amount);
		}
		return null;
	}
	
	public void updateDescription() {
		description = baseDescription;
		DESCRIPTION = description;
	}
}
