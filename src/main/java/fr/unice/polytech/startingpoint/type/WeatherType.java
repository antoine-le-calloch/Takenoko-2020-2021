package fr.unice.polytech.startingpoint.type;

public enum WeatherType {
    SUN{
        @Override
        public String toString(){return "sun";}
    }
    ,
    WIND{
        @Override
        public String toString(){return "wind";}
    }
}