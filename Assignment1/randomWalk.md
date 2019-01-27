Random Walk

*I* - Length of Step

*n* - Steps （in code it is m)

*d* - how far in the end(After n steps)



### [Assignment 1 (Random Walk)](https://northeastern.blackboard.com/webapps/assignment/uploadAssignment?content_id=_19506610_1&course_id=_2565127_1&group_id=&mode=view)

An important type of practical experiment is normally referred to as the "random walk" experiment.

Imagine a drunken man who, starting out leaning against a lamp post in the middle of an open space, takes a series of steps of the same length l. The direction of these steps is randomly chosen from North, South, East or West. After n steps, how far (*d*), generally speaking, is the man from the lamp post? Note that *d* is the Euclidean distance of the man from the lamp-post.

It turns out that there is a relationship between *d, l* and *n* which is typically applicable to many different types of stochastic (randomized) experiments. Your task is to implement the code for the experiment and, most importantly, to deduce the relationship.

Please pull from the class repo and work on *RandomWalk.java* and *RandomWalkTest.java* each of package *randomwalk* and each under the appropriate source directory. You may have to remove other java files from the classpath in order to allow the whole project to compile. Once you have all the unit tests running, you can do the experiment by running *RandomWalk* as a main program (provide the value of *n* as the first argument).

For this particular assignment, it is necessary but *not* sufficient to ensure that the unit tests all run. You must demonstrate via image files, graphs, whatever, what experiments you made in order to come up with the required expression. You will run the experiment for at least six values of n and will run each of these at least five times. That's to say, you will run the program *at least* 30 separate times.

Feel free to change the main program so that it will run all your experiments in one shot instead of 30 different runs.

Your submission should include:

1. Your conclusion about the relationship between d, n and l;
2. Your evidence to support that relationship;
3. Your code (*RandomWalk.java* plus anything else that you changed or created);
4. Evidence (screen shot) of the unit tests all passing.

Please note: for this assignment, you do *not* need to set up github and push your files, as described in the general instructions for submission (*Submitting Assignments*).