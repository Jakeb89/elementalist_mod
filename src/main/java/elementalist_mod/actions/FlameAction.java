package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.common.Flame;
import elementalist_mod.powers.WindburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlameAction extends AbstractGameAction {

	public FlameAction(int amount) {
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = 0.5F;
		this.amount = amount;
	}

	public void update() {

		for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
			if ((c instanceof Flame)) {
				c.baseDamage += this.amount;
				c.applyPowers();
			}
		}
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if ((c instanceof Flame)) {
				c.baseDamage += this.amount;
				c.applyPowers();
			}
		}
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if ((c instanceof Flame)) {
				c.baseDamage += this.amount;
				c.applyPowers();
			}
		}
		for (AbstractCard c : AbstractDungeon.player.limbo.group) {
			if ((c instanceof Flame)) {
				c.baseDamage += this.amount;
				c.applyPowers();
			}
		}

		this.isDone = true;
	}

}
