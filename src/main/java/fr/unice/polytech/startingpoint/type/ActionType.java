package fr.unice.polytech.startingpoint.type;

public enum ActionType {
    DrawCanal{
        @Override
        public String toString() {
            return "DrawCanal";
        }
    }
    ,
    DrawMission{
        @Override
        public String toString() {
            return "DrawMission";
        }
    }
    ,
    DrawParcels{
        @Override
        public String toString() {
            return "DrawParcels";
        }
    }
    ,
    SelectParcel{
        @Override
        public String toString() {
            return "SelectParcel";
        }
    }
    ,
    PlaceParcel{
        @Override
        public String toString() {
            return "PlaceParcel";
        }
    }
    ,
    PlaceCanal{
        @Override
        public String toString() {
            return "PlaceCanal";
        }
    }
    ,
    MovePeasant{
        @Override
        public String toString() {
            return "MovePeasant";
        }
    }
    ,
    MovePanda{
        @Override
        public String toString() {
            return "MovePanda";
        }
    };

    public static ActionType get(CharacterType characterType) throws IllegalAccessException {
        switch (characterType){
            case Panda:
                return MovePanda;
            case Peasant:
                return MovePeasant;
            default:
                throw new IllegalAccessException("Wrong CharacterType to move.");
        }
    }
}
