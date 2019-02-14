package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.VaccuumDrawAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Ignition extends AbstractElementalistCard {
	public static final String ID = "elementalist:Ignition";
	public static final String NAME = "Ignition";
	public static String DESCRIPTION = "Deal !D! damage 3 times. NL NL Aircast X: Deal !D! damage X times. Gain X-2 Fire.";
	private static final int COST = 2;
	public static final int DAMAGE = 3;
	public static final int DAMAGE_UPGRADE = 2;

	public Ignition() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_GREEN_4), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DAMAGE;
		
		addElementalCost("Air", -1);
		this.generatesElement = true;
		this.element = "Fire";
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));

		int air = getElement("Air");
		if (air >= 1) {
			cast("Air", 0);
			changeElement("Air", -air);
			if(air >= 3) {
				changeElement("Fire", air-2);
			}
			for(int i=0; i<air; i++) {
				AbstractDungeon.actionManager
				.addToBottom(new DamageAction(target, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));
			}
		}
	}


	public AbstractCard makeCopy() {
		return new Ignition();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UPGRADE);
			initializeDescription();
		}
	}

}