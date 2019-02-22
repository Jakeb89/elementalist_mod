package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.BlazeAction;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Blaze extends AbstractElementalistCard {
	public static final String ID = "elementalist:Blaze";
	public static final String NAME = "Blaze";
	public static String DESCRIPTION = "Deal !D! damage to X random enemies. NL elementalist:Firecast 2: Repeat the above effect.";
	public static String UPGRADED_DESCRIPTION = "Deal !D! damage to X+1 random enemies. NL elementalist:Firecast 2: Repeat the above effect.";
	private static final int COST = -1;
	private static final int ATTACK_DMG = 7;
	private boolean firecasted = false;
	private boolean firecastChecked = false;
	private int hits = 0;
	private int effect = 0;

	public Blaze() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BLAZE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = ATTACK_DMG;

		addElementalCost(Element.FIRE, 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);



	}

	@Override
	public boolean doCardStep(int stepNumber) {
		switch (stepNumber) {
		case (0):

			if (this.energyOnUse < EnergyPanel.totalCount) {
				this.energyOnUse = EnergyPanel.totalCount;
			}

			effect = EnergyPanel.getCurrentEnergy();


			if (this.energyOnUse != -1) {
				effect = this.energyOnUse;
			}
			if (player.hasRelic("Chemical X")) {
				effect += 2;
				player.getRelic("Chemical X").flash();
			}

			if (upgraded) {
				effect++;
			}
			
			hits = effect;
			
			
			if(castNow(Element.FIRE, 2)) {
				hits *= 2;
			}
			
			for(int i=0; i<hits; i++) {
				queueAction(new BlazeAction(player, null, this.damage));
			}
			
			if (hits > 0) {
				if (!this.freeToPlayOnce) {
					player.energy.use(EnergyPanel.totalCount);
				}
			}
		default:
			return false;
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