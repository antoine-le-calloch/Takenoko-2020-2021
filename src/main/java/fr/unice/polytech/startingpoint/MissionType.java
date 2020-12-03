package fr.unice.polytech.startingpoint;

public enum MissionType {
    PARCEL{
        @Override
        public String toString() {
            return "Parcel";
        }
    }
    ,
    PANDA{
        @Override
        public String toString() {
            return "Panda";
        }
    }
    ,
    PEASANT{
        @Override
        public String toString() {
            return "Peasant";
        }
    }

}
