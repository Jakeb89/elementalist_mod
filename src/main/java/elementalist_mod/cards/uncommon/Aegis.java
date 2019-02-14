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

public class Aegis extends AbstractElementalistCard {
	public static final String ID = "elementalist:Aegis";
	public static final String NAME = "Aegis";
	public static String DESCRIPTION = "Earthcast 2: Gain !B! Block for each card in your hand.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 8;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Aegis() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_YELLOW_2), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
	    this.baseBlock = BLOCK_AMT;

		addElementalCost("Earth", 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast("Earth", 2)) {
			int cardCount = p.hand.size();
		    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block*cardCount));
		}
	}

	public AbstractCard makeCopy() {
		return new Aegis();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			this.initializeDescription();
		}
	}
}