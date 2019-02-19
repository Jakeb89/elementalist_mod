package elementalist_mod.orbs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;

import elementalist_mod.ElementalistMod.Element;

public class FireOrb extends ElementOrb {
	public FireOrb(int amount) {
		super("elementalist_fireOrb", amount);
		name = "Fire";
		this.element = Element.FIRE;
		description = "The quanitity of "+element+" energy you have accumulated.";
		if(AbstractDungeon.player.hasRelic("Magus Staff")) {
			description += " NL NL If your Magus Staff has any charges, you can click this orb to gain 1 "+element+".";
		}
		this.amount = amount;
	}


	public void onShatter() {
		/*if (this.upgraded) {
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, new Anchor()));
			AbstractDungeon.actionManager
					.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 10));
		}*/
	}

	@Override
	public AbstractOrb makeCopy() {
		// TODO Auto-generated method stub
		return new FireOrb(amount);
	}

	public void triggerEvokeAnimation() {
		CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
		AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
	}

}

// Methods
// public void onStartOfTAnchor()
// public void onEndOfTAnchor()
// public void applyFocus()
// public void receivePostPowerApplySubscriber(AbstractPower p, AbstractCreature
// target, AbstractCreature source);
// public void receiveCardUsed(AbstractCard c);

// We can add more hooks by patching AbstractCreature if necessary