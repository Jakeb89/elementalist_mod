package elementalist_mod.cards.rare;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Wirbelwight extends AbstractElementalistCard {
	public static final String ID = "elementalist:Wirbelwight";
	public static final String NAME = "Wirbelwight";
	public static String DESCRIPTION = "You and ALL enemies gain !M! elementalist:Windburn. NL NL elementalist:Aircast 2: Double target's elementalist:Windburn.";
	private static final int COST = 1;
	private static final int MAGIC = 3;
	private static final int MAGIC_UPGRADE = 2;
	private AbstractMonster target;

	public Wirbelwight() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_GREEN_2), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);

		this.magicNumber = MAGIC;
		this.baseMagicNumber = MAGIC;

		addElementalCost(Element.AIR, 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		this.target = m;

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindburnPower(p, p, this.magicNumber)));

		for (AbstractMonster enemy : this.getAllLivingEnemies()) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(enemy, p, new WindburnPower(enemy, p, this.magicNumber), this.magicNumber));
		}

		if (cast(Element.AIR, 2)) {
			AbstractDungeon.actionManager.addToBottom(new CallbackAction(this));
		}
	}

	public void actionCallback(int value) {
		if (!target.hasPower(WindburnPower.POWER_ID))
			return;

		int windburn = target.getPower(WindburnPower.POWER_ID).amount;
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new WindburnPower(target, AbstractDungeon.player, windburn), windburn));
	}

	public AbstractCard makeCopy() {
		return new Wirbelwight();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(MAGIC_UPGRADE);
			this.initializeDescription();
		}
	}
}