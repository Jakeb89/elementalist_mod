package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Wards extends AbstractElementalistCard {

	public static final String ID = "elementalist:Wards";
	public static final String NAME = "Wards";
	public static String DESCRIPTION = "Gain !B! Block for each enemy.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Wards() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.WARDS), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK_AMT;

	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		/*
		for(int i=0; i<getAllLivingEnemies().size(); i++) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		}
		*/
	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):
			for(int i=0; i<getAllLivingEnemies().size(); i++) {
				queueAction(new GainBlockAction(player, player, this.block));
			}
		default:
			return false;
		}
	}

	public AbstractCard makeCopy() {
		return new Wards();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}
