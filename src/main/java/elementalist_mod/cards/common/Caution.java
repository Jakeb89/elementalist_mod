package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Caution extends AbstractElementalistCard {

	public static final String ID = "elementalist:Caution";
	public static final String NAME = "Caution";
	public static String DESCRIPTION = "Gain !B! Block. NL Draw !M! card.";
	public static String DESCRIPTION_UPGRADED = "Gain !B! Block. NL Draw !M! cards.";
	private static final int COST = 2;
	private static final int BLOCK_AMT = 15;
	private static final int UPGRADE_PLUS_BLOCK = 5;
	private static final int MAGIC = 1;
	private static final int UPGRADE_MAGIC = 1;

	public Caution() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_4), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK_AMT;
		this.baseMagicNumber = MAGIC;
		this.magicNumber = MAGIC;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		for(int i=0; i<this.magicNumber; i++) {
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		}
	}

	public AbstractCard makeCopy() {
		return new Caution();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
			this.upgradeMagicNumber(UPGRADE_MAGIC);
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}
