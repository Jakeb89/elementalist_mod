package elementalist_mod.cards.special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.CustomTags;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.*;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Zephyr_Soul extends AbstractElementalistCard {

	public static final String ID = "elementalist:Zephyrsoul";
	public static final String NAME = "Zephyrsoul";
	public static String DESCRIPTION = "Rune. Gain !M! Air.";
	private static final int COST = 0;
	private static final int MAGIC_NUM = 1;

	public Zephyr_Soul() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.ZEPHYR_SOUL), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.SPECIAL,
				AbstractCard.CardTarget.SELF);

		this.tags.add(CustomTags.RUNE);

		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		generatesElement = true;
		generatedElementAmount = this.magicNumber;
		this.element = "Air";

		this.isEthereal = true;
		this.exhaust = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new ElementAddAction(makeOrb(element, this.magicNumber)));
		AbstractDungeon.actionManager.addToTop(new ExhaustAllRunesAction());
	}

	public AbstractCard makeCopy() {
		return new Zephyr_Soul();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			generatedElementAmount = magicNumber;
		}
	}
}
