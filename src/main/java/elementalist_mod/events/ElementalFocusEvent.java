package elementalist_mod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import elementalist_mod.cards.common.*;
import elementalist_mod.cards.special.Air_Emblem;
import elementalist_mod.cards.special.Earth_Emblem;
import elementalist_mod.cards.special.Fire_Emblem;
import elementalist_mod.cards.special.Water_Emblem;
import elementalist_mod.relics.*;

public class ElementalFocusEvent extends AbstractImageEvent {

	// This isn't technically needed but it becomes useful later
	public static final String ID = "Chasing Memories";
	public static final String bodyText = "You find yourself at the edge of some kind of gathering of various monsters. Otherworldly lights and sounds pervade the location. NL On the floor you spot a collection of familiar trinkets - perhaps you could grab one before being spotted.";
	public static final String NAME = "A Strange Gathering";

	public static final String RIBBON_DIALOG = "You take the ribbon, and it glows with a strange warmth in your hand. A memory of warmth under the sun as someone fishes a few feet away, four feet dangling in the cool waters... NL NL You're brought back to the present as one of the monsters squeals, alerting the others of your presence.";
	public static final String CIRCLET_DIALOG = "You take the circlet, and it gleams with a strange warmth in your hand. A memory of halls of stone under the windy mountain, classes and study sessions shared by friends... NL NL You're brought back to the present as one of the monsters squeals, alerting the others of your presence.";
	public static final String EMBLEM_DIALOG = "You take the emblem. You had hoped it might spark some new memory, but only the warmth of fresh power greets you. The monsters don't seem to have noticed you just yet, but perhaps you should leave before they do this time.";
	public static final String RUN_DIALOG = "You just ran, you ran so far away.";

	private CUR_SCREEN screen = CUR_SCREEN.INTRO;

	private static enum CUR_SCREEN {
		INTRO, RIBBON, CIRCLET, EMBLEM, RUN;

		private CUR_SCREEN() {
		}
	}

	public ElementalFocusEvent() {
		super(ID, bodyText, "img/events/elemental_focus.png");

		// This is where you would create your dialog options
		if (!AbstractDungeon.player.hasRelic(BlueRibbon.ID)) {
			this.imageEventText.setDialogOption("Take the #bBlue #bRibbon");
		} else {
			this.imageEventText.setDialogOption("Take the #bBlue #bTrinket", new Water_Emblem());
		}

		if (!AbstractDungeon.player.hasRelic(RedRibbon.ID)) {
			this.imageEventText.setDialogOption("Take the #rRed #rRibbon");
		} else {
			this.imageEventText.setDialogOption("Take the #rRed #rTrinket", new Fire_Emblem());
		}

		if (!AbstractDungeon.player.hasRelic(GreenCirclet.ID)) {
			this.imageEventText.setDialogOption("Take the #gGreen #gCirclet");
		} else {
			this.imageEventText.setDialogOption("Take the #gGreen #gTrinket", new Air_Emblem());
		}

		if (!AbstractDungeon.player.hasRelic(YellowCirclet.ID)) {
			this.imageEventText.setDialogOption("Take the #yYellow #yCirclet");
		} else {
			this.imageEventText.setDialogOption("Take the #yYellow #yTrinket", new Earth_Emblem());
		}

		this.imageEventText.setDialogOption("Just run for it");
	}

	@Override
	protected void buttonEffect(int buttonPressed) {
		// It is best to look at examples of what to do here in the basegame event
		// classes, but essentially when you click on a dialog option the index of the
		// pressed button is passed here as buttonPressed.

		switch (this.screen) {
		case INTRO:

			AbstractRelic rewardRelic = null;
			AbstractCard rewardCard = null;

			this.imageEventText.clearAllDialogs();
			this.imageEventText.removeDialogOption(4);
			this.imageEventText.removeDialogOption(3);
			this.imageEventText.removeDialogOption(2);
			this.imageEventText.removeDialogOption(1);
			//this.imageEventText.removeDialogOption(0);

			switch (buttonPressed) {
			case (0):
				if (!AbstractDungeon.player.hasRelic(BlueRibbon.ID)) {
					rewardRelic = new BlueRibbon();
					this.imageEventText.updateBodyText(RIBBON_DIALOG);
					this.screen = CUR_SCREEN.RIBBON;
				} else {
					rewardCard = new Water_Emblem();
					this.imageEventText.updateBodyText(EMBLEM_DIALOG);
					this.screen = CUR_SCREEN.EMBLEM;
				}
				break;
			case (1):
				if (!AbstractDungeon.player.hasRelic(RedRibbon.ID)) {
					rewardRelic = new RedRibbon();
					this.imageEventText.updateBodyText(RIBBON_DIALOG);
					this.screen = CUR_SCREEN.RIBBON;
				} else {
					rewardCard = new Fire_Emblem();
					this.imageEventText.updateBodyText(EMBLEM_DIALOG);
					this.screen = CUR_SCREEN.EMBLEM;
				}
				break;
			case (2):
				if (!AbstractDungeon.player.hasRelic(GreenCirclet.ID)) {
					rewardRelic = new GreenCirclet();
					this.imageEventText.updateBodyText(CIRCLET_DIALOG);
					this.screen = CUR_SCREEN.CIRCLET;
				} else {
					rewardCard = new Air_Emblem();
					this.imageEventText.updateBodyText(EMBLEM_DIALOG);
					this.screen = CUR_SCREEN.EMBLEM;
				}
				break;
			case (3):
				if (!AbstractDungeon.player.hasRelic(YellowCirclet.ID)) {
					rewardRelic = new YellowCirclet();
					this.imageEventText.updateBodyText(CIRCLET_DIALOG);
					this.screen = CUR_SCREEN.CIRCLET;
				} else {
					rewardCard = new Earth_Emblem();
					this.imageEventText.updateBodyText(EMBLEM_DIALOG);
					this.screen = CUR_SCREEN.EMBLEM;
				}
				break;
			case (4):
			default:

				this.screen = CUR_SCREEN.RUN;
				break;
			}

			this.imageEventText.updateDialogOption(0, "Just keep running.");

			if (rewardRelic != null) {
				rewardRelic.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.size(), false);
				rewardRelic.playLandingSFX();
				rewardRelic.flash();
			}
			if (rewardCard != null) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewardCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
			}

			AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;

			break;

		case RIBBON:
		case CIRCLET:
		case EMBLEM:
		case RUN:

		default:
			openMap();
		}
	}
}