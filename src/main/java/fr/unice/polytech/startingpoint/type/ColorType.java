package fr.unice.polytech.startingpoint.type;

/**
 * Enumération contenant les types de couleurs de parcelles disponibles
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public enum ColorType {
    RED {
        @Override
        public String toString() {
            return "Red";
        }
    }
    ,
    GREEN {
        @Override
        public String toString() {
            return "Green";
        }
    }
    ,
    YELLOW {
        @Override
        public String toString() {
            return "Yellow";
        }
    }
    ,
    NO_COLOR {
        @Override
        public String toString() {
            return "No Color";
        }
    }
}
