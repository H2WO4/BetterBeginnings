package BetterBeginnings.powers;

import BetterBeginnings.BetterBeginnings;
import BetterBeginnings.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BetterBeginnings.BetterBeginnings.makePowerPath;

public class SubzeroPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = BetterBeginnings.makeID("SubzeroPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Subzero_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Subzero_32.png"));

    public SubzeroPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new RemoveNextOrbAction());
    }

    @Override
    public AbstractPower makeCopy() {
        return new SubzeroPower(owner);
    }
}