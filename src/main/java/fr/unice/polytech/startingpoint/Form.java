package fr.unice.polytech.startingpoint;

public enum Form {
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
