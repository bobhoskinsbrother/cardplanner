### Welcome to CardPlanner ###

We've a simple aim with CardPlanner; a shared board that can be used in a big visible workspace, or in a terminal.  The idea is simple; you move a story card on your Plasma TV in the bay area, and the rest of the team in Shenzhen can see it move on their SmartBoard, and vice versa.


There is one principal in CardPlanner; a card __is__ a board (a bit like everything is a file in unix).  So if you want to make another board, add a card.

Operation Cleanup means I'm fixing a bunch of stuff I've left for a couple of years.

To build, go to the root of the cloned app and run ant:

```
cd cardplanner
ant -DfudgeIt=hellYeah -f master.xml
```

To run the server:

```
cd dist/
unzip CardPlannerServer.zip
cd CardPlannerServer/
chmod a+x start_cardplanner.sh
./start_cardplanner.sh
```

