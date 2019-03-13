# fsaeSTAR
Code written for Purdue Formula SAE's CFD programme

These macros are meant to be used with batch operations. There are sysenvs the macros look for. I attempted to encapsulate every lookup within an if or a try-catch and have the macros still work or prompt for user input if something's missing, but more often than not things will break if you try using some of these with interactive sessions. Anyway; a list of all the envs the macros may be looking for. You WILL need to add the location of the macros to STAR's class path. The shell scripts automatically do that. I don't believe the batch scripts do. It's the same argument for both systems though I believe.


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


So the 2019 macros I wrote are fucking terrible and I want to rewrite them. Well, some of them at least.

Hopefully this time I'll comment them, they'll be quicker, and easier to maintain. The 2019 postprocessing macros were....really bad. The others were okay, but still could be rewritten.

I also want to write something that can also automate writing the batch files themsevles. Just read vehicle parameters/import CAD files based on a spreadsheet entry or something. And turn that into a batch file that can be executed straight from a terminal without having to fiddle with anything whatsoever with STAR. Maybe with a GUI? Could be a fun project. For now command line arguments are more than sufficient.

Maybe this will be useful for 2020. Maybe not.

There's a good chance these are all going to be broken while I work on them.

Another note. Rather than supporting a million different macros, each corresponding to a specific permutation or combination of macros and maintaining backwards compataibility, I only intend on supporting a small set of macros. Make your own combinations and macro queues if you need them.

If you want to modify these. I'd suggest using intelliJ. That's what these were written in. Except for the GUI projects, those were VS for the C#/.NET project, and pyCharm for the Python project. I don't know which one I'll actually end up writing.
