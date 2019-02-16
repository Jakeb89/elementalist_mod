package elementalist_mod.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Autopetrify extends AbstractElementalistCard {
	public static final String ID = "elementalist:Autopetrify";
	public static final String NAME = "Autopetrify";
	public static String DESCRIPTION = "Ward. NL NL Bloodied: Gain !M! Plated Armor and 1 Earth. Exhaust.";
	private static final int COST = 0;
	private int MAGIC = 2;
	private int MAGIC_UPGRADE = 1;
	

	public Autopetrify() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.AUTOPETRIFY), COST, DESCRIPTION, AbstractCard.CardType.STATUS, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = baseMagicNumber;

		generatesElement = true;
		generatedElementAmount = 1;
	    this.element = "Earth";
		
		this.isWard = true;
		this.activeWard = true;
		this.retain = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

	}
	
	@Override
	public boolean willSuccessfullyGainElement() {
		return true;
	}

	@Override
	public int onLoseHp(int damageAmount) {
		//ElementalistMod.log("AbstractElementalistCard.onLoseHp("+damageAmount+")");
		damageAmount = super.onLoseHp(damageAmount);

		if (this.isWard && this.activeWard) {
			this.activeWard = false;
			AbstractPlayer p = AbstractDungeon.player;
			
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, p.hand));
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
			
			changeElement("Earth", 1);
		}

		return damageAmount;
	}

	public AbstractCard makeCopy() {
		return new Autopetrify();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(MAGIC_UPGRADE);
			this.magicNumber = MAGIC + MAGIC_UPGRADE;
			initializeDescription();
		}
	}

}