# fsaeSTAR
Code written for Purdue Formula SAE's CFD programme

<<<<<<< HEAD
These macros are meant to be used with batch operations. There are sysenvs the macros look for. I attempted to encapsulate every lookup within an if or a try-catch and have the macros still work or prompt for user input if something's missing, but more often than not things will break if you try using some of these with interactive sessions. Anyway; a list of all the envs the macros may be looking for. You WILL need to add the location of the macros to STAR's class path. The shell scripts automatically do that. I don't believe the batch scripts do. It's the same argument for both systems though I believe. 
=======
As of right now, the GUI projects are far from usable. The python folder is irrelevant for now. The C# folder has a functioning GUI but no backend. 

These macros are meant to be used with batch operations. There are sysenvs the macros look for. I attempted to encapsulate every lookup within an if or a try-catch and have the macros still work or prompt for user input if something's missing, but more often than not things will break if you try using some of these with interactive sessions. Anyway; a list of all the envs the macros may be looking for. You WILL need to add the location of the macros to STAR's class path. The shell scripts automatically do that. I don't believe the batch scripts do. It's the same argument for both systems though I believe.
>>>>>>> d71dbc6... Update README.md


frh - Sets front ride height. (rideHeight.java)

rrh - Sets rear ride height (rideHeight.java)

newName - Sets new file name (must end with .sim) (rideHeight.java)

rws - Enter 1 if you want to sweep through rear wing scenes. 0 if not. (exportScenes.java)

fws - Front wing scenes. See above

uts - Undertray scenes. See above

cs - Car scenes. See above

ps - Plot scenes. See above

sceneMultiplier - Multiplies scene dimension steps by the input. Use 1 to use default (very fine)

yaw - Yaw angle in degrees. Positive value rotates turns the vehicle to the left.

domainSet - "full" or "half". Sets the domain to be either full-car or half-car. 

freestream - set freestream velocity in m/s


Another note. Sometimes the macros will throw error messages. This is usually okay. It's just to let you know it was expecting something that it didn't find. For example, the rollaxis coordinate system isn't in every sim set up. Sim setups without a solution wont have a finite volume representation, and might throw something in the terminal. That's fine. Sometimes displayers aren't entirely set-up. That's fine too. As long as it doesn't completely shit the bed, it should be okay. If it does shit the bed, hopefully it's in a try-catch and something shows up in the terminal. I'm not entirely confident the code is going to catch every possible exception STAR/Java could possibly throw. 

Your parts ****NEED**** to follow a naming convention. Here's the relevant code (aero, non-aero, lift-generators need to start with one of those prefixes, wheel names need to match exactly):

aeroPrefixes.addAll(Arrays.asList("RW", "FW", "UT", "EC", "NS", "MOUNT", "SW", "FC"));
nonAeroPrefixes.addAll(Arrays.asList("CFD"));
wheelNames.addAll(Arrays.asList("Front Left", "Front Right", "Rear Left", "Rear Right"));
liftGeneratorPrefixes.addAll(Arrays.asList("RW", "FW", "UT", "SW", "FC"));
