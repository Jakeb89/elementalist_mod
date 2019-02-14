package elementalist_mod.actions;

import elementalist_mod.cards.AbstractElementalistCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

public class VaccuumDrawAction extends AbstractElementalistAction {
	int drawCount = 0;
	int step = 0;
	int additionalDraw = 0;
	int cardsDrawn = 0;

	public VaccuumDrawAction(AbstractElementalistCard sourceCard, AbstractCreature specificTarget, int drawCount) {
		super(sourceCard, specificTarget);
		this.drawCount = drawCount;

		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {
		step = 0;
		doStep();
		this.isDone = true;
	}

	private void doStep() {
		if(step < 3 + additionalDraw && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
			if (AbstractDungeon.player.drawPile.isEmpty()) {
				AbstractDungeon.actionManager.addToTop(new CallbackAction(this));
				AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
			} else {
				cardsDrawn++;
				AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
				if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
					additionalDraw++;
				}
				AbstractDungeon.actionManager.addToTop(new CallbackAction(this));
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
				step++;
			}
			
		}else {
			for (int i = 0; i < cardsDrawn; i++) {
				AbstractDungeon.actionManager.addToTop(new DamageAction(specificTarget, new DamageInfo(AbstractDungeon.player, sourceCard.damage, DamageInfo.DamageType.NORMAL),
					AbstractGameAction.AttackEffect.SLASH_VERTICAL));
			}
		}

	}

	public void actionCallback(int memory) {
		doStep();
	}
}
