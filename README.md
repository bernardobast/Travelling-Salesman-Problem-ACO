# Travelling-Salesman-Problem-ACO
This project was developed in the context of Object-Oriented Programing where an Ant Colony Optimization (ACO) algorithm was developed to solve the Traveling Salesman Problem (TSP).  The TSP problem consists of finding the shortest and most effective path that goes through all the nodes in a graph. Once the program is finished, the shortest path is printed. 

#Ant Colony Optimization
The ant colony optimization algorithm (ACO) is a probabilistic algorithm inspired by the capacity of ants finding the most effective path to the closest food source. When reaching a food source and returning to the colony, ants deploy pheromones. The deployed pheromones will converge to a single path that proves to be the most effective. 

Thus, the ACO algorithm can be used to find good paths through graphs.  Each "ant" will explore the different paths in the provided graphs and deploy pheromones. These pheromones evaporate through time to avoid getting trapped in local solutions. 

#Application Extensibility
The components were also divided into several packages which include:
*ants - Includes all the components related to ants which will explore the graph. These ants are further grouped into a colony.
*graph - Includes all components that define the weighted graph with time to traverse between nodes and pheromone levels in the edges that can increase or evaporate. It also chooses the next node of the path based on the current node.
*simulation - Includes all components related to the process of running the algorithm. Includes the Main class.
Moreover, several interfaces were also implemented:
*graph:
**IAnt: Interface of the ant class
**IColony: Interface of the ant colony which stores all the ants.

#UML
The visual representation of all the software components is presented in the UML folder.

