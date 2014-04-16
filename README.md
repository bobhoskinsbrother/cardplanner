### Welcome to CardPlanner ###

We've a simple aim with CardPlanner; a shared board that can be used in a big visible workspace, or in a terminal.  The idea is simple; you move a story card on your Plasma TV in the bay area, and the rest of the team in Shenzhen can see it move on their SmartBoard, and vice versa.


There is one principal in CardPlanner; a card __is__ a board (a bit like everything is a file in unix).  So if you want to make another board, add a card.

Operation Cleanup means I'm fixing a bunch of stuff I've left for a couple of years.

To build, go to the root of the cloned app and run ant:

```
cd cardplanner
ant -DfudgeIt=hellYeah -f master.xml
```

__"Why, -DfudgeIt?"__

Yes, I broke the functional tests, and yes that's the first thing I'll fix


__"Crap, my build failed"__

The most likely cause of this is machine specific settings.  These are contained in the environments folder, as properties files.
Simply copy the ben.properties, and name it <hostname>.properties.
If you don't know your hostname, run the command "hostname" via the command line to find it out.


To run the server after building:

```
cd dist/
unzip CardPlannerServer.zip
cd CardPlannerServer/
chmod a+x start_cardplanner.sh
./start_cardplanner.sh
```

