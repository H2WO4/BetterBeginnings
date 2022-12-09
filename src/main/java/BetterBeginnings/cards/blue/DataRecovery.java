package BetterBeginnings.cards.blue;

import BetterBeginnings.BetterBeginnings;
import gameplayatoms.actions.common.MoveRandDrawAction;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BetterBeginnings.BetterBeginnings.makeCardPath;

public class DataRecovery extends CustomCard
{

    public static final String ID = BetterBeginnings.makeID(DataRecovery.class.getSimpleName());
    public static final String IMG = makeCardPath("DataRecovery.png");
    public static final CardColor COLOR = CardColor.BLUE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public DataRecovery()
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 4;
        this.block = this.baseBlock;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new GainBlockAction(p, p, this.block));

        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.addToBot(new MoveCardsAction(temp, p.discardPile, 2, (cards) ->
        {
            for (AbstractCard card : cards)
                this.addToTop(new MoveRandDrawAction(card, temp));
        }));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(2);
            initializeDescription();
        }
    }
}
