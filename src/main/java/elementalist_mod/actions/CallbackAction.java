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

public class CallbackAction extends AbstractGameAction {
	private AbstractElementalistCard card = null;
	private AbstractElementalistAction action = null;
	private int memory = -1;
	private int memory2 = -1;
	private int callbackMode = 1;

	public CallbackAction(AbstractElementalistCard card) {
		this.duration = 0.001f;
		this.card = card;
	}
	public CallbackAction(AbstractElementalistCard card, int memory) {
		this.duration = 0.001f;
		this.card = card;
		this.memory = memory;
	}
	public CallbackAction(AbstractElementalistCard card, int memory, int memory2) {
		this.duration = 0.001f;
		this.card = card;
		this.memory = memory;
		this.memory2 = memory2;
		callbackMode = 2;
	}
	public CallbackAction(AbstractElementalistAction action) {
		this.duration = 0.001f;
		this.action = action;
	}
	public CallbackAction(AbstractElementalistAction action, int memory) {
		this.duration = 0.001f;
		this.action = action;
		this.memory = memory;
	}

	public CallbackAction(AbstractElementalistAction action, int memory, int memory2) {
		this.duration = 0.001f;
		this.action = action;
		this.memory = memory;
		this.memory2 = memory2;
		callbackMode = 2;
	}
	
	
	public void update() {
		if(this.card != null) {
			switch(callbackMode) {
			case 1:
				card.actionCallback(memory);
				break;
			case 2:
				card.actionCallback(memory, memory2);
				break;
			}
		}
		
		if(this.action != null) {
			switch(callbackMode) {
			case 1:
				action.actionCallback(memory);
				break;
			case 2:
				action.actionCallback(memory, memory2);
				break;
			}
		}
		
		this.isDone = true;
	}

}
