package fr.unice.polytech.startingpoint.Type;

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
