package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.DrawCardFromPileAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Mage_Hand extends AbstractElementalistCard {
	public static final String ID = "elementalist:Mage_Hand";
	public static final String NAME = "Mage Hand";
	public static String DESCRIPTION = "Choose !M! card(s) from your draw pile to add to your hand.";
	private static final int COST = 1;
	private static final int MAGIC_NUM = 1;
	private static final int MAGIC_UPG = 1;

	public Mage_Hand() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_2), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		AbstractDungeon.actionManager.addToBottom(new DrawCardFromPileAction(this, p.drawPile, this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Mage_Hand();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(MAGIC_UPG);
			this.initializeDescription();
		}
	}
}