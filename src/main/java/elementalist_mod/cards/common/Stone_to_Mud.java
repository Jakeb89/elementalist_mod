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

public class Stone_to_Mud extends AbstractElementalistCard {

	public static final String ID = "elementalist:Stone_to_Mud";
	public static final String NAME = "Stone to Mud";
	public static String DESCRIPTION = "elementalist:Earthcast 1: Gain !B! Block. NL NL elementalist:Earthcast 1: Apply 1 Weak to ALL enemies.";
	private static final int COST = 0;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public Stone_to_Mud() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.STONE_TO_MUD), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK_AMT;

		addElementalCost(Element.EARTH, 1);
		addElementalCost(Element.EARTH, 1);
	}

	/*public Color getCustomEnergyCostColor() {
		if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hand.contains(this))) { // check if lacking elemental cost
			if (AbstractElementalistCard.getElement(costElement) == 1) {
				return Color.GRAY.cpy();
			}
			if (AbstractElementalistCard.getElement(costElement) < 1) {
				return Color.DARK_GRAY.cpy();
			}
		}
		return Color.WHITE.cpy();
	}*/

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		if (cast(Element.EARTH, 1)) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		}
		if (getElement(Element.EARTH) >= 2 && cast(Element.EARTH, 1)) {
			if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				flash();
				for (AbstractMonster enemy : getAllLivingEnemies()) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(enemy, p, new WeakPower(enemy, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
				}
			}
		}
	}

	public AbstractCard makeCopy() {
		return new Stone_to_Mud();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}
