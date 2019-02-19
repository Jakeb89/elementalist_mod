package elementalist_mod.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Firestrike extends AbstractElementalistCard{
	public static final String ID = "elementalist:Firestrike";
	public static final String NAME = "Firestrike";
	public static String DESCRIPTION = "Deal !D! damage. NL elementalist:Firecast 1: Deal !D! damage to ALL enemies.";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_PLUS_DMG = 3;

	public Firestrike() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.FIRESTRIKE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.BASIC,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.tags.add(AbstractCard.CardTags.STRIKE);
		this.tags.add(BaseModCardTags.BASIC_STRIKE);

		addElementalCost(Element.FIRE, 1);

	    this.isMultiDamage = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		

		if (cast(Element.FIRE, 1)) {
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
			for(AbstractMonster enemy : getAllLivingEnemies()) {
			    
			}
		}
	}

	public AbstractCard makeCopy() {
		return new Firestrike();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}