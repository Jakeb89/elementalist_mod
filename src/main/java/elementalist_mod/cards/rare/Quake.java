package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Quake extends AbstractElementalistCard {
	public static final String ID = "elementalist:Quake";
	public static final String NAME = "Quake";
	public static String DESCRIPTION = "Exhaust. NL elementalist:Earthcast X: Deal damage to ALL enemies equal to the sum total cost of all cards in your discard pile. Do this X times.";
	public static String DESCRIPTION_UPGRADED = "elementalist:Tiring. NL elementalist:Earthcast X: Deal damage to ALL enemies equal to the sum total cost of all cards in your discard pile. Do this X times.";
	private static final int COST = 2;

	public Quake() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_YELLOW_1), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);

		addElementalCost(Element.EARTH, -1);
		this.keywords.add("total cost");
		this.exhaust = true;
		
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		int casts = cast(Element.EARTH);
		if(upgraded) casts += 2;
		
		int damage = 0;
		
		for(AbstractCard card : p.discardPile.group) {
			if(card instanceof AbstractElementalistCard) {
				damage += ((AbstractElementalistCard) card).getTotalCost();
			}else {
				damage += Math.max(0, card.cost);
			}
		}
		
		if(damage == 0) return;
		
		for(int i=0; i<casts; i++) {
			for(AbstractMonster enemy : this.getAllLivingEnemies()) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(enemy, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SMASH));
			}
		}

	}

	public AbstractCard makeCopy() {
		return new Quake();
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