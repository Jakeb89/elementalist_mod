package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Magma_Shot extends AbstractElementalistCard {
	public static final String ID = "elementalist:Magma_Shot";
	public static final String NAME = "Magma Shot";
	public static String DESCRIPTION = "Gain !B! Block. NL NL elementalist:Earthcast 2: Deal !D! damage then gain !M! elementalist:Fire.";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 10;
	private static final int UPGRADE_PLUS_DMG = 2;
	private static final int BLOCK_AMT = 8;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	private static final int MAGIC_NUM = 1;

	public Magma_Shot() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.MAGMA_SHOT), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
	    this.baseBlock = BLOCK_AMT;
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		addElementalCost(Element.EARTH, 2);

		generatesElement = true;
		generatedElementAmount = 1;
	    this.element = Element.FIRE;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

	    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		if (cast(Element.EARTH, 2)) {
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL)
			);
			changeElement(Element.FIRE, this.magicNumber);
		}
	}

	public AbstractCard makeCopy() {
		return new Magma_Shot();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			upgradeMagicNumber(1);
			this.initializeDescription();
			generatedElementAmount = magicNumber;
		}
	}
}