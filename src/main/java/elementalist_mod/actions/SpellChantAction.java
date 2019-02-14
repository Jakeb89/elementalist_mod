package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.powers.WindburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

public class SpellChantAction extends AbstractGameAction {
	private AbstractPlayer player;
	private String spellChant;
	private float textDuration;

	public SpellChantAction(String spellChant, float duration) {
		this.actionType = AbstractGameAction.ActionType.DIALOG;
	    this.spellChant = spellChant;
	    this.textDuration = duration;
	    this.duration = 0.1f;
	}

	public void update() {

	    if(spellChant.contains("!")) {
	        AbstractDungeon.effectList.add(new MegaSpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, this.textDuration, this.spellChant, true));
	    }else {
	        AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, this.textDuration, this.spellChant, true));
	    }
		
	    
		this.isDone = true;
	}

}
