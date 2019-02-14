package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

import elementalist_mod.ElementalistMod;

public class MirrorCircletPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:MirrorCirclet";
	public static final String NAME = "Mirror Circlet";
	public static String baseDescription;
	public static String DESCRIPTION;
	public boolean active = true;

	public MirrorCircletPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		initIcon(ElementalistMod.POWER_MIRROR_CIRCLET, ElementalistMod.POWER_MIRROR_CIRCLET_SMALL);
		this.atStartOfTurn();
	}
	
	public void atStartOfTurn() {
		active = true;
	}
	
	public void onCardExhaust(AbstractCard card) {
		if(!active) return;
		
		active = false;
		AbstractCard copy = card.makeStatEquivalentCopy();
		copy.modifyCostForCombat(1);
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(copy, amount, false, true));
	}
	
	public void updateDescription() {
		baseDescription = "Put a copy of the first card you exhaust each turn on top of your deck. It costs 1 more to play.";
		description = baseDescription;
		DESCRIPTION = description;
	}
}
