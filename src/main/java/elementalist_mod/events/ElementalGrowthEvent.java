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

public class ElementalGrowthEvent extends AbstractImageEvent {

	// This isn't technically needed but it becomes useful later
	public static final String ID = "Growth as a Mage";
	public static final String bodyText = "Through hard-earned experience (and no small measure of luck), you feel your understanding of the elements expanding.";
	public static final String NAME = "Growth as a Mage";

	public static final String AIR_DIALOG = "Your understanding of #gAir has expanded.";
	public static final String WATER_DIALOG = "Your mastery of #bWater has increased.";
	public static final String EARTH_DIALOG = "Your oneness with #yEarth has been centered.";
	public static final String FIRE_DIALOG = "You seek the path of #rFire.";
	public static final String SHINY_DIALOG = "Ooh, it was. A full 50 gold pieces. Wait, what were you thinking about again...? NL NL Eh, whatever. Probably wasn't that important.";

	private CUR_SCREEN screen = CUR_SCREEN.INTRO;

	private static enum CUR_SCREEN {
		INTRO, AIR, WATER, EARTH, FIRE, SHINY;

		private CUR_SCREEN() {
		}
	}

	public ElementalGrowthEvent() {
		super(ID, bodyText, "img/events/elemental_growth.png");
		

		this.imageEventText.setDialogOption("Expand your understanding of #gAir.", new Air_Emblem());
		this.imageEventText.setDialogOption("Increase your mastery of #bWater.", new Water_Emblem());
		this.imageEventText.setDialogOption("Center your oneness with #yEarth.", new Earth_Emblem());
		this.imageEventText.setDialogOption("Feed your image of #rFire.", new Fire_Emblem());
		this.imageEventText.setDialogOption("Learn to... wait, is that a bag of gold?");
	}

	@Override
	protected void buttonEffect(int buttonPressed) {
		// It is best to look at examples of what to do here in the basegame event
		// classes, but essentially when you click on a dialog option the index of the
		// pressed button is passed here as buttonPressed.

		switch (this.screen) {
		case INTRO:

			AbstractCard rewardCard = null;

			this.imageEventText.clearAllDialogs();
			this.imageEventText.removeDialogOption(4);
			this.imageEventText.removeDialogOption(3);
			this.imageEventText.removeDialogOption(2);
			this.imageEventText.removeDialogOption(1);
			//this.imageEventText.removeDialogOption(0);

			switch (buttonPressed) {
			case (0):
				this.imageEventText.updateBodyText(AIR_DIALOG);
				this.screen = CUR_SCREEN.AIR;
				rewardCard = new Air_Emblem();
				break;
			case (1):
				this.imageEventText.updateBodyText(WATER_DIALOG);
				this.screen = CUR_SCREEN.WATER;
				rewardCard = new Water_Emblem();
				break;
			case (2):
				this.imageEventText.updateBodyText(EARTH_DIALOG);
				this.screen = CUR_SCREEN.EARTH;
				rewardCard = new Earth_Emblem();
				break;
			case (3):
				this.imageEventText.updateBodyText(FIRE_DIALOG);
				this.screen = CUR_SCREEN.FIRE;
				rewardCard = new Fire_Emblem();
				break;
			case (4):
			default:
				this.imageEventText.updateBodyText(SHINY_DIALOG);
				this.screen = CUR_SCREEN.SHINY;
				AbstractDungeon.player.gainGold(50);
				break;
			}
			
			if (rewardCard != null) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewardCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
			}

			this.imageEventText.updateDialogOption(0, "Continue");
			AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;

			break;

		case AIR:
		case WATER:
		case EARTH:
		case FIRE:
		case SHINY:
		default:
			openMap();
			//this.enterCombatFromImage();
		}
	}
}