package elementalist_mod.cards.uncommon;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.VaccuumDrawAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Cauterize extends AbstractElementalistCard {
	public static final String ID = "elementalist:Cauterize";
	public static final String NAME = "Cauterize";
	public static String DESCRIPTION = "Firecast X: Deal !D! damage to target X times. Deal X damage to target for each Status in your hand. Exhaust X random Status cards in your hand.";
	private static final int COST = 1;
	public static final int DAMAGE = 5;
	public static final int DAMAGE_UPGRADE = 2;

	public Cauterize() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_RED_3), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DAMAGE;
		
		addElementalCost("Fire", -1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		int statusCount = 0;
		ArrayList<AbstractCard> statusList = new ArrayList<AbstractCard>();
		for(AbstractCard card : p.hand.group) {
			if(card.type == AbstractCard.CardType.STATUS) {
				statusList.add(card);
				statusCount++;
			}
		}
		
		/*if(statusCount > 0) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, statusCount)));
		}*/
		
		int firecast = this.getElement("Fire");
		
		if(firecast > 0) {
			cast("Fire");

			for(int i=0; i<firecast; i++) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, this.damage)));
			}
			
			
			while(statusList.size() > firecast) {
				statusList.remove((int)(Math.random()*statusList.size()));
			}
			
			for(AbstractCard card : statusList) {
				AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
			}
		}
	}


	public AbstractCard makeCopy() {
		return new Cauterize();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UPGRADE);
			initializeDescription();
		}
	}

}