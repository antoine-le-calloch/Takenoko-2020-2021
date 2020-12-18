package fr.unice.polytech.startingpoint.type;

public enum ResourceType {
    ParcelMission {
        @Override
        public String toString() {
            return "ParcelMission";
        }
    }
    ,
    PandaMission {
        @Override
        public String toString() {
            return "PandaMission";
        }
    }
    ,
    PeasantMission {
        @Override
        public String toString() {
            return "PeasantMission";
        }
    }
    ,
    AllMission {
        @Override
        public String toString() {
            return "AllMission";
        }
    }
    ,
    Canal {
        @Override
        public String toString() {
            return "Canal";
        }
    }
    ,
    Parcel {
        @Override
        public String toString() {
            return "Parcel";
        }
    }
}
