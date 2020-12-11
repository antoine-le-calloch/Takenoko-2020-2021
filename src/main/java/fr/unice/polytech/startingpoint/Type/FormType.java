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
    TRIANGLE{
        @Override
        public String toString() {
            return "Triangle";
        }
    }
    ,
    LINE{
        @Override
        public String toString() {
            return "Line";
        }
    }
}
