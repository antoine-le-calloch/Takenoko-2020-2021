package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>{@link ParcelBot} :</h1>
 *
 * <p>This class provides a bot specialized in {@link ParcelMission} missions.</p>
 *
 * <p>The programmer needs only to provide implementations for the {@link Bot#botPlay(WeatherType)} and {@link Bot#bestMissionTypeToDraw()} methods from the {@link Bot}.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Bot
 * @see PandaBot
 * @see ParcelBot
 * @see PeasantBot
 * @see RandomBot
 * @version 0.5
 */

public class ParcelBot extends Bot {
    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass.</p>
     *
     * @param gameInteraction
     *            <b>Game object.</b>
     */
    public ParcelBot(GameInteraction gameInteraction) {
        super(gameInteraction);
        botType = BotType.PARCEL_BOT;;
    }

    @Override
    public MissionType bestMissionTypeToDraw() {
        if (gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0)
            return MissionType.PARCEL;
        return chooseMissionTypeDrawable(MissionType.PANDA,MissionType.PEASANT,MissionType.PARCEL);
    }

    @Override
    public void stratRain() {
        for(PeasantMission peasantMission:gameInteraction.getInventoryPeasantMissions()) {
            ColorType peasantMissionColor = peasantMission.getColorType();

            List<Coordinate> parcelsIrrigatedSameColorAsMission = gameInteraction.
                    getPlacedCoordinatesByColor(peasantMissionColor).stream()
                    .filter(gameInteraction::isPlacedAndIrrigatedParcel)
                    .collect(Collectors.toList());
            if (!parcelsIrrigatedSameColorAsMission.isEmpty()) {
                gameInteraction.rainAction(parcelsIrrigatedSameColorAsMission.get(0));
                break;
            }
        }
    }
}