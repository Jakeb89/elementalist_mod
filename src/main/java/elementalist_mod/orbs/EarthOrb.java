package elementalist_mod.orbs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

import elementalist_mod.ElementalistMod.Element;

public class EarthOrb extends ElementOrb {
	public EarthOrb(int amount) {
		super("elementalist_earthOrb", amount);
		name = "Earth";
		this.element = Element.EARTH;
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
		AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
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