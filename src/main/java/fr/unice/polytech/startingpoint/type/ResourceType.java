package fr.unice.polytech.startingpoint.type;

public enum ResourceType {
    PARCEL_MISSION {
        @Override
        public String toString() {
            return "ParcelMission";
        }
    }
    ,
    PANDA_MISSION {
        @Override
        public String toString() {
            return "PandaMission";
        }
    }
    ,
    PEASANT_MISSION {
        @Override
        public String toString() {
            return "PeasantMission";
        }
    }
    ,
    ALL_MISSION {
        @Override
        public String toString() {
            return "AllMission";
        }
    }
    ,
    CANAL {
        @Override
        public String toString() {
            return "Canal";
        }
    }
    ,
    PARCEL {
        @Override
        public String toString() {
            return "Parcel";
        }
    }
    ,
    ALLIMPROVEMENT{
        @Override
        public String toString() {
            return "Improvement";
        }
    },
    WATHERSHEDMPROVEMENT{
        @Override
        public String toString() {
            return "Wathershed Improvement";
        }
    },
    ENCLOSUREIMPROVEMENT{
        @Override
        public String toString() {
            return "Enclosure Improvement";
        }
    },
    FERTIZILERIMPROVEMENT{
        @Override
        public String toString() {
            return "Fertiziler Improvement";
        }
    }
}
