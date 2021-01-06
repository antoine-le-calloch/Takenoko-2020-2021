package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.IllegalTypeException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.board.BoardRules;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.game.playerdata.PlayerData;
import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GameInteraction {
    private final int NB_MAX_MISSION = 5;
    private final Game game;

    GameInteraction(Game game){
        this.game = game;
    }

    public PlayerData getPlayerData() {
        return game.getPlayerData();
    }

    public void drawCanal() {
        if (getPlayerData().add(ActionType.DRAW_CANAL)){
            getPlayerData().looseStamina();
            getPlayerData().addCanal(game.getResource().drawCanal());
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void drawMission(MissionType missionType) {
        if (getPlayerData().add(ActionType.DRAW_MISSION)){
            if (getPlayerData().getMissionsSize() <= NB_MAX_MISSION){
                getPlayerData().looseStamina();
                switch (missionType){
                    case PANDA:
                        getPlayerData().addMission(game.getResource().drawPandaMission());
                        break;
                    case PARCEL:
                        getPlayerData().addMission(game.getResource().drawParcelMission());
                        break;
                    case PEASANT:
                        getPlayerData().addMission(game.getResource().drawPeasantMission());
                        break;
                }
            }
            else{
                getPlayerData().remove(ActionType.DRAW_MISSION);
                throw new RulesViolationException("You already have five missions in your inventory.");
            }
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public List<ParcelInformation> drawParcels() {
        if (getPlayerData().add(ActionType.DRAW_PARCELS)){
            getPlayerData().looseStamina();
            getPlayerData().saveParcels(game.getResource().drawParcels());
            List<ParcelInformation> parcelInformationList = new ArrayList<>();
            for(Parcel parcel : getPlayerData().getParcelsSaved())
                parcelInformationList.add(parcel.getParcelInformation());
            return parcelInformationList;
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void selectParcel(ParcelInformation parcelInformation){
        if (getPlayerData().add(ActionType.SELECT_PARCEL)){
            if (getPlayerData().contains(ActionType.DRAW_PARCELS)) {
                for (Parcel parcel : getPlayerData().getParcelsSaved())
                    if (parcel.getParcelInformation() == parcelInformation) {
                        game.getResource().selectParcel(parcel);
                        getPlayerData().saveParcel(parcel);
                        return;
                    }
                throw new RulesViolationException("Wrong Parcel asked.");
            }
            else
                throw new RulesViolationException("You haven’t drawn.");
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void thunderstormAction(Coordinate coordinate){
        if(getPlayerData().add(ActionType.WEATHER)){
             if(getPlacedCoordinates().contains(coordinate))
                 game.getBoard().moveCharacter(CharacterType.PANDA,coordinate);
             else
                 throw new BadCoordinateException("The character can't move to this coordinate : " + coordinate.toString());
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void rainAction(Coordinate coordinate){
        if(getPlayerData().add(ActionType.WEATHER)){
            if (game.getBoard().isPlacedAndIrrigatedParcel(coordinate))
                    game.getBoard().getPlacedParcels().get(coordinate).addBamboo();
            else
                throw new BadCoordinateException("The parcel with the coordinate : " + coordinate.toString() + " is not placed");
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void placeParcel(Coordinate coordinate){
        if (getPlayerData().add(ActionType.PLACE_PARCEL)){
            if (getPlayerData().contains(ActionType.DRAW_PARCELS) && getPlayerData().contains(ActionType.SELECT_PARCEL)){
                if(game.getRules().isPlayableParcel(coordinate)){
                    game.getBoard().placeParcel(getPlayerData().getParcel(),coordinate);
                }
                else{
                    getPlayerData().remove(ActionType.PLACE_PARCEL);
                    throw new BadCoordinateException("The parcel can't be place on this coordinate : " + coordinate.toString());
                }
            }
            else
                throw new RulesViolationException("You haven’t drawn or selected a parcel.");
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void placeCanal(Coordinate coordinate1, Coordinate coordinate2){
        if (getPlayerData().add(ActionType.PLACE_CANAL)) {
            if (game.getRules().isPlayableCanal(coordinate1, coordinate2))
                game.getBoard().placeCanal(getPlayerData().pickCanal(), coordinate1, coordinate2);
            else{
                getPlayerData().remove(ActionType.PLACE_CANAL);
                throw new BadCoordinateException("The canal can't be place on these coordinates : " + coordinate1.toString() + " " + coordinate2.toString());
            }
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void moveCharacter(CharacterType characterType, Coordinate coordinate){
        ColorType ColorBambooEat;
        if (getPlayerData().add(ActionType.get(characterType))) {
            if (game.getRules().isMovableCharacter(characterType, coordinate)) {
                getPlayerData().looseStamina();
                if((ColorBambooEat = game.getBoard().moveCharacter(characterType, coordinate)) != null)
                    getPlayerData().addBamboo(ColorBambooEat);
            }
            else{
                getPlayerData().remove(ActionType.get(characterType));
                throw new BadCoordinateException("The character can't move to this coordinate : " + coordinate.toString());
            }
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    /**
     * <h1><u>BOT GETTERS</u></h1>
     */

    public List<Coordinate> getAllParcelsIrrigated(){
        return getPlacedCoordinates()
                .stream()
                .filter(this::isIrrigatedParcel)
                .collect(Collectors.toList());
    }

    public int getNumberMissionsDone(){
        return getPlayerData().getMissionsDone();
    }

    public boolean isPlacedParcel(Coordinate coordinate) {
        return game.getBoard().isPlacedParcel(coordinate);
    }

    public boolean isIrrigatedParcel(Coordinate coordinate) {
        return game.getBoard().isPlacedAndIrrigatedParcel(coordinate);
    }

    public ParcelInformation getPlacedParcelInformation(Coordinate coordinate) {
        return game.getBoard().getPlacedParcels().get(coordinate).getParcelInformation();
    }

    public int getPlacedParcelsNbBamboo(Coordinate coordinate) {
        return game.getBoard().getPlacedParcels().get(coordinate).getNbBamboo();
    }

    public List<ActionType> getActionTypeList() {
        return game.getPlayerData().getActionTypeList();
    }

    public boolean contains(ActionType action){
        return game.getPlayerData().contains(action);
    }

    public int getStamina(){
        return game.getPlayerData().getStamina();
    }

    public BoardRules getRules(){
        return  game.getRules();
    }

    public List<Coordinate> getPlacedCoordinates(){
        return new ArrayList<>(game.getBoard().getPlacedParcels().keySet());
    }
    public List<Coordinate> getPlacedCoordinatesByColor(ColorType color){
        return (getPlacedCoordinates()
               .stream()
               .filter(coordinate -> getPlacedParcelInformation(coordinate).getColorType().equals(color))
               .collect(Collectors.toList()));
    }

    /**
     * @return <b>The {@link ParcelMission} list of the current bot.</b>
     */
    public List<ParcelMission> getInventoryParcelMissions(){
        return getPlayerData().getParcelMissions();
    }

    /**
     * @return <b>The {@link PandaMission} list of the current bot.</b>
     */
    public List<PandaMission> getInventoryPandaMissions(){
        return getPlayerData().getPandaMissions();
    }

    /**
     * @return <b>The {@link PeasantMission} list of the current bot.</b>
     */
    public List<PeasantMission> getInventoryPeasantMissions(){
        return getPlayerData().getPeasantMissions();
    }

    public int[] getInventoryBamboo() {
        return getPlayerData().getInventoryBamboo();
    }

    public int getResourceSize(ResourceType resourceType){
        switch (resourceType){
            case PEASANT_MISSION:
                return game.getResource().getDeckPeasantMission().size();
            case PANDA_MISSION:
                return game.getResource().getDeckPandaMission().size();
            case PARCEL_MISSION:
                return game.getResource().getDeckParcelMission().size();
            case PARCEL:
                return game.getResource().getDeckParcel().size();
            case CANAL:
                return game.getResource().getDeckCanal().size();
            case ALL_MISSION:
                return game.getResource().getNbMission();
            default:
                throw new IllegalTypeException("Wrong ResourceType.");
        }
    }
}