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
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

import elementalist_mod.cards.AbstractElementalistCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class AbstractPlayerDamagePatch {
	@SpireInsertPatch(
        localvars={"damageAmount"},
        locator = Locator.class
		)

	public static void Insert(AbstractPlayer obj, DamageInfo info, @ByRef int[] damageAmount) {
	      for (AbstractCard card : AbstractDungeon.player.hand.group) {
	    	  if(card instanceof AbstractElementalistCard) {
	    		  damageAmount[0] = ((AbstractElementalistCard)card).onLoseHp(damageAmount[0]);
	    	  }
	      }
	}
	

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        	ArrayList<Matcher> matcherList = new ArrayList<Matcher>();
        	matcherList.add( new Matcher.MethodCallMatcher(AbstractPower.class, "onLoseHp") );
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findInOrder(ctMethodToPatch, matcherList, finalMatcher);
        }
    }
}

