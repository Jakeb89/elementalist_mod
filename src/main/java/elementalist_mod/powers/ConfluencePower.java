package elementalist_mod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.ElementAddAction;
import elementalist_mod.orbs.AirOrb;
import elementalist_mod.orbs.EarthOrb;
import elementalist_mod.orbs.ElementOrb;
import elementalist_mod.orbs.FireOrb;
import elementalist_mod.orbs.WaterOrb;

public class ConfluencePower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Confluence";
	public static final String NAME = "Confluence";
	public static String baseDescription = "At the beginning of your turn: NL Gain 1 in each Synergizing element.";
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
    	if(ElementalistMod.hasSynergy(Element.FIRE)) 	changeElement(Element.FIRE, this.amount, "confluence");
    	if(ElementalistMod.hasSynergy(Element.EARTH)) 	changeElement(Element.EARTH, this.amount, "confluence");
    	if(ElementalistMod.hasSynergy(Element.WATER)) 	changeElement(Element.WATER, this.amount, "confluence");
    	if(ElementalistMod.hasSynergy(Element.AIR)) 	changeElement(Element.AIR, this.amount, "confluence");
    	updateDescription();
    }
    

	
	public void changeElement(Element element, int delta, String sourceType) {
		ElementalistMod.changeElement(element, delta, sourceType);
	}
	

	
	public void updateDescription() {
		description = baseDescription;
		DESCRIPTION = description;
	}
}
