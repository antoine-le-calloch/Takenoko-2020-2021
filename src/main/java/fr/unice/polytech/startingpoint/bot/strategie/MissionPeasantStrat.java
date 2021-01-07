package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;
import java.util.stream.Collectors;

public class MissionPeasantStrat extends Strategie {

    /**
     * @param bot
     */
    public MissionPeasantStrat(Bot bot) {
        super(bot);
    }

    /**
     * <p>The actions of the bot during his turn.</p>
     * @param mission
     */
    public void stratOneTurn(Mission mission){
        if (isJudiciousMovePeasant((PeasantMission) mission))
            bot.movePeasant(strategyMovePeasant((PeasantMission) mission));
        else if (isJudiciousPlaceParcel())
            strategyPlaceParcel(mission.getColorType());
        else if (isJudiciousPlaceCanal())
            strategyPlaceCanal(mission.getColorType());
        else if (!bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty())
            bot.movePanda(possibleCoordinatesPanda().get(0));
    }

    void strategyPlaceCanal(ColorType colorType) {
        bot.getGameInteraction().drawCanal();
        if(!possibleCoordinatesCanal().isEmpty()){
            Coordinate[] coordinates = possibleCoordinatesCanal().get(0);
            bot.getGameInteraction().placeCanal(coordinates[0],coordinates[1]);
        }
    }

    boolean isJudiciousPlaceCanal() {
        return !bot.getGameInteraction().contains(ActionType.DRAW_CANAL) && !bot.getGameInteraction().contains(ActionType.PLACE_CANAL) && (bot.getGameInteraction().getResourceSize(ResourceType.CANAL) > 0) ;
    }

    public void strategyPlaceParcel(ColorType colorType) {
        try {
            List<ParcelInformation> parcelInformationList = bot.getGameInteraction().drawParcels();
            if (parcelInformationList.stream().map(ParcelInformation::getColorType).collect(Collectors.toList()).contains(colorType)){
                Coordinate coordinate = null;
                for (Coordinate c : possibleCoordinatesParcel()){
                    if (bot.getGameInteraction().getRules().isMovableCharacter(CharacterType.PANDA,c))
                        coordinate = c;
                }
                if (coordinate == null)
                    coordinate = possibleCoordinatesParcel().get(0);
                bot.selectParcel(parcelInformationList.stream().filter(parcelInformation -> parcelInformation.getColorType().equals(colorType)).collect(Collectors.toList()).get(0));
                bot.placeParcel(coordinate);
            }
            else {
                bot.selectParcel(parcelInformationList.get(0));
                bot.placeParcel(possibleCoordinatesParcel().get(0));
            }

        } catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    public boolean isJudiciousPlaceParcel() {
        return !bot.getGameInteraction().contains(ActionType.DRAW_PARCELS) && (bot.getGameInteraction().getResourceSize(ResourceType.PARCEL) > 0) ;
    }

    public int howManyMoveToDoMission(Mission mission) {
        PeasantMission peasantMission = (PeasantMission) mission;
        if(!isAlreadyFinished(peasantMission) &&
                isJudiciousMovePeasant((PeasantMission) mission) &&
                        (!bot.getGameInteraction().contains(ActionType.MOVE_PANDA) && !possibleCoordinatesPanda().isEmpty()) ){
            if (peasantMission.getImprovementType().equals(ImprovementType.WHATEVER) || notExistGoodMovableParcel(peasantMission))
                return -1;
            else if (isFinishedInOneTurn(peasantMission))
                return 1;
            return nbMovePeasant(peasantMission);
        }
        return -1;
    }

    public boolean isAlreadyFinished(PeasantMission peasantMission) {
        for (Coordinate coordinate : bot.getGameInteraction().getPlacedCoordinatesByParcelInformation(new ParcelInformation(peasantMission.getColorType(),peasantMission.getImprovementType()))){
            if (bot.getGameInteraction().getPlacedParcelsNbBamboo(coordinate) == 4)
                return true;
        }
        return false;
    }

    public boolean isFinishedInOneTurn(PeasantMission peasantMission) {
        if (strategyMovePeasant(peasantMission) != null)
            if (bot.getGameInteraction().getPlacedParcelsNbBamboo(strategyMovePeasant(peasantMission)) == 2 &&
                    peasantMission.getImprovementType().equals(ImprovementType.WATERSHED))
                return true;
            else
                return bot.getGameInteraction().getPlacedParcelsNbBamboo(strategyMovePeasant(peasantMission)) == 3;
        return false;
    }

    public boolean notExistGoodMovableParcel(PeasantMission mission){
        int nbGoodParcelPlaced = 0;
        List<Coordinate> coordinates = bot.getGameInteraction().getPlacedCoordinatesByColor(mission.getColorType());
        for (Coordinate coordinate : coordinates){
            if (bot.getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType().equals(mission.getImprovementType()))
                nbGoodParcelPlaced++;
        }
        return nbGoodParcelPlaced == 0;
    }

    public int nbMovePeasant(PeasantMission peasantMission){
        int maxNbBamboo = 0;
        for (Coordinate coordinate : bot.getGameInteraction().getPlacedCoordinatesByParcelInformation(new ParcelInformation(peasantMission.getColorType(),peasantMission.getImprovementType()))){
            if (bot.getGameInteraction().getPlacedParcelsNbBamboo(coordinate) > maxNbBamboo)
                maxNbBamboo = bot.getGameInteraction().getPlacedParcelsNbBamboo(coordinate);
        }
        return 5 - maxNbBamboo;
    }

    public boolean isJudiciousMovePeasant(PeasantMission peasantMission){
        return strategyMovePeasant(peasantMission) != null && !bot.getGameInteraction().contains(ActionType.MOVE_PEASANT);
    }

    /**
     *            <b>The list of coordinates containing places where we want to move the Peasant.</b>
     * @return <b>Return the first coordinate where the parcel has at least two bamboos.</b>
     */
    public Coordinate strategyMovePeasant(PeasantMission mission) {
        if (!mission.getImprovementType().equals(ImprovementType.WHATEVER))
            for (Coordinate coordinate : possibleCoordinatesPeasant())
                if (bot.getGameInteraction().getPlacedParcelInformation(coordinate).getColorType().equals(mission.getColorType()))
                    return coordinate;
        return null;
    }
}