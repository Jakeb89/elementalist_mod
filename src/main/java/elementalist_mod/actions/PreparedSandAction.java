package elementalist_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;

public class PreparedSandAction extends AbstractElementalistAction {
	
	public PreparedSandAction(AbstractElementalistCard sourceCard, AbstractCreature specificTarget) {
		super(sourceCard, specificTarget);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {

		if (sourceCard.cast(Element.EARTH, 1)) {

			for (AbstractMonster enemy : this.getAllLivingEnemies()) {
				if (!intentContainsAttack(enemy.intent)) {
					AbstractDungeon.actionManager.addToBottom(
						new DamageAction(enemy, new DamageInfo(AbstractDungeon.player, sourceCard.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
				} else {
					AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, sourceCard.block));
				}
			}
		}

		this.isDone = true;
		return;
	}

}
