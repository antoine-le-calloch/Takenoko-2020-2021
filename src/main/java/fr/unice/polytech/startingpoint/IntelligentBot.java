package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class IntelligentBot extends Bot{
    Resource resource;
    Board board;

    IntelligentBot(Resource resource, Board board) {
        super(resource, board);
        this.resource = resource;
        this.board = board;
    }

    @Override
    void Botplay(){
        if (!doDrawMission() && resource.getMission().size() > 0)
            drawMission();
        if (resource.getParcel().size() > 0)
            PutParcel();
    }

    //Si le bot n'a pas de mission => true
    boolean doDrawMission(){
        return inventoryMission.size() > 1;
    }

    //Pour chaque mission, cherche les cases disponible pour finir la forme
    void PutParcel() {
        for (Mission mission : inventoryMission) {
            if (coordinateMakingGoal(mission.getGoal()).size() > 0) {
                // on doit verifier que la liste n'est pas vide
                placeParcel(coordinateMakingGoal(mission.getGoal()));
                return;
            }
        }
        placeParcel(possibleCoordinatesParcel());
    }

    void testEachParcel(){
        int x = -3;
        int y = 3;
        int z = 0;
        int line = 0;
        int nbCasesColumn = 3;
        while (x < 4) {
            nbParcelAlreadyPlaced(x,y - line,z + line, inventoryMission.get(0).getGoal());
            System.out.print("||x : " + x);
            System.out.print(" y : " + (y - line));
            System.out.println(" z : " + (z + line));
            if(line >= nbCasesColumn) {
                System.out.println();
                if(x < 0) {
                    nbCasesColumn++;
                    z--;
                }else{
                    nbCasesColumn--;
                    y--;
                }
                line=0;
                x++;
            }
            else
                line++;
        }
    }

    //renvoie le nombre de parcel déjà posé sur la forme [form] qui a pour parcel haute [x,y,z]
    int nbParcelAlreadyPlaced(int x,int y,int z, String form){
        int nbParcel = 0;

        for (int i = 0; i < 3; i++) {
            if(x == 0 && y == 0 && z == 0)
                return 0;
            if(form.equals("line") && board.isplacedParcel(new Coordinate(x, y--, z++)))
                nbParcel++;

            else if(form.equals("triangle") && board.isplacedParcel(new Coordinate(x, y, z))) {
                x = x - 1 + (2 * i); //x-- pour la parcel 2, x++ pour la parcel 3
                y = y - i; //y pour la parcel 2, y-- pour la parcel 3
                z = z + 1 - i; //z++ pour la parcel 2, z pour la parcel 3
                nbParcel++;
            }
        }
        return nbParcel;
    }

    //creer une liste des coordonnée possible qui peuvent faire une forme
    ArrayList<Coordinate> coordinateMakingGoal(String goal /* + couleur*/) {
        ArrayList<Coordinate> possibleCoord = possibleCoordinatesParcel();
        ArrayList<Coordinate> coordMakingLine = new ArrayList<>();

        for (Coordinate coordinate : possibleCoord) {
                if (board.checkGoal(goal,coordinate, false))
                    coordMakingLine.add(coordinate);
            }
        return coordMakingLine;
    }

}
