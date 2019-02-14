package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

public class Rising_Tides extends AbstractElementalistCard {
	public static final String ID = "elementalist:Rising_Tides";
	public static final String NAME = "High Tide";
	public static String DESCRIPTION = "Deal !D! damage. NL NL Watercast 2: For every !M! cards in your draw pile, a random enemy gains 2 Vulnerability.";
	private static final int COST = 1;
	public static final int DAMAGE = 10;
	public static final int DAMAGE_UP = 2;
	public static final int MAGIC_NUM = 5;
	public static final int MAGIC_NUM_UPGRADE = -2;

	public Rising_Tides() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_BLUE_3), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = MAGIC_NUM;
		this.baseDamage = DAMAGE;
		
		addElementalCost("Water", 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, 0));

	}
	
	@Override
	public void actionCallback(int value) {
		int tides = 0;
		if (cast("Water", 2)) {
			tides = AbstractDungeon.player.drawPile.size() / this.magicNumber;
			
			for(int i=0; i<tides; i++) {
				AbstractMonster randomTarget = AbstractDungeon.getRandomMonster();
				AbstractDungeon.actionManager.addToTop( new ApplyPowerAction( randomTarget, AbstractDungeon.player, new VulnerablePower(randomTarget, 2, false), 2));
				//AbstractDungeon.actionManager.addToTop( new ApplyPowerAction( randomTarget, AbstractDungeon.player, new WeakPower(randomTarget, 2, false), 2));
			}
		}
	}


	public AbstractCard makeCopy() {
		return new Rising_Tides();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UP);
			this.upgradeMagicNumber(MAGIC_NUM_UPGRADE);
			//editElementalCost(0, "Water", 1);
			initializeDescription();
		}
	}
}