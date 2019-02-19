package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.LunarFormPower;

public class Lunar_Form extends AbstractElementalistCard {
	public static final String ID = "elementalist:Lunar_Form";
	public static final String NAME = "Lunar Form";
	public static String DESCRIPTION = "The first time you cast an element each turn, gain 2 Water.";
	private static final int COST = 1;

	public Lunar_Form() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.LUNAR_FORM), COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

		generatesElement = true;
		generatedElementAmount = this.magicNumber;
		this.element = Element.WATER;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		/*for (AbstractPower power : AbstractDungeon.player.powers) {
			if (power.ID.contains("elementalist:") && power.ID.contains("Form")) {
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, power));
			}
		}*/

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LunarFormPower(p, p, 2), 2));
	}

	public AbstractCard makeCopy() {
		return new Lunar_Form();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.updateCost(-1);
			initializeDescription();
		}
	}

}