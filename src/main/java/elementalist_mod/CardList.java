package elementalist_mod;

import java.util.ArrayList;

import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.basic.*;
import elementalist_mod.cards.common.*;
import elementalist_mod.cards.rare.*;
import elementalist_mod.cards.special.*;
import elementalist_mod.cards.uncommon.*;

public class CardList {
	private static ArrayList<AbstractElementalistCard> cards;
	private static ArrayList<String> starterDeck;
	
	static{
		cards = new ArrayList<AbstractElementalistCard>();
		starterDeck = new ArrayList<String>();

		// Basic, in Starter
		addCard(new Strike_Elemblue(), 1);
		addCard(new Defend_Elemblue(), 5);
		
		//addCard(new Elemental_Strike(), 1);
		//addCard(new Elemental_Defend(), 0);
		


		
		// Common, in Starter

		addCard(new Firestrike(), 1);
		addCard(new Earthstrike(), 1);
		addCard(new Waterstrike(), 1);
		addCard(new Airstrike(), 1);

		addCard(new Crystal_Chalk(), 1); //Actually Basic, but this makes the starting deck better organized.
		
		addCard(new Fire_Emblem(), 0);
		addCard(new Water_Emblem(), 0);
		addCard(new Earth_Emblem(), 0);
		addCard(new Air_Emblem(), 0);

		addCard(new RelicCard_RedRibbon(), 0);
		addCard(new RelicCard_BlueRibbon(), 0);
		addCard(new RelicCard_GreenCirclet(), 0);
		addCard(new RelicCard_YellowCirclet(), 0);
		

		addCard(new Golemancy(), 1);
		

		//addCard(new Check_The_Pins(), 0);
		
		
		// Special

		addCard(new Ignis_Eye());
		addCard(new Aqua_Mind());
		addCard(new Terra_Heart());
		addCard(new Zephyr_Soul());
		
		
		// Common
		
		addCard(new Spark());
		addCard(new Flame());
		addCard(new Prepared_Sand());
		addCard(new Etherstrike());
		addCard(new Mist());
		addCard(new Breeze());
		addCard(new Stone_to_Mud());
		addCard(new Confluence());
		addCard(new Momentum());
		addCard(new Spout());
		addCard(new Monument());
		addCard(new Overheat());
		addCard(new Cyclone());
		addCard(new Wards());
		addCard(new Caution());
		addCard(new Emberveil());
		
		// Status?
		addCard(new Autopetrify());

		// Uncommon
		addCard(new Magma_Shot());
		addCard(new Blaze());
		addCard(new Vaccuum());
		addCard(new Ignition());
		addCard(new Rising_Tides());
		addCard(new Low_Tide());
		addCard(new Concentricity());
		addCard(new Refract());
		addCard(new Heavensfall());
		addCard(new Solar_Form());
		addCard(new Lunar_Form());
		addCard(new Sidereal_Form());
		addCard(new Gaian_Form());
		addCard(new Astral_Form());
		addCard(new Widdershins());
		addCard(new Aegis());
		addCard(new Mage_Hand());
		addCard(new Geomancy());
		addCard(new Ferruchemy());
		addCard(new Pressurize());
		addCard(new Cauterize());
		addCard(new Aero());
		addCard(new Undertow());


		// Rare
		addCard(new Fission_Lance());
		addCard(new Reckless_Dance());
		addCard(new Quadmire());
		addCard(new Multification());
		addCard(new Absolute_Zero());
		addCard(new Quake());
		addCard(new Accelerant());
		addCard(new Rising_Vapors());
		addCard(new Erosion());
		addCard(new Kiln());
		addCard(new Glaciate());
		addCard(new Impact());
		addCard(new Inferno());
		addCard(new Wirbelwight());
		

		addCard(new Burning_Soul());
		addCard(new Mirror_Circlet());
		addCard(new Aetheric_Shield());
		addCard(new Dragon_Bigram());
		addCard(new Tsunami());
		
		
		// <--current place in implementation checklist
	}
	
	private static void addCard(AbstractElementalistCard card) {
		addCard(card, 0);
	}
	
	private static void addCard(AbstractElementalistCard card, int numberInDeck) {
		cards.add(card);
		for(int i=0; i<numberInDeck; i++) {
			starterDeck.add(card.cardID);
		}
	}

	
	public static ArrayList<AbstractElementalistCard> getCards() {
		return cards;
	}
	
	public static ArrayList<String> getStarterDeck() {
		return starterDeck;
	}
}
