package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Inferno extends AbstractElementalistCard {
	public static final String ID = "elementalist:Inferno";
	public static final String NAME = "Inferno";
	public static String DESCRIPTION = "Firecast 2: Deal !D! damage. Exhaust a random card in your hand. NL NL Repeat the effect of this card until you have less than 2 Fire, your hand is empty or the target is dead.";
	public static final int DAMAGE = 12;
	public static final int DAMAGE_UPGRADE = 4;
	private static final int COST = 2;
	private AbstractMonster useTarget = null;

	public Inferno() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_RED_1), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DAMAGE;
		
		addElementalCost("Fire", 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		useTarget = m;
		
		if(getElement("Fire") >= 2) {
			doStep();
		}
	}
	
	public void doStep() {
		if(useTarget.isDying || useTarget.isDead) return;
		
		if(cast("Fire", 2)) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(useTarget, new DamageInfo(AbstractDungeon.player, this.damage), AbstractGameAction.AttackEffect.FIRE));

			if(AbstractDungeon.player.hand.size() < 1) return;
			
			AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
			AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));

			AbstractDungeon.actionManager.addToBottom(new CallbackAction(this));
		}
	}
	
	public void actionCallback(int value) {
		if(getElement("Fire") < 2) return;
		if(AbstractDungeon.player.hand.size() < 1) return;
		
		doStep();
	}

	public AbstractCard makeCopy() {
		return new Inferno();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UPGRADE);
			this.initializeDescription();
		}
	}
}