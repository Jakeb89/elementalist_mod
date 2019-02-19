package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;

public class AethericShieldPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:AethericShield";
	public static final String NAME = "Aetheric Shield";
	public static String baseDescription;
	public static String DESCRIPTION;

	public AethericShieldPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		initIcon(ElementalistMod.POWER_AETHERIC_SHIELD, ElementalistMod.POWER_AETHERIC_SHIELD_SMALL);
	}
	
	public int onAddElement(Element element, int amount, String sourceType) {
		if(sourceType == "widdershins") return amount;
		if(amount < 0) return amount;
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, source, this.amount));
		
		return amount;
	}
	
	public void updateDescription() {
		baseDescription = "When you gain elemental energy, gain "+amount+"Block.";
		description = baseDescription;
		DESCRIPTION = description;
	}
}
