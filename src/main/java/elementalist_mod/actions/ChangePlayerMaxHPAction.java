package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
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

public class ChangePlayerMaxHPAction extends AbstractGameAction {
	private int delta = 0;

	public ChangePlayerMaxHPAction(int delta) {
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
	    this.duration = 0.5F;
	    this.delta = delta;
	}

	public void update() {
		
		AbstractDungeon.player.increaseMaxHp(delta, false);
		
		this.isDone = true;
	}

}
