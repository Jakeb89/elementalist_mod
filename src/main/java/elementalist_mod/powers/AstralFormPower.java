package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import elementalist_mod.ElementalistMod;

public class AstralFormPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:AstralForm";
	public static final String NAME = "Astral Form";
	public static String[] DESCRIPTION = {"When you cast an element from a synergizing element, pick 1 of 3 random cards", " to shuffle into your draw pile."};
	
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
	
	public void onElementalCast(String element) {
		if(ElementalistMod.hasSynergy(element)) {
			this.flash();
		    AbstractDungeon.actionManager.addToBottom(new CodexAction());
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
