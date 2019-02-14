package elementalist_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = AbstractCard.class, method = "renderInLibrary", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch" })
public class ElementalCardRenderInLibraryPatch {


	public static void Postfix(AbstractCard obj, final SpriteBatch sb) {
		ElementalCardRenderer.RenderPostfix(obj, sb, false);
	}

}

