package fr.unice.polytech.startingpoint.Type;

public enum ColorType {
    RED{
        @Override
        public String toString() {
            return "Red";
        }
    }
    ,
    BLUE{
        @Override
        public String toString() {
            return "Blue";
        }
    }
    ,
    NO_COLOR{
        @Override
        public String toString() {
            return "No_Color";
        }
    }

}
