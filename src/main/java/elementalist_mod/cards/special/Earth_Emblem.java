package elementalist_mod.cards.special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Earth_Emblem extends AbstractElementalistCard {
	public static final String ID = "elementalist:Earth_Emblem";
	public static final String NAME = "Earth Emblem";
	public static String DESCRIPTION = "elementalist:Emblem. Gain !M! elementalist:Earth.";
	private static final int COST = -2;
	private int MAGIC = 1;
	private int MAGIC_UPGRADE = 1;
	

	public Earth_Emblem() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_YELLOW_1), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = baseMagicNumber;

		this.element = Element.EARTH;
		this.generatesElement = true;
		this.generatedElementAmount = magicNumber;
		this.isEmblem = true;
		this.emblemID = "earth";
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		this.changeElement(element, this.magicNumber, "emblem");
	}

	public AbstractCard makeCopy() {
		return new Earth_Emblem();
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