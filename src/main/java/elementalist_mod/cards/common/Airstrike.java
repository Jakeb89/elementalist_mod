package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.helpers.BaseModCardTags;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Airstrike extends AbstractElementalistCard{
	public static final String ID = "elementalist:Airstrike";
	public static final String NAME = "Airstrike";
	public static String DESCRIPTION = "Deal !D! damage. NL Aircast 1: Draw !M! card(s).";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int MAGIC_NUM = 1;
	private static final int UPGRADE_MAGIC_NUM = 1;

	public Airstrike() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.AIRSTRIKE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.BASIC,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = MAGIC_NUM;
		this.tags.add(AbstractCard.CardTags.STRIKE);
		this.tags.add(BaseModCardTags.BASIC_STRIKE);
		
		addElementalCost("Air", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		
		//Aircast code
		if (cast("Air", 1)) {
			int drawAmount = MAGIC_NUM;
			if(upgraded) drawAmount++;
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, drawAmount));
		}
	}

	public AbstractCard makeCopy() {
		return new Airstrike();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeMagicNumber(UPGRADE_MAGIC_NUM);
		}
	}
}