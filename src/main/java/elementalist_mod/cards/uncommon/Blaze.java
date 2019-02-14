package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Blaze extends AbstractElementalistCard {
	public static final String ID = "elementalist:Blaze";
	public static final String NAME = "Blaze";
	public static String DESCRIPTION = "Deal !D! damage to X random enemies. NL Firecast 2: Repeat the above effect.";
	public static String UPGRADED_DESCRIPTION = "Deal !D! damage to X+1 random enemies. NL Firecast 2: Repeat the above effect.";
	private static final int COST = -1;
	private static final int ATTACK_DMG = 7;
	private boolean firecasted = false;
	private boolean firecastChecked = false;
	private int hits = 0;
	private int effect = 0;

	public Blaze() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_RED_3), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = ATTACK_DMG;

		addElementalCost("Fire", 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);


		if (this.energyOnUse < EnergyPanel.totalCount) {
			this.energyOnUse = EnergyPanel.totalCount;
		}

		effect = EnergyPanel.getCurrentEnergy();


		if (this.energyOnUse != -1) {
			effect = this.energyOnUse;
		}
		if (p.hasRelic("Chemical X")) {
			effect += 2;
			p.getRelic("Chemical X").flash();
		}

		if (upgraded) {
			effect++;
		}
		
		hits = effect;
		
		if (hits > 0) {
			if (!this.freeToPlayOnce) {
				p.energy.use(EnergyPanel.totalCount);
			}
			AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, 0));
			
			
			/*AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect(), 0.0F));
			for (int i = 0; i < effect; i++) {
				AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
				AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.0F));
				AbstractDungeon.actionManager
					.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));
			}

			if (cast("Fire", 2)) {
				AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect(), 0.0F));
				for (int i = 0; i < effect; i++) {
					AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
					AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.0F));
					AbstractDungeon.actionManager
						.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
				}
			}
			*/
		}

	}
	
	@Override
	public void actionCallback(int value) {
		AbstractPlayer p = AbstractDungeon.player;
		boolean doHitThisTime = false;
		if(hits > 0) {
			doHitThisTime = true;
			hits--;
		}
		if(hits == 0 && !firecastChecked) {
			firecastChecked = true;
			if (cast("Fire", 2)) {
				hits = effect;
			}
		}
		if(hits > 0) {
			AbstractDungeon.actionManager.addToTop(new CallbackAction(this, 0));
		}
		if(doHitThisTime) {
			AbstractDungeon.actionManager
				.addToTop(new DamageRandomEnemyAction(new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.FIRE));
			AbstractDungeon.actionManager.addToTop(new VFXAction(p, new CleaveEffect(), 0.0F));
			AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_HEAVY"));
		}
	}

	public AbstractCard makeCopy() {
		return new Blaze();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = UPGRADED_DESCRIPTION;
			initializeDescription();
		}
	}

}