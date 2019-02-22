package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Spout extends AbstractElementalistCard {
	public static final String ID = "elementalist:Spout";
	public static final String NAME = "Spout";
	public static String DESCRIPTION = "elementalist:Watercast 1: Deal !D! damage. NL NL elementalist:Watercast 1: Gain !B! Block for each card in your discard pile.";
	private static final int COST = 1;
	private final int ATTACK_DMG = 8;
	private final int UPGRADE_PLUS_DMG = 4;
	private final int BLOCK = 3;
	private final int UPGRADE_BLOCK = 1;

	public Spout() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.SPOUT), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = BLOCK;

		addElementalCost(Element.WATER, 1);
		addElementalCost(Element.WATER, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		/*
		if (cast(Element.WATER, 1)) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		}

		if (getElement(Element.WATER) >= 2 && cast(Element.WATER, 1)) {
			int discardSize = AbstractDungeon.player.discardPile.size();
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block*discardSize));
		}
		*/
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			return castNow(Element.WATER, 1);
		case (1):
			queueAction(new DamageAction(singleTarget, new DamageInfo(player, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			return true;
		case (2):
			return castNow(Element.WATER, 1);
		case (3):
			int discardSize = player.discardPile.size();
			queueAction(new GainBlockAction(player, player, this.block*discardSize));
		default:
			return false;
		}
	}

	public AbstractCard makeCopy() {
		return new Spout();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(this.UPGRADE_PLUS_DMG);
			this.upgradeBlock(this.UPGRADE_BLOCK);
			initializeDescription();
		}
	}

}