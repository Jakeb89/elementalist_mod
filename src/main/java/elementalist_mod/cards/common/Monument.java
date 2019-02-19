package elementalist_mod.cards.common;

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
	public static String DESCRIPTION = "Tiring. Deal !D! damage to target. NL NL Earthcast 1: If this card kills an enemy, permanently increase its damage by !M!.";
	private static final int COST = 0;
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

		addElementalCost(Element.EARTH, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		int incAmount = (cast(Element.EARTH, 1) ? this.magicNumber : 0);
		AbstractDungeon.actionManager.addToBottom(new MonumentAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), incAmount, this.uuid));
		
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