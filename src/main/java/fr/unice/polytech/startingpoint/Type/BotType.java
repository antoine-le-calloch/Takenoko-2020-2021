package fr.unice.polytech.startingpoint.Type;

/**
 * Enumération contenant les types de robots disponibles
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public enum BotType {
    RANDOM{
        @Override
        public String toString() {
            return "Random";
        }
    }
    ,
    INTELLIGENT{
        @Override
        public String toString() {
            return "Intelligent";
        }
    }
}
