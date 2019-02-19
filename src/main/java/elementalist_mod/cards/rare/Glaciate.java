package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Glaciate extends AbstractElementalistCard {
	public static final String ID = "elementalist:Glaciate";
	public static final String NAME = "Glaciate";
	public static String DESCRIPTION = "Deal !D! damage. NL NL elementalist:Watercast 2: Gain Block equal to target's base attack intent.";
	private static final int COST = 2;
	private static final int DAMAGE = 16;
	private static final int DAMAGE_UPGRADE = 4;

	public Glaciate() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_BLUE_2), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);

		this.baseDamage = DAMAGE;
		addElementalCost(Element.WATER, 2);

	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
		if(cast(Element.WATER, 2)) {
			int enemyDamage = 0;
			try {
				java.lang.reflect.Field move = AbstractMonster.class.getDeclaredField("move");
				move.setAccessible(true);
				EnemyMoveInfo copiedMove = (EnemyMoveInfo) move.get(m);
				enemyDamage = copiedMove.baseDamage;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(enemyDamage > 0) {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, enemyDamage));
			}
		}
		

	}

	public AbstractCard makeCopy() {
		return new Glaciate();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UPGRADE);
			this.initializeDescription();
		}
	}
}