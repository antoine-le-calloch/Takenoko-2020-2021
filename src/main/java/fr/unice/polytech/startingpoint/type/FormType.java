package fr.unice.polytech.startingpoint.type;

/**
 * Enumération contenant les types de formes de parcelles disponibles
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


public enum FormType {
    Triangle {
        @Override
        public String toString() {
            return "Triangle";
        }
    }
    ,
    Line {
        @Override
        public String toString() {
            return "Line";
        }
    }
}
