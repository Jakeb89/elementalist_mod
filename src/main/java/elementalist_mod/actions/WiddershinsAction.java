package elementalist_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import elementalist_mod.cards.AbstractElementalistCard;

public class WiddershinsAction extends AbstractElementalistAction {
	
	public WiddershinsAction(AbstractElementalistCard sourceCard, AbstractCreature specificTarget) {
		super(sourceCard, specificTarget);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {

		int fireDelta = getElement("Air") - getElement("Fire");
		int earthDelta = getElement("Fire") - getElement("Earth");
		int waterDelta = getElement("Earth") - getElement("Water");
		int airDelta = getElement("Water") - getElement("Air");

		changeElement("Fire", fireDelta, "widdershins");
		changeElement("Earth", earthDelta, "widdershins");
		changeElement("Water", waterDelta, "widdershins");
		changeElement("Air", airDelta, "widdershins");

		this.isDone = true;
		return;
	}

}
