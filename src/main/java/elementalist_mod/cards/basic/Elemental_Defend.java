package elementalist_mod.cards.basic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.*;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Elemental_Defend extends AbstractElementalistCard {

	public static final String ID = "elementalist:Elemental_Defend";
	// private static final CardStrings cardStrings =
	// CardCrawlGame.languagePack.getCardStrings(ID);
	// public static final String NAME = cardStrings.NAME;
	// public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String NAME = "Elemental Defend";
	public static String DESCRIPTION = "Gain !B! Block. NL Elementize 1.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Elemental_Defend() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.ELEMENTAL_DEFEND), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);
		this.tags.add(BaseModCardTags.BASIC_DEFEND);
		this.baseBlock = BLOCK_AMT;

		generatesElement = true;
		generatedElementAmount = 1;
		customDescription = DESCRIPTION;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

		// AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new
		// MagicCircleEffect(p.hb.cX, p.hb.cY), 0.5F));
		AbstractDungeon.actionManager.addToBottom(new ElementAddAction(makeOrb(element, 1)));
	}

	public AbstractCard makeCopy() {
		return new Elemental_Defend();
	}

	@Override
	public boolean isDefend() {
		return true;
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}
