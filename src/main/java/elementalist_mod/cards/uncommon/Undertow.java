package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.AerialDodgePower;
import elementalist_mod.powers.WindblightPower;

public class Undertow extends AbstractElementalistCard {
	public static final String ID = "elementalist:Undertow";
	public static final String NAME = "Undertow";
	public static String DESCRIPTION = "Target loses all Weak. Gain !B! Block. NL NL elementalist:Watercast 2: Gain 1 Strength for every !M! Weak the target just lost.";
	private static final int COST = 0;
	private static final int MAGIC = 3;
	private static final int MAGIC_UP = -1;
	private static final int BLOCK = 6;
	private static final int BLOCK_UP = 2;
	private AbstractPower weakPower = null;
	private int weakRemoved = 0;

	public Undertow() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_BLUE_1), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);

		this.baseMagicNumber = MAGIC;
		this.magicNumber = MAGIC;
		this.baseBlock = BLOCK;
		
		addElementalCost(Element.WATER, 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			weakPower = null;
			weakRemoved = 0;
			if(singleTarget.hasPower(WeakPower.POWER_ID)) {
				weakPower = singleTarget.getPower(WeakPower.POWER_ID);
				weakRemoved = weakPower.amount;
			}
			queueAction(new RemoveSpecificPowerAction(singleTarget, singleTarget, weakPower));
			return true;
			
		case (1):
			queueAction(new GainBlockAction(player, player, this.block));
			return true;
			
		case (2):
			return cast(Element.WATER, 2);
		
		case (3):
			int strToGain = weakRemoved / Math.max(1, this.magicNumber);
			queueAction(new ApplyPowerAction(player, player, new StrengthPower(player, strToGain), strToGain));
			
		default:
			return false;
		}
	}

	public AbstractCard makeCopy() {
		return new Undertow();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			if(this.magicNumber > 1) {
				this.upgradeMagicNumber(MAGIC_UP);
			}
			this.upgradeBlock(BLOCK_UP);
			initializeDescription();
		}
	}

}