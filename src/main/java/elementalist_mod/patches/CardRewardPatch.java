package elementalist_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.relics.BlueRibbon;
import elementalist_mod.relics.GreenCirclet;
import elementalist_mod.relics.RedRibbon;
import elementalist_mod.relics.YellowCirclet;

@SpirePatch(clz = AbstractDungeon.class, method = "getCard", paramtypez = { AbstractCard.CardRarity.class })
public class CardRewardPatch {

	public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardRarity rarity) {
		ElementalistMod.log("CardRewardPatch>Prefix(...)");
		ElementalistMod.log("rarity: " + rarity);
		/*
		 * if(obj instanceof AbstractElementalistCard) { AbstractElementalistCard card =
		 * (AbstractElementalistCard) obj; if(card.isEmblem) { text[0] = ""; } }
		 */
		boolean hasBlueRelic 	= AbstractDungeon.player.hasRelic(BlueRibbon.ID);
		boolean hasRedRelic 	= AbstractDungeon.player.hasRelic(RedRibbon.ID);
		boolean hasGreenRelic 	= AbstractDungeon.player.hasRelic(GreenCirclet.ID);
		boolean hasYellowRelic 	= AbstractDungeon.player.hasRelic(YellowCirclet.ID);

		ElementalistMod.log("relics: " + hasBlueRelic + ", " + hasRedRelic + ", " + hasGreenRelic + ", " + hasYellowRelic);

		AbstractCard returnCard = null;
		boolean conditionsSatisfied = false;
		
		while(!conditionsSatisfied) {
			
			switch (rarity) {
			case RARE:
				returnCard = AbstractDungeon.rareCardPool.getRandomCard(true);
				break;
			case UNCOMMON:
				returnCard = AbstractDungeon.uncommonCardPool.getRandomCard(true);
				break;
			case COMMON:
				returnCard = AbstractDungeon.commonCardPool.getRandomCard(true);
				break;
			case CURSE:
				returnCard = AbstractDungeon.curseCardPool.getRandomCard(true);
				break;
			default:
				returnCard = null;
				break;
			}
			
			if(returnCard != null && returnCard instanceof AbstractElementalistCard) {
				AbstractElementalistCard elementalistCard = (AbstractElementalistCard)returnCard;
				if(hasBlueRelic 	&& elementalistCard.getElementalTags().contains("Water")) 	conditionsSatisfied = true;
				if(hasRedRelic 		&& elementalistCard.getElementalTags().contains("Fire")) 	conditionsSatisfied = true;
				if(hasGreenRelic 	&& elementalistCard.getElementalTags().contains("Air")) 	conditionsSatisfied = true;
				if(hasYellowRelic 	&& elementalistCard.getElementalTags().contains("Earth")) 	conditionsSatisfied = true;
				if(Math.random() < 0.25) conditionsSatisfied = true;
				String tags = "";
				for(String tag : elementalistCard.getElementalTags()) {
					tags += tag + ", ";
				}
				ElementalistMod.log("elemental tags: " + tags);
			}else {
				conditionsSatisfied = true;
			}
		}

		if(returnCard != null) {
			ElementalistMod.log(returnCard.name);
			ElementalistMod.log("relics: " + hasBlueRelic + ", " + hasRedRelic + ", " + hasGreenRelic + ", " + hasYellowRelic);
		}else {
			ElementalistMod.log("returnCard was null??");
		}
		return SpireReturn.Return(returnCard);

		// logger.info("No rarity on getCard in Abstract Dungeon");
		//return null;

	}

}
