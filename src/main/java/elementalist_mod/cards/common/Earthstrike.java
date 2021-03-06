package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Earthstrike extends AbstractElementalistCard{
	public static final String ID = "elementalist:Earthstrike";
	public static final String NAME = "Earthstrike";
	public static String DESCRIPTION = "Deal !D! damage. NL Earthcast 1: Gain !B! Block.";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int BLOCK_AMT = 4;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Earthstrike() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.EARTHSTRIKE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.BASIC,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = BLOCK_AMT;
		this.tags.add(AbstractCard.CardTags.STRIKE);
		this.tags.add(BaseModCardTags.BASIC_STRIKE);
		
		addElementalCost("Earth", 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		
		//Earthcast code
		if (cast("Earth", 1)) {
		    AbstractDungeon.actionManager.addToBottom(
		    		new GainBlockAction(p, p, this.block)
		    		);
		}
	}

	public AbstractCard makeCopy() {
		return new Earthstrike();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}