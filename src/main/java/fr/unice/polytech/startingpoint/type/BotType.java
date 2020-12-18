package fr.unice.polytech.startingpoint.type;

/**
 * Enumération contenant les types de robots disponibles
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public enum BotType {
    Random {
        @Override
        public String toString() {
            return "Random Bot";
        }
    }
    ,
    ParcelBot {
        @Override
        public String toString() {
            return "Parcel Bot";
        }
    }
    ,
    PandaBot {
        @Override
        public String toString() {
            return "Panda Bot";
        }
    }
    ,
    PeasantBot {
        @Override
        public String toString() {
            return "Peasant Bot";
        }
    }
}
