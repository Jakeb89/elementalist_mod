package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.orbs.ElementOrb;

public class WindblightPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Windblight";
	public static final String NAME = "Windblight";
	public static String baseDescription = "When you draw or discard cards, gain 1 Aerial Dodge and 1 Windburn.";
	public static String DESCRIPTION = baseDescription;

	public WindblightPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();

		this.type = PowerType.BUFF;
		this.isTurnBased = true;

		// ElementalistMod.log("WindburnPower()");

		initIcon(ElementalistMod.POWER_WINDBLIGHT, ElementalistMod.POWER_WINDBLIGHT_SMALL);
	}
	
	public void onDrawOrDiscard() {
		this.flash();
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new WindburnPower(this.owner, this.owner, 1), 1));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new AerialDodgePower(this.owner, this.owner, 1), 1));
	}

	public void atEndOfTurn(boolean isPlayer) {
		this.amount--;
		if(this.amount < 1) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}
		updateDescription();
	}

	public void updateDescription() {
		description = baseDescription;
		DESCRIPTION = description;
	}
}
