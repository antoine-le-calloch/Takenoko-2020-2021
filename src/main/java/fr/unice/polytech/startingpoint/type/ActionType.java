package fr.unice.polytech.startingpoint.type;

import fr.unice.polytech.startingpoint.exception.IllegalTypeException;

public enum ActionType {
    DRAW_CANAL {
        @Override
        public String toString() {
            return "DrawCanal";
        }
    }
    ,
    DRAW_MISSION {
        @Override
        public String toString() {
            return "DrawMission";
        }
    }
    ,
    DRAW_PARCELS {
        @Override
        public String toString() {
            return "DrawParcels";
        }
    }
    ,
    SELECT_PARCEL {
        @Override
        public String toString() {
            return "SelectParcel";
        }
    }
    ,
    PLACE_PARCEL {
        @Override
        public String toString() {
            return "PlaceParcel";
        }
    }
    ,
    PLACE_CANAL {
        @Override
        public String toString() {
            return "PlaceCanal";
        }
    }
    ,
    WEATHER{
        @Override
        public String toString() {
            return "Utilise la météo";
        }
    },
    MOVE_PEASANT {
        @Override
        public String toString() {
            return "MovePeasant";
        }
    }
    ,
    MOVE_PANDA {
        @Override
        public String toString() {
            return "MovePanda";
        }
    };

    public static ActionType get(CharacterType characterType){
        switch (characterType){
            case PANDA:
                return MOVE_PANDA;
            case PEASANT:
                return MOVE_PEASANT;
            default:
                throw new IllegalTypeException("Wrong CharacterType to move.");
        }
    }
}
