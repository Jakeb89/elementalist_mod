package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Impact extends AbstractElementalistCard {
	public static final String ID = "elementalist:Impact";
	public static final String NAME = "Impact";
	public static String DESCRIPTION = "Exhaust. Gain 7 Plated Armor. Gain !B! Block. NL NL Earthcast 3: Deal damage equal to your Block 3 times.";
	public static String DESCRIPTION_UPGRADED = "Tiring. Gain 7 Plated Armor. Gain !B! Block. NL NL Earthcast 3: Deal damage equal to your Block 3 times.";
	private static final int COST = 2;
	private static final int BLOCK = 5;

	public Impact() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_YELLOW_4), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);

		this.baseBlock = BLOCK;
		
		this.exhaust = true;
		
		addElementalCost(Element.EARTH, 3);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 7), 7));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		
		
		if(cast(Element.EARTH, 3)) {
			for(int i=0; i<3; i++) {
				int block = p.currentBlock;
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, block), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
			}
		}
		

	}

	public AbstractCard makeCopy() {
		return new Impact();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.exhaust = false;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}