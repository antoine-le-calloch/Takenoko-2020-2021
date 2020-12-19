package fr.unice.polytech.startingpoint.type;

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

    public static ActionType get(CharacterType characterType) throws IllegalAccessException {
        switch (characterType){
            case PANDA:
                return MOVE_PANDA;
            case PEASANT:
                return MOVE_PEASANT;
            default:
                throw new IllegalAccessException("Wrong CharacterType to move.");
        }
    }
}
