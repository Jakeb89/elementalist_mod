package elementalist_mod.actions;

import elementalist_mod.cards.AbstractElementalistCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class RisingTidesAction extends AbstractElementalistAction {

	int tides = 0;

	public RisingTidesAction(int tides) {
		super();

		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = Settings.ACTION_DUR_FAST;
		this.target = specificTarget;
		this.tides = tides;
	}

	public void update() {
		
		for(int i=0; i<tides; i++) {
			target = AbstractDungeon.getRandomMonster();
			AbstractDungeon.actionManager.addToBottom( new ApplyPowerAction( target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
			AbstractDungeon.actionManager.addToBottom( new ApplyPowerAction( target, AbstractDungeon.player, new WeakPower(target, 1, false), 1));
		}

		this.isDone = true;
		return;
	}

}
