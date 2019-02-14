package elementalist_mod.cards.common;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.TooltipInfo;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Check_The_Pins extends AbstractElementalistCard {
	public static final String ID = "elementalist:Check_The_Pins";
	public static final String NAME = "How do I get started modding?";
	public static String DESCRIPTION = "Check the pins.";
	private static final int COST = -2;
	private int MAGIC = 1;
	

	public Check_The_Pins() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREEN_1), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = baseMagicNumber;
		
		//this.getCustomTooltips().add(new TooltipInfo("Check the Pins.", "For fuck's sake, please just check the pins."));

		this.element = "";
		this.generatesElement = true;
		this.generatedElementAmount = magicNumber;
		this.isEmblem = true;
		this.emblemID = "air";
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
	}

	public AbstractCard makeCopy() {
		return new Check_The_Pins();
	}

	public void upgrade() {
	}

}