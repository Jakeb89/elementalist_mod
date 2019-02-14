package elementalist_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.random.Random;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.characters.TheElementalist;
import elementalist_mod.events.ElementalFocusEvent;
import elementalist_mod.relics.BlueRibbon;
import elementalist_mod.relics.GreenCirclet;
import elementalist_mod.relics.RedRibbon;
import elementalist_mod.relics.YellowCirclet;

@SpirePatch(clz = AbstractDungeon.class, method = "generateEvent", paramtypez = { Random.class })
public class EventPickerPatch {

	public static SpireReturn<AbstractEvent> Prefix(Random rng) {
		
		if(AbstractDungeon.player instanceof TheElementalist && !ElementalistMod.ribbonEventFired) {
			if(!AbstractDungeon.player.hasRelic(BlueRibbon.ID) &&
				!AbstractDungeon.player.hasRelic(RedRibbon.ID) &&
				!AbstractDungeon.player.hasRelic(GreenCirclet.ID) &&
				!AbstractDungeon.player.hasRelic(YellowCirclet.ID)) {
				if(rng.random(1.0F) < 0.25) {
					ElementalistMod.ribbonEventFired = true;
					return SpireReturn.Return(new ElementalFocusEvent());
				}
			}
		}
		return SpireReturn.Continue();
	}

}
