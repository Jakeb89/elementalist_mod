package elementalist_mod.cards.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.DrawCardFromPileAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Overheat extends AbstractElementalistCard {
	public static final String ID = "elementalist:Overheat";
	public static final String NAME = "Overheat";
	public static String DESCRIPTION = "Firecast 1: Add a random card from your exhaust pile to your hand. Add a Burn to your discard pile.";
	public static String DESCRIPTION_UPGRADE = "Firecast 1: Add a chosen card from your exhaust pile to your hand. Add a Burn to your discard pile.";
	private static final int COST = 1;

	public Overheat() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.OVERHEAT), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);

		addElementalCost("Fire", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		if(!upgraded) {

			if (cast("Fire", 1)) {
				if(!p.exhaustPile.isEmpty()) {
					int randomCardIndex = (int) (Math.random()*p.exhaustPile.group.size());
					AbstractCard randomCard = p.exhaustPile.group.get(randomCardIndex);
					
					p.hand.addToHand(randomCard);
					if ((AbstractDungeon.player.hasPower("Corruption")) && (randomCard.type == AbstractCard.CardType.SKILL)) {
						randomCard.setCostForTurn(-9);
			        }
					p.exhaustPile.removeCard(randomCard);
					randomCard.unhover();
					randomCard.fadingOut = false;
				}
				Burn burn = new Burn();
				//p.exhaustPile.addToTop(burn);
			    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(burn, 1));
			}
		}else {

			if (cast("Fire", 1)) {
				if(!p.exhaustPile.isEmpty()) {
				    AbstractDungeon.actionManager.addToBottom(new DrawCardFromPileAction(this, AbstractDungeon.player.exhaustPile, 1));
				}
			}

		}

	}
	
	public void actionCallback(AbstractCard card) {

		if ((AbstractDungeon.player.hasPower("Corruption")) && (card.type == AbstractCard.CardType.SKILL)) {
			card.setCostForTurn(-9);
        }
		card.unhover();
		card.fadingOut = false;

		Burn burn = new Burn();
	    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(burn, 1));
	   
	}

	public AbstractCard makeCopy() {
		return new Overheat();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADE;
			initializeDescription();
		}
	}

}