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

public class DragonBigramPower extends ElementalPower {
	public static final String POWER_ID = "elementalist:DragonBigram";
	public static final String NAME = "Dragon Bigram";
	public static String baseDescription;
	public static String DESCRIPTION;

	public DragonBigramPower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.ID = POWER_ID;
		this.name = NAME;
		this.owner = owner;
		this.source = source;
		this.amount = amount;
		updateDescription();
		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		
		initIcon(ElementalistMod.POWER_DRAGON_BIGRAM, ElementalistMod.POWER_DRAGON_BIGRAM_SMALL);
	}
	
	@Override
	public void onElementalCast(Element element) {
		if(element == Element.EARTH) {
			ElementalistMod.changeElement(Element.AIR, 1);
		}else if(element == Element.AIR) {
			ElementalistMod.changeElement(Element.EARTH, 1);
		}
	}
	
	public void updateDescription() {
		baseDescription = "When you Earthcast, gain "+amount+" Air. When you Aircast, gain "+amount+" Earth.";
		description = baseDescription;
		DESCRIPTION = description;
	}
}
