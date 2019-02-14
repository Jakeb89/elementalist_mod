package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.powers.WindburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TsunamiAction extends AbstractGameAction {
	public int amount = 0;
	public TsunamiAction(int amount) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.5F;
		this.amount = amount;
	}

	public void update() {

		AbstractPlayer p = AbstractDungeon.player;
		ElementalistMod.log("TsunamiAction.update()");
		ElementalistMod.log(p.hand.group.size()+"");
		int tempStr = 0;
		
		for(AbstractCard card : p.hand.group) {
			if(card instanceof AbstractElementalistCard) {
				ElementalistMod.log(card.name);
				AbstractElementalistCard eCard = (AbstractElementalistCard)card;
				ElementalistMod.log(eCard.costElement.size()+"");
				for(int i=0; i<eCard.costElement.size(); i++) {
					ElementalistMod.log(eCard.costElement.get(i) + ": " + eCard.costElementAmount.get(i));
					if(eCard.costElement.get(i) == "Water") {
						tempStr += eCard.costElementAmount.get(i);
					}
				}
			}
		}
		
		tempStr *= amount;
		if(tempStr != 0) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, tempStr), tempStr));
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, tempStr), tempStr));
		}
	    
		this.isDone = true;
	}

}
