<h1>Projet semestre 5 : Takenoko</h1>
<div></div><em>Polytech-Nice Sophia SI3</em></div>
<div><em>Eric Naud, Loic Madern, Enzo Manuel, Antoine Le Calloch</em></div><br>

Takenoko is a turn-based board game. It is played by 2 to 4 players for games of around 45 minutes.
The theme of the game is to take care of a panda, a gift from the emperor of China to that of Japan, while taking care of the imperial bamboo grove, maintained by a gardener.
Players cultivate plots of land, irrigate them, and grow one of three varieties of bamboo (green, yellow or pink) through this gardener.

<h3>Project setup</h4>

	mvn clean package

	mvn exec:java 

<br>
<h3>Program behavior:</h3>

We've developed a game that lets you play against artificial intelligences or have them play against each other. There are several different bots with different playing techniques.
Game simulation is done by instantiating a Game object which has a list of bots as an argument. The number of players can vary from 1 to 4.

The standard output displays the win, lose and tie percentage as well as the average score for each bot, for a total of 1000 games.
You can also activate the display of game details each turn.

Two parts launch automatically. The first part is composed of a Parcel Bot, a Panda Bot, an Intelligent Bot and a Random Bot
The second part is composed of two Intelligent Bots. These parts do not display game details.

The tests cover 91% of the lines of code.




















