package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RulesTest {
    Game game;
    Board board;
    Rules rules;
    Resource resource;

    @BeforeEach
    public void initialize(){
        game = new Game(new BotType[]{BotType.RANDOM},4);
        board = game.getBoard();
        rules = game.getRules();
        resource = game.getResource();
    }

    @Test
    void parcelNextToCenter(){
        assertTrue(rules.isPlayableParcel(new Coordinate(0,-1,1)));
    }

    @Test
    void parcelNextToTwoPlacedParcels(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertTrue(rules.isPlayableParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void parcelOnCenter(){
        assertFalse(rules.isPlayableParcel(new Coordinate()));
    }

    @Test
    void parcelAwayFromCenter(){
        assertFalse(rules.isPlayableParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void parcelNextToOnePlacedParcel(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertFalse(rules.isPlayableParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void canalOnTwoPlacedParcelsNextToCenter(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertTrue(rules.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void canalsOnTwoPlacedParcelsNextToAnotherCanal(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(),new Coordinate(1,-2,1));
        board.placeParcel(new Parcel(),new Coordinate(2,-2,0));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertTrue(rules.isPlayableCanal(new Coordinate(1,-2,1),new Coordinate(1,-1,0))); //isPlacedCanal(coordinate2, coordinate)
        board.placeCanal(new Canal(),new Coordinate(1,-2,1),new Coordinate(1,-1,0));
        assertTrue(rules.isPlayableCanal(new Coordinate(1,-2,1),new Coordinate(2,-2,0))); //isPlacedCanal(coordinate1, coordinate)
    }

    @Test
    void canalsOnZeroPlacedParcel(){
        assertFalse(rules.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void canalsOnOnePlacedParcel(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertFalse(rules.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void canalsOnTwoPlacedParcelsNotNextToEachOther(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(1,0,-1));
        assertFalse(rules.isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,0,-1)));
    }

    @Test
    void canalOnCenterAndOnePlacedParcel(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertFalse(rules.isPlayableCanal(new Coordinate(),new Coordinate(0,-1,1)));
    }

    @Test
    void characterMoveOnPlacedParcelNextToCenter(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertTrue(rules.isMovableCharacter(CharacterType.PANDA,new Coordinate(0,-1,1)));
    }

    @Test
    void characterMoveOnPlacedParcelAwayFromCenter(){
        board.placeParcel(new Parcel(),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(),new Coordinate(0,-2,2));
        assertTrue(rules.isMovableCharacter(CharacterType.PANDA,new Coordinate(0,-2,2)));
    }

    @Test
    void characterMoveOnNotSameLine(){
        board.placeParcel(new Parcel(),new Coordinate(1,-2,1));
        assertFalse(rules.isMovableCharacter(CharacterType.PANDA,new Coordinate(1,-2,1)));
    }

    @Test
    void characterMoveOnNPlacedParcelAwayFromCenterNoParcelBetween(){
        board.placeParcel(new Parcel(),new Coordinate(0,-2,2));
        assertFalse(rules.isMovableCharacter(CharacterType.PANDA,new Coordinate(0,-2,2)));
    }
}