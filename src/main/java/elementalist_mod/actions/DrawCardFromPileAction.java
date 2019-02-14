package elementalist_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import elementalist_mod.cards.AbstractElementalistCard;

public class DrawCardFromPileAction extends AbstractGameAction {

	// private static final UIStrings uiStrings =
	// CardCrawlGame.languagePack.getUIString("SkillFromDeckToHandAction");
	// public static final String[] TEXT = uiStrings.TEXT;
	private AbstractPlayer p;
	private CardGroup cardGroup;
	private AbstractElementalistCard source;

	public DrawCardFromPileAction(AbstractElementalistCard source, CardGroup cardGroup, int amount) {
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.cardGroup = cardGroup;
		this.source = source;
	}

	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) {
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : cardGroup.group) {
				if(source.customCardTest(c)) {
					tmp.addToRandomSpot(c);
				}
			}
			if (tmp.size() == 0) {
				this.isDone = true;
				return;
			}
			if (tmp.size() == 1) {
				AbstractCard card = tmp.getTopCard();
				if (this.p.hand.size() == 10) {
					cardGroup.moveToDiscardPile(card);
					source.actionCallback(card);
					this.p.createHandIsFullDialog();
				} else {
					card.unhover();
					card.lighten(true);
					card.setAngle(0.0F);
					card.drawScale = 0.12F;
					card.targetDrawScale = 0.75F;
					card.current_x = CardGroup.DRAW_PILE_X;
					card.current_y = CardGroup.DRAW_PILE_Y;
					cardGroup.removeCard(card);
					
					AbstractDungeon.player.hand.addToTop(card);
					AbstractDungeon.player.hand.refreshHandLayout();
					AbstractDungeon.player.hand.applyPowers();
					source.actionCallback(card);
				}
				this.isDone = true;
				return;
			}
			AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose "+amount+" card"+(amount!=1?"s":"")+" to move to your hand.", false);
			tickDuration();
			return;
		}
		if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				if (this.p.hand.size() == 10) {
					cardGroup.moveToDiscardPile(c);
					source.actionCallback(c);
					this.p.createHandIsFullDialog();
				} else {
					cardGroup.removeCard(c);
					this.p.hand.addToTop(c);
					source.actionCallback(c);
				}
				this.p.hand.refreshHandLayout();
				this.p.hand.applyPowers();
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
