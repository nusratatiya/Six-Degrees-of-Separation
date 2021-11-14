Nusrat Atiya
CS201 HW 3 
Read Me 


Person Driver


In the Search method, I added a resetPeeps() method at the top in order to reset the explored, predecessor and chainList variables using the setter and getter methods from the class file because otherwise, the queries after the initial query would be messed up due what was stored in the variables during the initial query. 
I created the ArrayList followingFriends in order to iterate the person Y object’s friends. In the algorithm to find the chain of acquaintances for each person, I had to do Collections.reverse(chainList), because it would print out the chain list from the order of the second person to the first person and it had to be the other way around.  
