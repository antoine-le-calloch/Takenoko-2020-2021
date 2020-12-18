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
    Red {
        @Override
        public String toString() {
            return "Red";
        }
    }
    ,
    Blue {
        @Override
        public String toString() {
            return "Blue";
        }
    }
    ,
    NoColor {
        @Override
        public String toString() {
            return "No Color";
        }
    }
}
