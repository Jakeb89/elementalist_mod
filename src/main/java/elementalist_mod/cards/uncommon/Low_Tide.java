package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.actions.RisingTidesAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Low_Tide extends AbstractElementalistCard {
	public static final String ID = "elementalist:Low_Tides";
	public static final String NAME = "Low Tide";
	public static String DESCRIPTION = "Gain !B! Block. NL NL Watercast 2: For every !M! cards in your discard pile, apply 2 Weak to a random enemy.";
	
	private static final int COST = 1;
	public static final int BLOCK = 10;
	public static final int BLOCK_UP = 2;
	public static final int MAGIC_NUM = 5;
	public static final int MAGIC_NUM_UPGRADE = -2;

	public Low_Tide() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.LOW_TIDE), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = MAGIC_NUM;
		this.baseBlock = BLOCK;
		
		addElementalCost("Water", 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, 0));

	}
	
	@Override
	public void actionCallback(int value) {
		int tides = 0;
		if (cast("Water", 2)) {
			tides = AbstractDungeon.player.discardPile.size() / this.magicNumber;
			
			for(int i=0; i<tides; i++) {
				AbstractMonster randomTarget = AbstractDungeon.getRandomMonster();
				//AbstractDungeon.actionManager.addToTop( new ApplyPowerAction( randomTarget, AbstractDungeon.player, new VulnerablePower(randomTarget, 2, false), 2));
				AbstractDungeon.actionManager.addToTop( new ApplyPowerAction( randomTarget, AbstractDungeon.player, new WeakPower(randomTarget, 2, false), 2));
			}
		}
	}


	public AbstractCard makeCopy() {
		return new Low_Tide();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(BLOCK_UP);
			this.upgradeMagicNumber(MAGIC_NUM_UPGRADE);
			//editElementalCost(0, "Water", 1);
			initializeDescription();
		}
	}
}