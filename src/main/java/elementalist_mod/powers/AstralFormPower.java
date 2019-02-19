package elementalist_mod.powers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;

public class AstralFormPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:AstralForm";
	public static final String NAME = "Astral Form";
	public static String[] DESCRIPTION = {"When you cast from a Synergizing element, gain ", " in one of your Synergizing elements."};
	
	public AstralFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		
		initIcon(ElementalistMod.POWER_ASTRALFORM, ElementalistMod.POWER_ASTRALFORM_SMALL);
	}
	
	public void onElementalCast(Element element) {
		if(ElementalistMod.hasSynergy(element)) {
			this.flash();
			
			ArrayList<Element> synergizingElements = new ArrayList<Element>();
			for(Element elem : Element.values()) {
				if(ElementalistMod.hasSynergy(elem)) synergizingElements.add(elem);
			}
			Element chosenElement = synergizingElements.get( (int)( Math.random()*synergizingElements.size() ) );
			
			ElementalistMod.changeElement(chosenElement, amount);
		}
	}
	
	public void updateDescription() {
		if(amount == 1) {
			this.description = DESCRIPTION[0] + DESCRIPTION[1];
		}else {
			this.description = DESCRIPTION[0] + " " + amount + " times" + DESCRIPTION[1];
		}
	}
}
