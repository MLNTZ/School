PCG_hmintz:
	This file contains an algorithm to generate a world map using the Poisson Disk algorithm.
	In order to see varied maps you can change the global variables at the top of the file:
		seed - the seed value for the random number generator
		gridScale - the relative size of each block in the grid to the window (small = more blocks)
		count - the number of new point generation attempts in each iteration of the algorithm (large = denser map (ususaly))
	As you change these variables you will see different maps form

	
	To see data structure representation:
		1. change grid scale to 64 (not required but it makes it more pretty)
		2. in draw() comment out line 32:  grid.drawGrid()
		3. in draw() uncomment line 34: #grid.represent()
		4. press the play button.
	you will see how each Block in the grid is represented using a string for the obstical, and holding the values of the blocks location on the screen

  	
To run:
	1. Extract Assignment_3_hmintz from the .zip file
	1. open the PCG_hmintz.pde file using processing IDE
	2. ensure the proper icons folder is located in the file system
	3. Change parameters to ur desire
	4. click the play button and see the map