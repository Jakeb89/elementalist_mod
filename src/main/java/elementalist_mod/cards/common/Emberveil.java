package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Emberveil extends AbstractElementalistCard {
	public static final String ID = "elementalist:Emberveil";
	public static final String NAME = "Emberveil";
	public static String DESCRIPTION = "Ward. NL Bloodied: Reduce incoming damage by !M! and discard Emberveil. Add 1 Burn to your discard pile and gain 1 Fire.";
	private static final int COST = -2;
	private static final int MAGIC_NUM = 5;
	private static final int UPGRADE_MAGIC_NUM = 5;

	public Emberveil() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_RED_4), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.NONE);
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		generatesElement = true;
		generatedElementAmount = 1;
	    this.element = "Fire";
		
		this.isWard = true;
		this.activeWard = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		// Shouldn't ever occur.
	}
	
	@Override
	public boolean willSuccessfullyGainElement() {
		return true;
	}

	@Override
	public int onLoseHp(int damageAmount) {
		//ElementalistMod.log("AbstractElementalistCard.onLoseHp("+damageAmount+")");
		damageAmount = super.onLoseHp(damageAmount);

		if (this.isWard && this.activeWard && damageAmount>0) {
			this.activeWard = false;
			damageAmount = Math.max(0, damageAmount - this.magicNumber);
			AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(new Burn(), 1));
			changeElement("Fire", 1);
		}

		return damageAmount;
	}

	public AbstractCard makeCopy() {
		return new Emberveil();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_MAGIC_NUM);
			initializeDescription();
		}
	}

}