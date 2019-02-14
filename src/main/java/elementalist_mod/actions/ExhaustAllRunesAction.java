package elementalist_mod.actions;

import elementalist_mod.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustAllRunesAction extends AbstractGameAction {

	public ExhaustAllRunesAction() {
		//ElementalistMod.log("ExhaustRunesAction()");
		this.actionType = AbstractGameAction.ActionType.EXHAUST;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {
		//ElementalistMod.log("ElementAddAction.update()");

		if (!this.isDone) {
			for (AbstractCard card : AbstractDungeon.player.hand.group) {
				if(card.tags.contains(CustomTags.RUNE)) {
					AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
				}
			}
			this.isDone = true;
		}
	}
}
