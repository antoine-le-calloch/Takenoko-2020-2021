package fr.unice.polytech.startingpoint.type;

public enum ImprovementType {
    Enclosure {
        @Override
        public String toString() {
            return "Enclosure";
        }
    }
    ,
    Fertilizer {
        @Override
        public String toString() {
            return "Fertilizer";
        }
    }
    ,
    Watershed {
        @Override
        public String toString() {
            return "Watershed";
        }
    }
    ,
    Nothing {
        @Override
        public String toString() {
            return "Nothing";
        }
    }
}
