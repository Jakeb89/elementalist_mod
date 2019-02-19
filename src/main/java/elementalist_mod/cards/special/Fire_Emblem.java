package elementalist_mod.cards.special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Fire_Emblem extends AbstractElementalistCard {
	public static final String ID = "elementalist:Fire_Emblem";
	public static final String NAME = "Fire Emblem";
	public static String DESCRIPTION = "elementalist:Emblem. Gain !M! elementalist:Fire.";
	private static final int COST = -2;
	private int MAGIC = 1;
	private int MAGIC_UPGRADE = 1;
	

	public Fire_Emblem() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_RED_1), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = baseMagicNumber;

		this.element = Element.FIRE;
		this.generatesElement = true;
		this.generatedElementAmount = magicNumber;
		this.isEmblem = true;
		this.emblemID = "fire";
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		this.changeElement(element, this.magicNumber, "emblem");
	}

	public AbstractCard makeCopy() {
		return new Fire_Emblem();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(MAGIC_UPGRADE);
			this.magicNumber = baseMagicNumber;
			this.generatedElementAmount = magicNumber;
			initializeDescription();
		}
	}

}