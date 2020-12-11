package fr.unice.polytech.startingpoint.type;

public enum ResourceType {
    PARCEL_MISSION{
        @Override
        public String toString() {
            return "Parcel Mission";
        }
    }
    ,
    PANDA_MISSION{
        @Override
        public String toString() {
            return "Panda Mission";
        }
    }
    ,
    PEASANT_MISSION{
        @Override
        public String toString() {
            return "Peasant Mission";
        }
    }
    ,
    ALL_MISSION{
        @Override
        public String toString() {
            return "All Mission";
        }
    }
    ,
    CANAL{
        @Override
        public String toString() {
            return "Canal";
        }
    }
    ,
    PARCEL{
        @Override
        public String toString() {
            return "Parcel";
        }
    }
}
