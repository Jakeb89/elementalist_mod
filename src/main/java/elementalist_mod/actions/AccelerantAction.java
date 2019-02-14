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

public class AccelerantAction extends AbstractGameAction {
	private AbstractPlayer player;
	private AbstractMonster target;
	private int damage = 0;

	public AccelerantAction(AbstractPlayer player, AbstractMonster target, int damage) {
		this.player = player;
		this.target = target;
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
	    this.duration = 0.5F;
	    this.damage = damage;
	}

	public void update() {
		
		int windburn = 0;
		if(target.hasPower(WindburnPower.POWER_ID)) {
			windburn = target.getPower(WindburnPower.POWER_ID).amount;
			ElementalistMod.log("Target had " + target.getPower(WindburnPower.POWER_ID).amount + " Windburn.");
		}

		ElementalistMod.log("AccelerantAction has " + damage + " damage.");
		ElementalistMod.log("It will do a total of " + windburn*damage + " damage.");
		AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, windburn*damage)));

		this.isDone = true;
	}

}
