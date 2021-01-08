package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;
import java.util.stream.Collectors;

public class MissionPeasantStrat extends Strategie {

    public MissionPeasantStrat(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    public void stratOneTurn(Mission mission){
        if ( isJudiciousMovePeasant((PeasantMission) mission) )
            gameInteraction.moveCharacter(CharacterType.PEASANT,strategyMovePeasant((PeasantMission) mission));
        else if ( isJudiciousPlaceParcel() )
            strategyPlaceParcel((PeasantMission) mission);
        else if ( isJudiciousPlaceCanal() )
            strategyPlaceCanal();
        else if ( !gameInteraction.contains(ActionType.MOVE_PANDA) &&
                  !possibleCoordinatesPanda().isEmpty() )
            gameInteraction.moveCharacter(CharacterType.PANDA,possibleCoordinatesPanda().get(0));
    }

    /**<b><u>IS JUDICIOUS METHODS</b>
     */

    boolean isJudiciousMovePeasant(PeasantMission peasantMission){
        return  strategyMovePeasant(peasantMission) != null &&
                !gameInteraction.contains(ActionType.MOVE_PEASANT);
    }

    boolean isJudiciousPlaceCanal() {
        return  !gameInteraction.contains(ActionType.DRAW_CANAL) &&
                !gameInteraction.contains(ActionType.PLACE_CANAL) &&
                gameInteraction.getResourceSize(ResourceType.CANAL) > 0;
    }

    boolean isJudiciousPlaceParcel() {
        return  !gameInteraction.contains(ActionType.DRAW_PARCELS) &&
                gameInteraction.getResourceSize(ResourceType.PARCEL) > 0;
    }

    /**<b><u>STRATEGIES METHODS</b>
     */

    public Coordinate strategyMovePeasant(PeasantMission mission) {
        if (!mission.getImprovementType().equals(ImprovementType.WHATEVER))
            for (Coordinate coordinate : possibleCoordinatesPeasant())
                if (gameInteraction.getPlacedParcelInformation(coordinate).getColorType().equals(mission.getColorType()) && gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(mission.getImprovementType()))
                    return coordinate;
        return null;
    }

    private void strategyPlaceParcel(PeasantMission peasantMission) {
        try{List<ParcelInformation> parcelInformationList = gameInteraction.drawParcels();

            if (parcelInformationList
                    .stream()
                    .map(ParcelInformation::getColorType)
                    .collect(Collectors.toList())
                    .contains(peasantMission.getColorType())){
                Coordinate coordinate = null;

                for (Coordinate c : possibleCoordinatesParcel())
                    if (gameInteraction.getRules().isMovableCharacter(CharacterType.PEASANT,c))
                        coordinate = c;

                if (coordinate == null)
                    coordinate = possibleCoordinatesParcel().get(0);

                if (parcelInformationList
                        .stream()
                        .filter(parcelInformation -> parcelInformation.getColorType().equals(peasantMission.getColorType()))
                        .map(ParcelInformation::getImprovementType)
                        .collect(Collectors.toList())
                        .contains(peasantMission.getImprovementType()))
                    gameInteraction.selectParcel(parcelInformationList
                            .stream()
                            .filter(parcelInformation -> parcelInformation.getColorType().equals(peasantMission.getColorType()) &&
                            parcelInformation.getImprovementType().equals(peasantMission.getImprovementType()))
                            .collect(Collectors.toList()).get(0) );
                else
                    gameInteraction.selectParcel(parcelInformationList
                            .stream()
                            .filter(parcelInformation -> parcelInformation.getColorType().equals(peasantMission.getColorType()))
                            .collect(Collectors.toList()).get(0) );

                gameInteraction.placeParcel(coordinate);
            }
            else {
                gameInteraction.selectParcel(parcelInformationList.get(0));
                gameInteraction.placeParcel(possibleCoordinatesParcel().get(0));
            }
        }
        catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public void strategyPlaceCanal() {
        gameInteraction.drawCanal();
        if(!possibleCoordinatesCanal().isEmpty()){
            Coordinate[] coordinates = possibleCoordinatesCanal().get(0);
            gameInteraction.placeCanal(coordinates[0],coordinates[1]);
        }
    }

    /**<b><u>NUMBER OF MOVES TO DO THE MISSION METHODS</b>
     */

    public int howManyMoveToDoMission(Mission mission) {
        PeasantMission peasantMission = (PeasantMission) mission;
        if(     !isAlreadyFinished(peasantMission) &&
                isJudiciousMovePeasant((PeasantMission) mission) &&
                !gameInteraction.contains(ActionType.MOVE_PEASANT) ){
            if (    peasantMission.getImprovementType().equals(ImprovementType.WHATEVER) ||
                    notExistGoodMovableParcel(peasantMission) )
                return -1;
            else if ( isFinishedInOneTurn(peasantMission) )
                return 1;
            return nbMovePeasant(peasantMission);
        }
        return -1;
    }

    boolean isAlreadyFinished(PeasantMission peasantMission) {
        for (Coordinate coordinate : gameInteraction.getPlacedCoordinatesByParcelInformation(new ParcelInformation(peasantMission.getColorType(),peasantMission.getImprovementType())))
            if (gameInteraction.getPlacedParcelsNbBamboo(coordinate) == 4)
                return true;
        return false;
    }

    boolean isFinishedInOneTurn(PeasantMission peasantMission) {
        if (strategyMovePeasant(peasantMission) != null)
            if (    gameInteraction.getPlacedParcelsNbBamboo(strategyMovePeasant(peasantMission)) == 2 &&
                    peasantMission.getImprovementType().equals(ImprovementType.FERTILIZER) )
                return true;
            else
                return gameInteraction.getPlacedParcelsNbBamboo(strategyMovePeasant(peasantMission)) == 3;
        return false;
    }

    boolean notExistGoodMovableParcel(PeasantMission mission){
        int nbGoodParcelPlaced = 0;
        List<Coordinate> coordinates = gameInteraction.getPlacedCoordinatesByColor(mission.getColorType());

        for (Coordinate coordinate : coordinates)
            if ( gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(mission.getImprovementType()) )
                nbGoodParcelPlaced++;

        return nbGoodParcelPlaced == 0;
    }

    private int nbMovePeasant(PeasantMission peasantMission){
        int maxNbBamboo = 0;

        for (Coordinate coordinate : gameInteraction.getPlacedCoordinatesByParcelInformation(new ParcelInformation(peasantMission.getColorType(),peasantMission.getImprovementType())))
            if (gameInteraction.getPlacedParcelsNbBamboo(coordinate) > maxNbBamboo)
                maxNbBamboo = gameInteraction.getPlacedParcelsNbBamboo(coordinate);

        return 5 - maxNbBamboo;
    }
}