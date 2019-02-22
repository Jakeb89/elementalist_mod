package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.rare.Heavensfall;
import elementalist_mod.powers.WindburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NextCardStepAction extends AbstractGameAction {
	private AbstractElementalistCard card = null;
	private int memory = -1;
	private int memory2 = -1;

	public NextCardStepAction(AbstractElementalistCard card) {
		this.duration = 0.001f;
		this.card = card;
	}
	public NextCardStepAction(AbstractElementalistCard card, int memory) {
		this.duration = 0.001f;
		this.card = card;
		this.memory = memory;
	}
	public NextCardStepAction(AbstractElementalistCard card, int memory, int memory2) {
		this.duration = 0.001f;
		this.card = card;
		this.memory = memory;
		this.memory2 = memory2;
	}
	
	public void update() {
		ElementalistMod.log("NextCardStepAction.update()");
		//card.doNextCardStep(memory, memory2);
		card.doNextCardStep();
		
		this.isDone = true;
	}

}
