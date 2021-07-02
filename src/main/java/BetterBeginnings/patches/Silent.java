package BetterBeginnings.patches;

import BetterBeginnings.cards.green.Plague;
import BetterBeginnings.cards.green.StabAndStrike;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.characters.TheSilent;

import java.util.ArrayList;

@SpirePatch2(clz = TheSilent.class, method = "getStartingDeck")
public class Silent {
    public static void Postfix(TheSilent __instance, ArrayList<String> __result)
    {
        __result.remove(Defend_Green.ID);
        __result.remove(Strike_Green.ID);
        __result.add(Plague.ID);
        __result.add(StabAndStrike.ID);
    }
}