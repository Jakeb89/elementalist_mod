package elementalist_mod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.TsunamiAction;
import elementalist_mod.cards.AbstractElementalistCard;

public class TsunamiPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:Tsunami";
	public static final String NAME = "Tsunami";
	public static String baseDescription;
	public static String DESCRIPTION;

	public TsunamiPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		initIcon(ElementalistMod.POWER_TSUNAMI, ElementalistMod.POWER_TSUNAMI_SMALL);
	}
	
	public void atStartOfTurnPostDraw() {
	    AbstractDungeon.actionManager.addToBottom(new TsunamiAction(amount));
	}
	
	
	public void updateDescription() {
		baseDescription = "Gain temporary Strength each turn equal to the amount of Watercast in your hand.";
		description = baseDescription;
		DESCRIPTION = description;
	}
}
