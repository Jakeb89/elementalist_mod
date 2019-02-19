package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.ElementAddAction;
import elementalist_mod.orbs.AirOrb;
import elementalist_mod.orbs.EarthOrb;
import elementalist_mod.orbs.ElementOrb;
import elementalist_mod.orbs.FireOrb;
import elementalist_mod.orbs.WaterOrb;

public class QuadmirePower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Quadmire";
	public static final String NAME = "Quadmire";

	public QuadmirePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		//ElementalistMod.log("QuadmirePower()");
		
		initIcon(ElementalistMod.POWER_QUADMIRE, ElementalistMod.POWER_QUADMIRE_SMALL);
	}
	
	public void onElementalCast(Element element) {
		if(element == Element.EARTH) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new PlatedArmorPower(owner, amount), amount));
		}
	}
	
	public void updateDescription() {
		description = "When you Earthcast, gain "+amount+" Plated Armor.";
	}
}
