package fr.unice.polytech.startingpoint;

public enum BotName {
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
