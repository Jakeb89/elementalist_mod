package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.*;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Prepared_Sand extends AbstractElementalistCard {
	public static final String ID = "elementalist:Prepared_Sand";
	public static final String NAME = "Prepared Sand";
	public static String DESCRIPTION = "Earthcast 1: Deal !D! damage to each enemy that doesn't intend to attack. NL Gain !B! Block for each enemy that intends to attack.";
	private static final int COST = 0;
	private static final int ATTACK_DMG = 3;
	private static final int UPGRADE_PLUS_DMG = 2;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Prepared_Sand() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.PREPARED_SAND), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ALL);
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = BLOCK_AMT;
		
		addElementalCost(Element.EARTH, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new PreparedSandAction(this, null));
	}

	public AbstractCard makeCopy() {
		return new Prepared_Sand();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}

}