package elementalist_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = AbstractCard.class, method = "renderTitle", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch" })
public class ElementalCardRenderPatch2 {


	public static void Prefix(AbstractCard obj, final SpriteBatch sb) {
		ElementalCardRenderer.RenderTitlePrefix(obj, sb);
	}

}

