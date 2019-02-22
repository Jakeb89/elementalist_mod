package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.*;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Monument extends AbstractElementalistCard {
	public static final String ID = "elementalist:Monument";
	public static final String NAME = "Monument";
	public static String DESCRIPTION = "Deal !D! damage. NL NL elementalist:Earthcast 2: Deal !D! damage. NL NL If this card kills an enemy, permanently increase its damage by !M! and Exhaust it.";
	private static final int COST = 1;
	private final int ATTACK_DMG = 4;
	private final int MAGIC = 2;
	private final int UPGRADE_MAGIC = 1;

	public Monument() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.MONUMENT), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = MAGIC;
	    this.magicNumber = this.baseMagicNumber;
		this.misc = ATTACK_DMG;
		this.keywords.add("tiring");
		this.exhaust = false;

		addElementalCost(Element.EARTH, 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		/*
		int incAmount = (castNow(Element.EARTH, 1) ? this.magicNumber : 0);
		AbstractDungeon.actionManager.addToBottom(new MonumentAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), incAmount, this.uuid));
		*/
		
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			queueAction(new MonumentAction(singleTarget, new DamageInfo(player, this.damage, this.damageTypeForTurn), this.magicNumber, this.uuid));
			return true;
		case (1):
			return castNow(Element.EARTH, 2);
		case (2):
			queueAction(new MonumentAction(singleTarget, new DamageInfo(player, this.damage, this.damageTypeForTurn), this.magicNumber, this.uuid));
		default:
			return false;
		}
	}
	
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		initializeDescription();
		baseDamage = misc;
	}

	public void applyPowers() {
		this.baseBlock = this.misc;
		super.applyPowers();
		initializeDescription();
	}

	public AbstractCard makeCopy() {
		return new Monument();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(this.UPGRADE_MAGIC);
			initializeDescription();
		}
	}

}