package elementalist_mod.patches;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;

import elementalist_mod.cards.AbstractElementalistCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(clz = AbstractCard.class, method = "renderType", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch" })
public class ElementalCardRenderPatch3 {
	@SpireInsertPatch(
        localvars={"text"},
        locator = Locator.class
		)

	public static void Insert(AbstractCard obj, SpriteBatch sb, @ByRef String[] text) {
		if(obj instanceof AbstractElementalistCard) {
			AbstractElementalistCard card = (AbstractElementalistCard) obj;
			if(card.isEmblem) {
				text[0] = "";
			}
		}
	}
	

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}

