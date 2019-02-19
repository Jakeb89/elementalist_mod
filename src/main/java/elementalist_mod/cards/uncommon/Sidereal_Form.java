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
import elementalist_mod.powers.SiderealFormPower;

public class Sidereal_Form extends AbstractElementalistCard {
	public static final String ID = "elementalist:Sidereal_Form";
	public static final String NAME = "Sidereal Form";
	public static String DESCRIPTION = "The first time you cast an element each turn, gain 2 Air.";
	private static final int COST = 1;

	public Sidereal_Form() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.SIDEREAL_FORM), COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

		generatesElement = true;
		generatedElementAmount = this.magicNumber;
		this.element = Element.AIR;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		/*for (AbstractPower power : AbstractDungeon.player.powers) {
			if (power.ID.contains("elementalist:") && power.ID.contains("Form")) {
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, power));
			}
		}*/

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SiderealFormPower(p, p, 2), 2));
	}

	public AbstractCard makeCopy() {
		return new Sidereal_Form();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.updateCost(-1);
			initializeDescription();
		}
	}

}