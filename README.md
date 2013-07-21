A java Sudoko solver, to see how it can be done without reading any doc.

Build with maven:
    mvn package

Then run it with the grid as a parameter

    java -jar target/sudoku-1.0-SNAPSHOT.jar "2 5 7   9 8  6   3  6  4  2 7   5       9       8   5 9  1  4  3   5  9 6   8 3 1"

It logs the steps then the resulting doc.

© Julien Kirch 2013 - Licensed under MIT license