package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Geomancy extends AbstractElementalistCard {
	public static final String ID = "elementalist:Geomancy";
	public static final String NAME = "Geomancy";
	public static String DESCRIPTION = "Earthcast 1: Gain !B! Block NL Watercast 1: Gain !B! Block. NL Aircast 1: Gain !B! Block.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 8;
	private static final int UPGRADE_PLUS_BLOCK = 4;

	public Geomancy() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_2), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
	    this.baseBlock = BLOCK_AMT;

		addElementalCost("Air", 1);
		addElementalCost("Water", 1);
		addElementalCost("Earth", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast("Air", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		}
		if (cast("Water", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		}
		if (cast("Earth", 1)) {
		    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		}
	}

	public AbstractCard makeCopy() {
		return new Geomancy();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			this.initializeDescription();
		}
	}
}