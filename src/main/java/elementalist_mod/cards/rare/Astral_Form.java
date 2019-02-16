package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.AstralFormPower;

public class Astral_Form extends AbstractElementalistCard {
	public static final String ID = "elementalist:Astral_Form";
	public static final String NAME = "Astral Form";
	public static String DESCRIPTION = "When you cast from a synergizing element, gain 1 in one of your synergizing elements.";
	private static final int COST = 1;

	public Astral_Form() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_POWER_GREY_1), COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

		generatesElement = true;
		generatedElementAmount = this.magicNumber;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		/*for (AbstractPower power : AbstractDungeon.player.powers) {
			if (power.ID.contains("elementalist:") && power.ID.contains("Form")) {
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, power));
			}
		}*/

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AstralFormPower(p, p, 1), 1));
		
	}

	public AbstractCard makeCopy() {
		return new Astral_Form();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.updateCost(-1);
			initializeDescription();
		}
	}

}