package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.IllegalTypeException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PlayerInteraction {
    private final Game game;

    PlayerInteraction(Game game){
        this.game = game;
    }

    public PlayerData getPlayerData() {
        return game.getPlayerData();
    }

    public void drawCanal() throws OutOfResourcesException, RulesViolationException {
        if (getPlayerData().add(ActionType.DRAW_CANAL)){
            getPlayerData().looseStamina();
            getPlayerData().addCanal(game.getResource().drawCanal());
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void drawMission(MissionType missionType) throws OutOfResourcesException, RulesViolationException {
        if (getPlayerData().add(ActionType.DRAW_MISSION)){
            getPlayerData().looseStamina();
            getPlayerData().addMission(game.getResource().drawMission(missionType));
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public List<ParcelInformation> drawParcels() throws OutOfResourcesException, RulesViolationException {
        if (getPlayerData().add(ActionType.DRAW_PARCELS)){
            getPlayerData().looseStamina();
            getPlayerData().saveParcels(game.getResource().drawParcels());
            List<ParcelInformation> parcelInformationList = new ArrayList<>();
            for(Parcel parcel : getPlayerData().getParcelsSaved()){
                parcelInformationList.add(parcel.getParcelInformation());
            }
            return parcelInformationList;
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void selectParcel(ParcelInformation parcelInformation) throws RulesViolationException {
        if (getPlayerData().add(ActionType.SELECT_PARCEL)){
            if (getPlayerData().contains(ActionType.DRAW_PARCELS)){
                for (Parcel parcel : getPlayerData().getParcelsSaved()){
                    if (parcel.getParcelInformation() == parcelInformation){
                        game.getResource().selectParcel(parcel);
                        getPlayerData().saveParcel(parcel);
                        return;
                    }
                }
                throw new RulesViolationException("Wrong Parcel asked.");
            }
            else
                throw new RulesViolationException("You haven’t drawn.");
        }
        else
            throw new RulesViolationException("Already used this method.");
    }

    public void placeParcel(Coordinate coordinate) throws BadCoordinateException, RulesViolationException {
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

    public void placeCanal(Coordinate coordinate1, Coordinate coordinate2) throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
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

    public void moveCharacter(CharacterType characterType, Coordinate coordinate) throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        if (getPlayerData().add(ActionType.get(characterType))) {
            if (game.getRules().isMovableCharacter(characterType, coordinate)) {
                getPlayerData().looseStamina();
                getPlayerData().addBamboo(game.getBoard().moveCharacter(characterType, coordinate));
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

    public int getStamina(){
        return game.getPlayerData().getStamina();
    }

    public Rules getRules(){
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

    /**
     * @return <b>The {@link Mission} list of the current bot.</b>
     */
    public List<Mission> getInventoryMissions() {
        return getPlayerData().getMissions();
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