package elementalist_mod;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PreStartGameSubscriber;
import elementalist_mod.actions.*;
import elementalist_mod.cards.*;
import elementalist_mod.characters.*;
import elementalist_mod.events.*;
import elementalist_mod.orbs.*;
import elementalist_mod.patches.*;
import elementalist_mod.powers.*;
import elementalist_mod.relics.*;

@SpireInitializer
public class ElementalistMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber, EditStringsSubscriber,
	EditKeywordsSubscriber, OnStartBattleSubscriber, PostDungeonInitializeSubscriber, PostExhaustSubscriber, PostBattleSubscriber, PreStartGameSubscriber {
	public static final Logger logger = LogManager.getLogger(ElementalistMod.class.getName());
	public static HashMap<String, Integer> loggerMessages = new HashMap<String, Integer>();
	public static ArrayList<String> loggerMutes = new ArrayList<String>();
	public static boolean ribbonEventFired = false;
	public static CardGroup levelupRewardGroup = null;

	public static void log(String info) {
		logger.info("ElemLog: " + info);

	}
	
	public static enum Element {
	    AIR, WATER, EARTH, FIRE
	}

	public static Color ELEMBLUE = CardHelper.getColor(128f, 128f, 128f);

	private static final String MODNAME = "ElemenatlistMod";
	private static final String AUTHOR = "Jake Berry";
	private static final String DESCRIPTION = "v0.0.1\n Adds The Elementalist as a playable character";

	// character assets
	public static final String ELEMENTALIST_BUTTON = "charSelect/Button.png";
	public static final String ELEMENTALIST_MAIN = "char/elementalist/main.png";
	public static final String ELEMENTALIST_PORTRAIT = "charSelect/PortraitBG.jpg";
	public static final String ELEMENTALIST_SHOULDER_1 = "char/elementalist/shoulder.png";
	public static final String ELEMENTALIST_SHOULDER_2 = "char/elementalist/shoulder2.png";
	public static final String ELEMENTALIST_CORPSE = "char/elementalist/corpse.png";

	// card backgrounds
	private static final String ATTACK = "512/bg_attack.png";
	private static final String SKILL = "512/bg_skill.png";
	private static final String POWER = "512/bg_power.png";
	private static final String ENERGY_ORB = "512/card_orb.png";

	private static final String ATTACK_PORTRAIT = "1024/bg_attack.png";
	private static final String SKILL_PORTRAIT = "1024/bg_attack.png";
	private static final String POWER_PORTRAIT = "1024/bg_attack.png";
	private static final String ENERGY_ORB_PORTRAIT = "1024/card_orb.png";

	// custom card backs
	public static final String BLANK = "cardui/512/blank.png";

	// card images
	public static final String ELEMENTAL_STRIKE = "cards/elemental_strike.png";
	public static final String ELEMENTAL_DEFEND = "cards/elemental_defend.png";
	public static final String DEFEND_ELEM = "cards/defend.png";
	public static final String FIRESTRIKE = "cards/firestrike.png";
	public static final String EARTHSTRIKE = "cards/earthstrike.png";
	public static final String WATERSTRIKE = "cards/waterstrike.png";
	public static final String AIRSTRIKE = "cards/airstrike.png";

	public static final String STONE_TO_MUD = "cards/stone_to_mud.png";
	public static final String SPARK = "cards/spark.png";
	public static final String CONFLUENCE = "cards/confluence.png";
	public static final String MAGMA_SHOT = "cards/magma_shot.png";
	public static final String WIDDERSHINS_CONVERSION = "cards/widdershins_conversion.png";
	public static final String RISING_VAPORS = "cards/rising_vapors.png";
	public static final String FISSION_LANCE = "cards/fission_lance.png";
	public static final String RECKLESS_DANCE = "cards/reckless_dance.png";
	public static final String QUADMIRE = "cards/quadmire.png";

	public static final String CRYSTAL_CHALK = "cards/crystal_chalk.png";
	public static final String TERRA_HEART = "cards/terra_heart.png";
	public static final String IGNIS_EYE = "cards/ignis_eye.png";
	public static final String AQUA_MIND = "cards/aqua_mind.png";
	public static final String ZEPHYR_SOUL = "cards/zephyr_soul.png";

	public static final String MULTIFICATION = "cards/multification.png";

	public static final String PREPARED_SAND = "cards/prepared_sand.png";
	public static final String ETHERSTRIKE = "cards/etherstrike.png";
	public static final String FLAME = "cards/flame.png";
	public static final String SPOUT = "cards/spout.png";
	public static final String MONUMENT = "cards/monument.png";
	public static final String HEAVENSFALL = "cards/heavensfall.png";
	

	public static final String AUTOPETRIFY = "cards/autopetrify.png";
	public static final String AEGIS = "cards/aegis.png";
	public static final String BREEZE = "cards/breeze.png";
	public static final String CAUTION = "cards/caution.png";
	public static final String CYCLONE = "cards/cyclone.png";
	public static final String EMBERVEIL = "cards/emberveil.png";
	public static final String MIST = "cards/mist.png";
	public static final String MOMENTUM = "cards/momentum.png";
	public static final String OVERHEAT = "cards/overheat.png";
	public static final String WARDS = "cards/wards.png";
	public static final String CONCENTRICITY = "cards/concentricity.png";

	public static final String SOLAR_FORM = "cards/solar_form.png";
	public static final String LUNAR_FORM = "cards/lunar_form.png";
	public static final String SIDEREAL_FORM = "cards/sidereal_form.png";
	public static final String GAIAN_FORM = "cards/gaian_form.png";
	public static final String ASTRAL_FORM = "cards/astral_form.png";
	

	public static final String IMPACT = "cards/impact.png";
	public static final String BURNING_SOUL = "cards/burning_soul.png";
	public static final String ABSOLUTE_ZERO = "cards/absolute_zero.png";
	public static final String ACCELERANT = "cards/accelerant.png";
	public static final String BLAZE = "cards/blaze.png";
	public static final String HIGH_TIDE = "cards/high_tide.png";
	public static final String LOW_TIDE = "cards/low_tide.png";

	// generic card images

	public static final String BETA_ATTACK_GREY_1 = "cards/beta_attack_grey_1.png";
	public static final String BETA_ATTACK_GREY_2 = "cards/beta_attack_grey_2.png";
	public static final String BETA_ATTACK_GREY_3 = "cards/beta_attack_grey_3.png";
	public static final String BETA_ATTACK_GREY_4 = "cards/beta_attack_grey_4.png";
	public static final String BETA_ATTACK_RED_1 = "cards/beta_attack_red_1.png";
	public static final String BETA_ATTACK_RED_2 = "cards/beta_attack_red_2.png";
	public static final String BETA_ATTACK_RED_3 = "cards/beta_attack_red_3.png";
	public static final String BETA_ATTACK_RED_4 = "cards/beta_attack_red_4.png";
	public static final String BETA_ATTACK_YELLOW_1 = "cards/beta_attack_yellow_1.png";
	public static final String BETA_ATTACK_YELLOW_2 = "cards/beta_attack_yellow_2.png";
	public static final String BETA_ATTACK_YELLOW_3 = "cards/beta_attack_yellow_3.png";
	public static final String BETA_ATTACK_YELLOW_4 = "cards/beta_attack_yellow_4.png";
	public static final String BETA_ATTACK_GREEN_1 = "cards/beta_attack_green_1.png";
	public static final String BETA_ATTACK_GREEN_2 = "cards/beta_attack_green_2.png";
	public static final String BETA_ATTACK_GREEN_3 = "cards/beta_attack_green_3.png";
	public static final String BETA_ATTACK_GREEN_4 = "cards/beta_attack_green_4.png";
	public static final String BETA_ATTACK_BLUE_1 = "cards/beta_attack_blue_1.png";
	public static final String BETA_ATTACK_BLUE_2 = "cards/beta_attack_blue_2.png";
	public static final String BETA_ATTACK_BLUE_3 = "cards/beta_attack_blue_3.png";
	public static final String BETA_ATTACK_BLUE_4 = "cards/beta_attack_blue_4.png";

	public static final String BETA_SKILL_GREY_1 = "cards/beta_skill_grey_1.png";
	public static final String BETA_SKILL_GREY_2 = "cards/beta_skill_grey_2.png";
	public static final String BETA_SKILL_GREY_3 = "cards/beta_skill_grey_3.png";
	public static final String BETA_SKILL_GREY_4 = "cards/beta_skill_grey_4.png";
	public static final String BETA_SKILL_RED_1 = "cards/beta_skill_red_1.png";
	public static final String BETA_SKILL_RED_2 = "cards/beta_skill_red_2.png";
	public static final String BETA_SKILL_RED_3 = "cards/beta_skill_red_3.png";
	public static final String BETA_SKILL_RED_4 = "cards/beta_skill_red_4.png";
	public static final String BETA_SKILL_YELLOW_1 = "cards/beta_skill_yellow_1.png";
	public static final String BETA_SKILL_YELLOW_2 = "cards/beta_skill_yellow_2.png";
	public static final String BETA_SKILL_YELLOW_3 = "cards/beta_skill_yellow_3.png";
	public static final String BETA_SKILL_YELLOW_4 = "cards/beta_skill_yellow_4.png";
	public static final String BETA_SKILL_GREEN_1 = "cards/beta_skill_green_1.png";
	public static final String BETA_SKILL_GREEN_2 = "cards/beta_skill_green_2.png";
	public static final String BETA_SKILL_GREEN_3 = "cards/beta_skill_green_3.png";
	public static final String BETA_SKILL_GREEN_4 = "cards/beta_skill_green_4.png";
	public static final String BETA_SKILL_BLUE_1 = "cards/beta_skill_blue_1.png";
	public static final String BETA_SKILL_BLUE_2 = "cards/beta_skill_blue_2.png";
	public static final String BETA_SKILL_BLUE_3 = "cards/beta_skill_blue_3.png";
	public static final String BETA_SKILL_BLUE_4 = "cards/beta_skill_blue_4.png";

	public static final String BETA_POWER_GREY_1 = "cards/beta_power_grey_1.png";
	public static final String BETA_POWER_GREY_2 = "cards/beta_power_grey_2.png";
	public static final String BETA_POWER_GREY_3 = "cards/beta_power_grey_3.png";
	public static final String BETA_POWER_GREY_4 = "cards/beta_power_grey_4.png";
	public static final String BETA_POWER_RED_1 = "cards/beta_power_red_1.png";
	public static final String BETA_POWER_RED_2 = "cards/beta_power_red_2.png";
	public static final String BETA_POWER_RED_3 = "cards/beta_power_red_3.png";
	public static final String BETA_POWER_RED_4 = "cards/beta_power_red_4.png";
	public static final String BETA_POWER_YELLOW_1 = "cards/beta_power_yellow_1.png";
	public static final String BETA_POWER_YELLOW_2 = "cards/beta_power_yellow_2.png";
	public static final String BETA_POWER_YELLOW_3 = "cards/beta_power_yellow_3.png";
	public static final String BETA_POWER_YELLOW_4 = "cards/beta_power_yellow_4.png";
	public static final String BETA_POWER_GREEN_1 = "cards/beta_power_green_1.png";
	public static final String BETA_POWER_GREEN_2 = "cards/beta_power_green_2.png";
	public static final String BETA_POWER_GREEN_3 = "cards/beta_power_green_3.png";
	public static final String BETA_POWER_GREEN_4 = "cards/beta_power_green_4.png";
	public static final String BETA_POWER_BLUE_1 = "cards/beta_power_blue_1.png";
	public static final String BETA_POWER_BLUE_2 = "cards/beta_power_blue_2.png";
	public static final String BETA_POWER_BLUE_3 = "cards/beta_power_blue_3.png";
	public static final String BETA_POWER_BLUE_4 = "cards/beta_power_blue_4.png";

	// powers
	public static final String POWER_CONFLUENCE = "img/powers/confluence.png";
	public static final String POWER_CONFLUENCE_SMALL = "img/powers/confluence_small.png";
	public static final String POWER_AERIAL_DODGE = "img/powers/aerial_dodge.png";
	public static final String POWER_AERIAL_DODGE_SMALL = "img/powers/aerial_dodge_small.png";
	public static final String POWER_WINDBURN = "img/powers/windburn.png";
	public static final String POWER_WINDBURN_SMALL = "img/powers/windburn_small.png";
	public static final String POWER_QUADMIRE = "img/powers/quadmire.png";
	public static final String POWER_QUADMIRE_SMALL = "img/powers/quadmire_small.png";
	public static final String POWER_MULTIFICATION = "img/powers/multification.png";
	public static final String POWER_MULTIFICATION_SMALL = "img/powers/multification_small.png";
	public static final String POWER_MOMENTUM = "img/powers/momentum.png";
	public static final String POWER_MOMENTUM_SMALL = "img/powers/momentum_small.png";

	public static final String POWER_DRAGON_BIGRAM = "img/powers/dragon_bigram.png";
	public static final String POWER_DRAGON_BIGRAM_SMALL = "img/powers/dragon_bigram_small.png";
	public static final String POWER_BURNING_SOUL = "img/powers/burning_soul.png";
	public static final String POWER_BURNING_SOUL_SMALL = "img/powers/burning_soul_small.png";
	public static final String POWER_AETHERIC_SHIELD = "img/powers/aetheric_shield.png";
	public static final String POWER_AETHERIC_SHIELD_SMALL = "img/powers/aetheric_shield_small.png";
	public static final String POWER_TSUNAMI = "img/powers/tsunami.png";
	public static final String POWER_TSUNAMI_SMALL = "img/powers/tsunami_small.png";
	public static final String POWER_MIRROR_CIRCLET = "img/powers/mirror_circlet.png";
	public static final String POWER_MIRROR_CIRCLET_SMALL = "img/powers/mirror_circlet_small.png";

	public static final String POWER_SOLARFORM = "img/powers/solarform.png";
	public static final String POWER_SOLARFORM_SMALL = "img/powers/solarform_small.png";
	public static final String POWER_LUNARFORM = "img/powers/lunarform.png";
	public static final String POWER_LUNARFORM_SMALL = "img/powers/lunarform_small.png";
	public static final String POWER_SIDEREALFORM = "img/powers/siderealform.png";
	public static final String POWER_SIDEREALFORM_SMALL = "img/powers/siderealform_small.png";
	public static final String POWER_GAIANFORM = "img/powers/gaianform.png";
	public static final String POWER_GAIANFORM_SMALL = "img/powers/gaianform_small.png";
	public static final String POWER_ASTRALFORM = "img/powers/astralform.png";
	public static final String POWER_ASTRALFORM_SMALL = "img/powers/astralform_small.png";

	// relics
	public static final String MAGUS_STAFF = "img/relics/magus_staff.png";
	public static final String MAGUS_STAFF_OUTLINE = "img/relics/magus_staff_outline.png";
	public static final String MAGUS_STAFF_INNERGLOW = "img/relics/magus_staff_innerglow.png";
	public static final String MAGUS_STAFF_ORB = "img/relics/magus_staff_orb.png";

	public static final String YELLOW_CIRCLET = "img/relics/yellow_circlet.png";
	public static final String YELLOW_CIRCLET_OUTLINE = "img/relics/yellow_circlet_outline.png";
	public static final String GREEN_CIRCLET = "img/relics/green_circlet.png";
	public static final String GREEN_CIRCLET_OUTLINE = "img/relics/green_circlet_outline.png";
	public static final String RED_RIBBON = "img/relics/red_ribbon.png";
	public static final String RED_RIBBON_OUTLINE = "img/relics/red_ribbon_outline.png";
	public static final String BLUE_RIBBON = "img/relics/blue_ribbon.png";
	public static final String BLUE_RIBBON_OUTLINE = "img/relics/blue_ribbon_outline.png";

	// misc. effects
	public static final String[] MAGIC_CIRCLE = { "img/effects/magic_circle_1.png", "img/effects/magic_circle_2.png", "img/effects/magic_circle_3.png",
		"img/effects/magic_circle_4.png", "img/effects/magic_circle_5.png", "img/effects/magic_circle_6.png" };

	// badge
	public static final String BADGE_IMG = "Badge.png";

	private static final String ASSETS_FOLDER = "img";
	
	
	private static ArrayList<ElementOrb> elementOrbList = new ArrayList<ElementOrb>();

	public static final String makePath(String resource) {
		return ASSETS_FOLDER + "/" + resource;
	}

	private static boolean elementalEnergyEnabled;

	// private GenericEventDialog imageEventText;

	public ElementalistMod() {

		BaseMod.subscribe(this);
		BaseMod.addColor(AbstractCardEnum.ELEMENTALIST_BLUE, ELEMBLUE, makePath(ATTACK), makePath(SKILL), makePath(POWER), makePath(ENERGY_ORB), makePath(ATTACK_PORTRAIT),
			makePath(SKILL_PORTRAIT), makePath(POWER_PORTRAIT), makePath(ENERGY_ORB_PORTRAIT), makePath(ENERGY_ORB));

		// public static Properties modDefaults = new Properties();

	}

	public static void initialize() {
		new ElementalistMod();
	}

	@Override
	public void receivePostDungeonInitialize() {
		ElementalistMod.log("receivePostDungeonInitialize() start");
		/*
		 * ElementalChoiceEvent event = new ElementalChoiceEvent();
		 * event.showProceedScreen("bodytext");
		 * 
		 * GenericEventDialog imageEventText;
		 * 
		 * imageEventText = new GenericEventDialog(); imageEventText.
		 * 
		 * AbstractEvent.type = AbstractEvent.EventType.IMAGE; this.imageEventText = new
		 * GenericEventDialog(); String[] eventDesc = {"desc1", "desc2"}; String[]
		 * eventOpts = {"opt1", "opt2"}; AbstractEvent.NAME = "NAME";
		 * AbstractEvent.DESCRIPTIONS = eventDesc; AbstractEvent.OPTIONS = eventOpts;
		 * //this.imageEventText.hide(); //this.imageEventText.clear();
		 * 
		 */
		ElementalistMod.log("receivePostDungeonInitialize() end");
		ribbonEventFired = false;
	}

	@Override
	public void receivePostInitialize() {
		// Mod badge
		Texture badgeTexture = new Texture(makePath(BADGE_IMG));
		ModPanel settingsPanel = new ModPanel();
		settingsPanel.addUIElement(new ModLabel("ElementalistMod does not have any settings (yet)!", 400.0f, 700.0f, settingsPanel, (me) -> {
		}));
		BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

		Settings.isDailyRun = false;
		Settings.isTrial = false;
		Settings.isDemo = false;

		BaseMod.addEvent(ElementalFocusEvent.ID, ElementalFocusEvent.class);
		BaseMod.addEvent(ElementalGrowthEvent.ID, ElementalGrowthEvent.class);

	}

	// @Override
	public void receiveEditCharacters() {
		// logger.info("begin editing characters");
		TheElementalist elementalistCharacter = new TheElementalist(CardCrawlGame.playerName, TheElementalistEnum.THE_ELEMENTALIST);
		BaseMod.addCharacter(elementalistCharacter, makePath(ELEMENTALIST_BUTTON), makePath(ELEMENTALIST_PORTRAIT), TheElementalistEnum.THE_ELEMENTALIST);
		// logger.info("done editing characters");
	}

	public void receiveEditCards() {
		logger.info("begin editing cards");
		logger.info("add cards for " + TheElementalistEnum.THE_ELEMENTALIST.toString());

		for (AbstractElementalistCard card : CardList.getCards()) {
			BaseMod.addCard(card);
		}
	}

	@Override
	public void receiveEditRelics() {

		BaseMod.addRelicToCustomPool(new MagusStaff(), AbstractCardEnum.ELEMENTALIST_BLUE);
		BaseMod.addRelicToCustomPool(new RedRibbon(), AbstractCardEnum.ELEMENTALIST_BLUE);
		BaseMod.addRelicToCustomPool(new BlueRibbon(), AbstractCardEnum.ELEMENTALIST_BLUE);
		BaseMod.addRelicToCustomPool(new YellowCirclet(), AbstractCardEnum.ELEMENTALIST_BLUE);
		BaseMod.addRelicToCustomPool(new GreenCirclet(), AbstractCardEnum.ELEMENTALIST_BLUE);
	}

	@Override
	public void receiveEditKeywords() {
		BaseMod.addKeyword("Elementize X", new String[] { "elementize" },
			"When drawn, Elementize X becomes one of Fire X, Water X, Earth X, or Air X randomly. When played, the given number of the chosen element is generated.");
		BaseMod.addKeyword("Create [Element] X", new String[] { "create fire", "create earth", "create water", "create air" }, "Generates X of the listed element.");

		BaseMod.addKeyword("Firecast X", new String[] { "firecast" }, "If you have X fire, it is spent to achieve the following effect.");
		BaseMod.addKeyword("Earthcast X", new String[] { "earthcast" }, "If you have X earth, it is spent to achieve the following effect.");
		BaseMod.addKeyword("Watercast X", new String[] { "watercast" }, "If you have X water, it is spent to achieve the following effect.");
		BaseMod.addKeyword("Aircast X", new String[] { "aircast" }, "If you have X air, it is spent to achieve the following effect.");
		BaseMod.addKeyword("Elementcast X", new String[] { "elementcast" },
			"If you have X between your elements, it is spent (from most to least) to achieve the following effect.");

		BaseMod.addKeyword("Transmute", new String[] { "transmute" }, "Takes all points in the first element and moves them to the second.");

		BaseMod.addKeyword("Synergy", new String[] { "synergy", "synergized", "synergizing", "synergizes" }, "Occurs when at least two elements are equal and greater than zero.");
		BaseMod.addKeyword("Aerial Dodge", new String[] { "aerial", "dodge" }, "When you have less than 5 extra block remaining at the end of the enemy turn, gain 2 Dexterity.");
		BaseMod.addKeyword("Windburn", new String[] { "windburn" },
			"Lose HP equal to your Windburn at the end of your turn if any element is higher than Air. Monsters always lose HP to Windburn.");
		BaseMod.addKeyword("Rune", new String[] { "rune", "igniseye", "terraheart", "zephyrsoul", "aquamind" },
			"Runes are temporary cards which exhaust on use, at the end of your turn, or if another Rune is played.");

		BaseMod.addKeyword("Igniseye", new String[] { "igniseye" }, "A 0-cost Rune Skill which gives 1 Fire. NL If upgraded, it gives 2 Fire instead.");
		BaseMod.addKeyword("Terraheart", new String[] { "terraheart" }, "A 0-cost Rune Skill which gives 1 Earth. NL If upgraded, it gives 2 Earth instead.");
		BaseMod.addKeyword("Zephyrsoul", new String[] { "zephyrsoul" }, "A 0-cost Rune Skill which gives 1 Air. NL If upgraded, it gives 2 Air instead.");
		BaseMod.addKeyword("Aquamind", new String[] { "aquamind" }, "A 0-cost Rune Skill which gives 1 Water. NL If upgraded, it gives 2 Water instead.");

		BaseMod.addKeyword("Mercurial", new String[] { "mercurial" }, "When drawn, the cost of a random card in your hand is reduced by 1 for a turn.");

		BaseMod.addKeyword("Tiring", new String[] { "tiring" }, "A card with 'Tiring' gains 'Exhaust' when played.");
		BaseMod.addKeyword("Emblem", new String[] { "emblem" },
			"Emblems are cards that activate automatically when drawn once per turn. They then move to the discard, and another card is drawn instead.");

		BaseMod.addKeyword("Total Cost", new String[] { "total cost" }, "'Total Cost' refers to the sum of both normal and elemental energy costs of a card.");
		BaseMod.addKeyword("Ward", new String[] { "ward" }, "Wards remain in your hand until the beginning of the next turn.");
		BaseMod.addKeyword("Bloodied", new String[] { "bloodied" }, "Bloodied effects occur when the player takes damage during the enemy turn.");
		BaseMod.addKeyword("Unplayable", new String[] { "unplayable" }, "Unplayable cards cannot be played manually.");
		BaseMod.addKeyword("Autopetrify", new String[] { "autopetrify" }, "Autopetrify is an unplayable status which exhausts and gives Plated Armor and Earth when you are damaged.");

		BaseMod.addKeyword("Check the Pins", new String[] { "pins" }, "For fuck's sake, please just check the pins.");
	}

	@Override
	public void receiveOnBattleStart(AbstractRoom arg0) {
		// TODO Auto-generated method stub

		elementOrbList.clear();
		if(!elementalEnergyIsEnabled()) {
			for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
				if(card instanceof AbstractElementalistCard) {
					setElementalEnergyEnabled(true);
				}
			}
		}
		if (elementalEnergyIsEnabled()) {
			changeElement(Element.AIR, 0);
			changeElement(Element.WATER, 0);
			changeElement(Element.EARTH, 0);
			changeElement(Element.FIRE, 0);
		}

	}

	@Override
	public void receiveEditStrings() {
		logger.info("begin editing strings");

		// RelicStrings
		String relicStrings = Gdx.files.internal("localization/ElementalistMod-RelicStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

		// CardStrings
		String cardStrings = Gdx.files.internal("localization/ElementalistMod-CardStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

	}

	public static Color getColorFromElement(String element) {
		switch (element.toLowerCase()) {
		case ("fire"):
			return Color.RED.cpy();
		case ("water"):
			return Color.BLUE.cpy();
		case ("earth"):
			return Color.YELLOW.cpy();
		case ("air"):
			return Color.GREEN.cpy();
		}
		return Color.WHITE.cpy();
	}

	public static int getElement(Element element) {
		setElementalEnergyEnabled(true);
		if (AbstractDungeon.player == null)
			return 0;
		if (AbstractDungeon.player.orbs == null)
			return 0;

		for (ElementOrb elementOrb : getElementOrbs()) {
			//if (orb instanceof ElementOrb) {
				//ElementOrb elementOrb = (ElementOrb) orb;
				if (elementOrb.element == element) {
					return elementOrb.amount;
				}
			//}
		}
		return 0;
	}

	private static void setElementalEnergyEnabled(boolean b) {
		if(elementalEnergyEnabled == false && b == true) {
			elementalEnergyEnabled = b;
			if(AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null) {
				changeElement(Element.AIR, 0);
				changeElement(Element.WATER, 0);
				changeElement(Element.EARTH, 0);
				changeElement(Element.FIRE, 0);
			}
		}
		elementalEnergyEnabled = b;
	}

	public static boolean hasMultification() {
		for (AbstractPower power : AbstractDungeon.player.powers) {
			if (power.ID == MultificationPower.POWER_ID || power instanceof MultificationPower) {
				return true;
			}
		}
		return false;
	}

	public static void changeElement(Element element, int delta) {
		changeElement(element, delta, "?");
	}

	public static void changeElement(Element element, int delta, String source) {
		if(AbstractDungeon.getCurrRoom() == null) return;
		if(AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) return;
		
		setElementalEnergyEnabled(true);
		AbstractDungeon.actionManager.addToBottom(new ElementAddAction(makeOrb(element, delta), source));
	}

	public static void changeElementNow(Element element, int delta) {
		changeElementNow(element, delta, "?");
	}

	public static void changeElementNow(Element element, int delta, String source) {
		if(AbstractDungeon.getCurrRoom() == null) return;
		if(AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) return;
		
		setElementalEnergyEnabled(true);
		AbstractDungeon.actionManager.addToTop(new ElementAddAction(makeOrb(element, delta), source));
	}

	public static ElementOrb makeOrb(Element element, int amount) {
		switch (element) {
		case FIRE:
			return new FireOrb(amount);
		case WATER:
			return new WaterOrb(amount);
		case EARTH:
			return new EarthOrb(amount);
		case AIR:
			return new AirOrb(amount);
		}
		return null;
	}

	public static AbstractMonster pickRandomLivingEnemy() {
		ArrayList<AbstractMonster> enemyList = new ArrayList<AbstractMonster>();

		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
				if ((!monster.isDead) && (!monster.isDying)) {
					enemyList.add(monster);
				}
			}
		}

		if (enemyList.size() == 0)
			return null;

		return enemyList.get((int) (Math.random() * enemyList.size()));
	}

	public static ArrayList<AbstractMonster> getAllLivingEnemies() {
		ArrayList<AbstractMonster> enemyList = new ArrayList<AbstractMonster>();

		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
				if ((!monster.isDead) && (!monster.isDying) && (!monster.halfDead)) {
					enemyList.add(monster);
				}
			}
		}

		return enemyList;
	}

	public static boolean intentContainsAttack(Intent intent) {
		if (intent == Intent.ATTACK)
			return true;
		if (intent == Intent.ATTACK_BUFF)
			return true;
		if (intent == Intent.ATTACK_DEBUFF)
			return true;
		if (intent == Intent.ATTACK_DEFEND)
			return true;

		return false;
	}

	public static Element[] getHighestElements() {
		ArrayList<Element> elements = new ArrayList<Element>();

		int fire = getElement(Element.FIRE);
		int earth = getElement(Element.EARTH);
		int water = getElement(Element.WATER);
		int air = getElement(Element.AIR);
		int highestValue = Math.max(Math.max(fire, earth), Math.max(water, air));

		if (fire == highestValue)
			elements.add(Element.FIRE);
		if (earth == highestValue)
			elements.add(Element.EARTH);
		if (water == highestValue)
			elements.add(Element.WATER);
		if (air == highestValue)
			elements.add(Element.AIR);

		Element[] output = new Element[elements.size()];

		return elements.toArray(output);

	}

	public static AbstractCard[] getAllStatusOrCurse(CardGroup cardGroup) {
		ArrayList<AbstractCard> badCards = new ArrayList<AbstractCard>();
		for (AbstractCard card : cardGroup.group) {
			if (card.type == CardType.STATUS || card.type == CardType.CURSE) {
				badCards.add(card);
			}
		}
		AbstractCard[] output = new AbstractCard[badCards.size()];
		return badCards.toArray(output);
	}

	public static AbstractCard getRandomCard(AbstractCard[] cards) {
		if (cards.length == 0)
			return null;
		return cards[(int) (Math.random() * cards.length)];
	}

	@Override
	public void receivePostExhaust(AbstractCard card) {
		for (AbstractPower power : AbstractDungeon.player.powers) {
			if (power instanceof ElementalPower) {
				((ElementalPower) power).onCardExhaust(card);
			}
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom room) {
		if(elementalEnergyIsEnabled()) {
			if(room instanceof MonsterRoom) {
				MonsterRoom mRoom = (MonsterRoom)room;
				if(mRoom instanceof MonsterRoomBoss) {
					TheElementalist.addExp(30);
				}else if(mRoom instanceof MonsterRoomElite) {
					TheElementalist.addExp(20);
				}else {
					TheElementalist.addExp(10);
				}
			}
			for(ElementOrb orb : getElementOrbs()) {
				orb.amount = 0;
			}
		}
	}

	public static boolean isReadyForGrowthEvent() {
		// TODO Auto-generated method stub
		if (TheElementalist.readyForLevelup()) {
			return true;
		}
		return false;
	}

	public static boolean hasSynergy(Element elem) {
		int fire = getElement(Element.FIRE);
		int earth = getElement(Element.EARTH);
		int water = getElement(Element.WATER);
		int air = getElement(Element.AIR);

		switch (elem) {
		case FIRE:
			return fire > 0 && anyEqualFirst(fire, earth, water, air);
		case EARTH:
			return earth > 0 && anyEqualFirst(earth, fire, water, air);
		case WATER:
			return water > 0 && anyEqualFirst(water, fire, earth, air);
		case AIR:
			return air > 0 && anyEqualFirst(air, fire, earth, water);
		}
		return false;
	}

	private static boolean anyEqualFirst(int val1, int val2, int val3, int val4) {
		if (val1 == val2)
			return true;
		if (val1 == val3)
			return true;
		if (val1 == val4)
			return true;

		return false;
	}

	@Override
	public void receivePreStartGame() {
		setElementalEnergyEnabled(false);
		/*if(AbstractDungeon.player instanceof TheElementalist) {
			setElementalEnergyEnabled(true);
		}*/
		TheElementalist.totalExp = 0;
		TheElementalist.level = 1;
	}

	public static void renderElementalEnergies(SpriteBatch sb, float current_x, float current_y) {
		// TODO Auto-generated method stub
		for(AbstractOrb orb : getElementOrbs()) {
			if(orb instanceof ElementOrb) {
				ElementOrb eOrb = (ElementOrb) orb;
				float dir = 0;
				float sixteenth = 2*3.14f / 16;
				switch(eOrb.element) {
					case FIRE: 	dir = 1 * sixteenth; 	break;
					case EARTH: dir = 3 * sixteenth; 	break;
					case WATER: dir = 5 * sixteenth; 	break;
					case AIR: 	dir = 7 * sixteenth; 	break;
				}
				float dis = 120*Settings.scale;
				float dx = (float) (Math.cos(dir)*dis);
				float dy = (float) (Math.sin(dir)*dis);;
				orb.cX = current_x+dx;
				orb.cY = current_y+dy;
				orb.hb.cX = orb.cX;
				orb.hb.cY = orb.cY;
				orb.render(sb);
			}
		}
	}

	public static ArrayList<ElementOrb> getElementOrbs() {
		return elementOrbList;
	}

	public static void addElementOrb(ElementOrb orb) {
		elementOrbList.add(orb);
	}

	public static boolean elementalEnergyIsEnabled() {
		return elementalEnergyEnabled;
	}

	public static String getElementName(Element element) {
		switch(element) {
			case AIR: return "Air";
			case WATER: return "Water";
			case EARTH: return "Earth";
			case FIRE: return "Fire";
		}
		return "?";
	}

}