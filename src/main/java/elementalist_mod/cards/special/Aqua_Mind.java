package elementalist_mod.cards.special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.CustomTags;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.*;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Aqua_Mind extends AbstractElementalistCard {

	public static final String ID = "elementalist:Aquamind";
	public static final String NAME = "Aquamind";
	public static String DESCRIPTION = "elementalist:Rune. Gain !M! elementalist:Water.";
	private static final int COST = 0;
	private static final int MAGIC_NUM = 1;

	public Aqua_Mind() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.AQUA_MIND), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.SPECIAL,
				AbstractCard.CardTarget.SELF);

		this.tags.add(CustomTags.RUNE);

		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		generatesElement = true;
		generatedElementAmount = this.magicNumber;
		this.element = Element.WATER;

		this.isEthereal = true;
		this.exhaust = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new ElementAddAction(makeOrb(element, this.magicNumber)));
		AbstractDungeon.actionManager.addToTop(new ExhaustAllRunesAction());
	}

	public AbstractCard makeCopy() {
		return new Aqua_Mind();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			generatedElementAmount = magicNumber;
		}
	}
}
