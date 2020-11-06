package fr.unice.polytech.startingpoint;

class Bot {
    int num_bot;

    Bot(int num_bot) {
        this.num_bot = num_bot;
    }

    int play(Resource resource,Board board) {
        board.putParcel(resource.getParcel());
        return 2;
    }
}
