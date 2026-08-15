# Maze
Kreiert ein Maze durch randomisierten DFS. Löst das Maze durch DFS. Zusätzliche Klasse für BFS ist enthalten, aber ungenutzt. Visualisierung enthalten, aber nicht von mir geschrieben.

Visualisierung Beispiel:


`Maze m = new Maze(sideLength,(int) (sideLength*sideLength/4));`

`List<Integer> path = m.findWay(0, sideLength*sideLength-1);`

`// die Visualisierung`

`GridGraph namenotimportant = new GridGraph(m.M(),path);`


Dies wurde im SoSe2026 im Modul Algorithmen und Datenstrukturen erstellt.
