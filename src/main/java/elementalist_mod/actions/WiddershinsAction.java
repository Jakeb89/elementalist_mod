package elementalist_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;

public class WiddershinsAction extends AbstractElementalistAction {
	
	public WiddershinsAction(AbstractElementalistCard sourceCard, AbstractCreature specificTarget) {
		super(sourceCard, specificTarget);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	public void update() {

		int fireDelta = getElement(Element.AIR) - getElement(Element.FIRE);
		int earthDelta = getElement(Element.FIRE) - getElement(Element.EARTH);
		int waterDelta = getElement(Element.EARTH) - getElement(Element.WATER);
		int airDelta = getElement(Element.WATER) - getElement(Element.AIR);

		changeElement(Element.FIRE, fireDelta, "widdershins");
		changeElement(Element.EARTH, earthDelta, "widdershins");
		changeElement(Element.WATER, waterDelta, "widdershins");
		changeElement(Element.AIR, airDelta, "widdershins");

		this.isDone = true;
		return;
	}

}
