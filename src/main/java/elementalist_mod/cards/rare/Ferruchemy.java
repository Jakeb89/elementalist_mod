package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.ChangePlayerMaxHPAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Ferruchemy extends AbstractElementalistCard {
	public static final String ID = "elementalist:Ferruchemy";
	public static final String NAME = "Ferruchemy";
	public static String DESCRIPTION = "Exhaust. NL NL Firecast 2: Gain 1 Max HP for every 100 gold you have. NL NL Earthcast 2: Gain 25 Gold.";
	public static String DESCRIPTION_UPGRADED = "Tiring. NL NL Firecast 2: Gain 1 Max HP for every 100 gold you have. NL NL Earthcast 2: Gain 25 Gold.";
	private static final int COST = 1;

	public Ferruchemy() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_2), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);

		addElementalCost("Fire", 2);
		addElementalCost("Earth", 2);
		this.exhaust = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast("Fire", 2)) {
			int gold = p.gold;
			if(gold >= 100) {
			    AbstractDungeon.actionManager.addToBottom(new ChangePlayerMaxHPAction(gold/100));
			    AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, gold/100));
			}
		}
		
		int increaseGold = 25;
		if (cast("Earth", 2)) {
		    AbstractDungeon.player.gainGold(increaseGold);
	        for (int i = 0; i < increaseGold; i++) {
	        	double dir = Math.random()*3.14*2*i/increaseGold;
	        	double dis = Math.random()*200;
	        	float goldX = (float) (p.hb.cX + Math.cos(dir)*dis);
	        	float goldY = (float) (p.hb.cX + Math.sin(dir)*dis);
	            AbstractDungeon.effectList.add(new GainPennyEffect(p, goldX, goldY, p.hb.cX, p.hb.cY, true));
	          }
		}
	}

	public AbstractCard makeCopy() {
		return new Ferruchemy();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.exhaust = false;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}