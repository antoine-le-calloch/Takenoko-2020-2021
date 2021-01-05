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
    ,
    RAIN{
        @Override
        public String toString(){return "wind";}
    },
    THUNDERSTORM{
        @Override
        public String toString(){return "thunderstorm";}
    },
    NO_WEATHER{
        @Override
        public String toString(){return "no weather";}
    }
}
