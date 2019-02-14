package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.powers.WindburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CycloneAction extends AbstractGameAction {
	private int discardedCards = 0;
	private AbstractPlayer player;
	private int block = 0;

	public CycloneAction(AbstractPlayer player, int block) {
		this.player = player;
		this.block = block;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
	    this.duration = 0.5F;
	}

	public void update() {

		if (this.duration == 0.5F) {
			AbstractDungeon.handCardSelectScreen.open("discard", 99, true, true);
			AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
			tickDuration();
			return;
		}

		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
				// AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.player,
				// AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
				for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
					AbstractDungeon.player.hand.moveToDiscardPile(c);
					GameActionManager.incrementDiscard(false);
					c.triggerOnManualDiscard();
					AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WindburnPower(player, player, 1), 1));
					discardedCards++;
				}
			}
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
			tickDuration();
			return;
		}

		if (AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			if(discardedCards/2 > 0) {
				ElementalistMod.changeElement("Air", discardedCards/2);
			}
			tickDuration();
			this.isDone = true;
		}

		// this.isDone = true;
		return;
	}

}
