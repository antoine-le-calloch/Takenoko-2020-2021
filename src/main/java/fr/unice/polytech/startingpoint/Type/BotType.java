package fr.unice.polytech.startingpoint.Type;

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
