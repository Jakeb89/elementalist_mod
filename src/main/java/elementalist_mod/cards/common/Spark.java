package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Spark extends AbstractElementalistCard {
	public static final String ID = "elementalist:Spark";
	public static final String NAME = "Spark";
	public static String DESCRIPTION = "Aircast 1: Draw 1 card. NL NL Aircast 1: Deal !D! damage to a random enemy.";
	public static String UPGRADE_DESCRIPTION = "Aircast 1: Draw 1 card. NL NL Aircast 1: Deal !D! damage to ALL enemies.";
	private static final int COST = 0;
	private static final int ATTACK_DMG = 8;
	private static final int UPGRADE_PLUS_DMG = 0;

	public Spark() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.SPARK), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
		this.baseDamage = ATTACK_DMG;

		addElementalCost("Air", 1);
		addElementalCost("Air", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast("Air", 1)) {
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
		}

		if (getElement("Air") >= 2 && cast("Air", 1)) {
			AbstractMonster randomEnemy = pickRandomLivingEnemy();
			if (randomEnemy != null) {
				if(!upgraded) {
					AbstractDungeon.actionManager.addToBottom(new ThunderStrikeAction(randomEnemy, new DamageInfo(p, this.damage, this.damageTypeForTurn), 1));
				}else {
					for(AbstractMonster enemy : getAllLivingEnemies()) {
						AbstractDungeon.actionManager.addToBottom(new ThunderStrikeAction(enemy, new DamageInfo(p, this.damage, this.damageTypeForTurn), 1));
					}
				}
			}
		}
	}

	public AbstractCard makeCopy() {
		return new Spark();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

}