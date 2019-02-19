package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.CycloneAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Cyclone extends AbstractElementalistCard {
	public static final String ID = "elementalist:Cyclone";
	public static final String NAME = "Cyclone";
	public static String DESCRIPTION = "Discard any number of cards. Gain !B! Block and 1 elementalist:Windburn for each card discarded."
										+ " NL NL Gain 1 elementalist:Air for every 2 cards discarded.";
	private static final int COST = 2;
	private static final int BLOCK = 6;
	private static final int BLOCK_UPG = 2;
	private static final int MAGIC = 2;
	private static final int MAGIC_UPG = 1;

	public Cyclone() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.CYCLONE), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK;
		this.baseMagicNumber = MAGIC;
		this.magicNumber = MAGIC;

		//addElementalCost("Air", 2);
		this.element = Element.AIR;
		this.generatesElement = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		//int cardsToDraw = cast("Air", 2) ? this.magicNumber : 0;
		AbstractDungeon.actionManager.addToBottom(new CycloneAction(p, this.block));


	}

	public AbstractCard makeCopy() {
		return new Cyclone();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(BLOCK_UPG);
			this.upgradeMagicNumber(MAGIC_UPG);
			initializeDescription();
		}
	}

}