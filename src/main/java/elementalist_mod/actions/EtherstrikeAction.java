package elementalist_mod.actions;

import elementalist_mod.cards.AbstractElementalistCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EtherstrikeAction extends AbstractElementalistAction {

	int drawCount = 0;

	public EtherstrikeAction(AbstractElementalistCard sourceCard, AbstractCreature specificTarget) {
		super(sourceCard, specificTarget);

		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {

		AbstractDungeon.actionManager.addToBottom(
			new DamageAction(specificTarget, new DamageInfo(AbstractDungeon.player, sourceCard.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

		String[] highestElements = getHighestElements();
		
		for(int i=0; i<highestElements.length; i++) {
			if(getElement(highestElements[i]) > 0) {
				changeElement(highestElements[i], -1);
			}
		}

		this.isDone = true;
		return;
	}

}
