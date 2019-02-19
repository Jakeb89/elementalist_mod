package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.ConfluencePower;

public class Confluence extends AbstractElementalistCard {

	public static final String ID = "elementalist:Confluence";
	public static final String NAME = "Confluence";
	public static String DESCRIPTION = "At the beginning of your turn gain 1 point in each elementalist:synergizing element.";
	private static final int COST = 1;

	public Confluence() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.CONFLUENCE), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConfluencePower(p, p, 1), 1));
	}

	public AbstractCard makeCopy() {
		return new Confluence();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(0);
		}
	}
}
