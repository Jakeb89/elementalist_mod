package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Pressurize extends AbstractElementalistCard {
	public static final String ID = "elementalist:Pressurize";
	public static final String NAME = "Pressurize";
	public static String DESCRIPTION = "Gain !B! Block. Gain !M! elementalist:Windburn. NL NL elementalist:Aircast 2: Deal damage equal to your elementalist:Windburn. Gain 1 elementalist:Water.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 10;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	private static final int MAGIC = 5;
	private static final int MAGIC_UP = 2;

	public Pressurize() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_GREEN_3), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);
	    this.baseBlock = BLOCK_AMT;
	    this.baseMagicNumber = MAGIC;
    	this.magicNumber = MAGIC;

		addElementalCost(Element.AIR, 2);
		
		generatesElement = true;
		generatedElementAmount = 1;
	    this.element = Element.WATER;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		
		int windburn = 0;
		if(p.hasPower(WindburnPower.POWER_ID)) {
			windburn = p.getPower(WindburnPower.POWER_ID).amount;
		}
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindburnPower(p, p, this.magicNumber), this.magicNumber));
		windburn += this.magicNumber;
		
		if (cast(Element.AIR, 2)) {
		    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, windburn)));
		    this.changeElement(Element.WATER, 1);
		}
	}

	public AbstractCard makeCopy() {
		return new Pressurize();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
			this.upgradeMagicNumber(MAGIC_UP);
			this.initializeDescription();
		}
	}
}