package BetterBeginnings.patches;

import BetterBeginnings.cards.red.Brute;
import BetterBeginnings.cards.red.Cowardice;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;

import java.util.ArrayList;

@SpirePatch2(clz = com.megacrit.cardcrawl.characters.Ironclad.class, method = "getStartingDeck")
public class Ironclad {
    public static void Postfix(com.megacrit.cardcrawl.characters.Ironclad __instance, ArrayList<String> __result)
    {
        __result.remove(Defend_Red.ID);
        __result.remove(Strike_Red.ID);
        __result.add(Brute.ID);
        __result.add(Cowardice.ID);
    }
}