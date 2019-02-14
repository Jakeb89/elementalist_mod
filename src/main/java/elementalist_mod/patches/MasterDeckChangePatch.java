package elementalist_mod.patches;

import elementalist_mod.cards.AbstractElementalistCard;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.lang.reflect.Field;

public class MasterDeckChangePatch {

    // TODO: Note for Yourself event doesn't use an ObtainEffect for whatever reason, so patch that too.

    @SpirePatch(clz = FastCardObtainEffect.class, method = "update")
    public static class FastCardObtainEffectPatch {
        public static void Postfix(FastCardObtainEffect obj) {
            if (obj.duration <= 0.0F){
                try{
                    Field cardField = FastCardObtainEffect.class.getDeclaredField("card");
                    cardField.setAccessible(true);
                    AbstractCard card = (AbstractCard)cardField.get(obj);
                    if (card instanceof AbstractElementalistCard){
                        ((AbstractElementalistCard) card).onAddedToMasterDeck();
                    }
                    //Beaked.onMasterDeckChange();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @SpirePatch(clz = ShowCardAndObtainEffect.class, method = "update")
    public static class ShowCardAndObtainEffectPatch {
        public static void Postfix(ShowCardAndObtainEffect obj) {
            if (obj.duration <= 0.0F){
                try{
                    Field cardField = ShowCardAndObtainEffect.class.getDeclaredField("card");
                    cardField.setAccessible(true);
                    AbstractCard card = (AbstractCard)cardField.get(obj);
                    if (card instanceof AbstractElementalistCard){
                        ((AbstractElementalistCard) card).onAddedToMasterDeck();
                    }
                    //Beaked.onMasterDeckChange();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @SpirePatch(clz= CardGroup.class, method="removeCard",paramtypez = {AbstractCard.class})
    public static class onRemoveCard {
        public static void Prefix(CardGroup obj, final AbstractCard c) {
            if (c instanceof AbstractElementalistCard && obj.type == CardGroup.CardGroupType.MASTER_DECK) {
                ((AbstractElementalistCard) c).onRemovedFromMasterDeck();
            }
            //Beaked.onMasterDeckChange();
        }
    }
}