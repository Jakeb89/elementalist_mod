package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.orbs.GolemOrb;
import elementalist_mod.patches.*;

public class Golemancy extends AbstractElementalistCard {

	public static final String ID = "elementalist:Golemancy";
	public static final String NAME = "Golemancy";
	public static String DESCRIPTION = "Create 1 Golem.";
	private static final int COST = 1;
	private static final int COST_UPDATE = 0;

	public Golemancy() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_YELLOW_1), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);

		//addElementalCost(Element.EARTH, 1);
	}
	public void use(AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			//return castNow(Element.EARTH, 1);
		    AbstractDungeon.actionManager.addToBottom(new ChannelAction(new GolemOrb()));
			
		default:
			return false;
		}
	}

	public AbstractCard makeCopy() {
		return new Golemancy();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.updateCost(COST_UPDATE);
		}
	}
}
