=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: gidia
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2d array - implemented as int[][] for each player's board with
  ships represented as a number from 1-5 and spots without ships have 0.
  easy to keep track of ships and moves each player has made and to make
  graphic respond accordingly.

  2. file i/o - attempted to do a save and upload feature where the player
  could save the ships' locations of both players (but not the moves done until that point)
  to a csv file and then the upload should read those values back into the
  2d array boards for each player. saving the games to a csv file (using a bufferWriter) was 
  successful, but uploading the games from that csv file back to the 2d arrays was not.

  3. testable components - tests some basic methods of the game such as reset board and
  made sure one player could actually win using static boards where all ships were
  horizontal in first 2 rows or all vertical in first 2 columns to avoid random
  generation

  4. advanced logic - method that randomly generates valid battleship boards each time
  it is called

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
     Boat - object that holds simple info such as initial length (value 1-5), length
     left (because if it gets hit length decreases by one until it is completely sunk
     so length goes to zero) and whether it is oriented horizontally or vertically
     (which is utilized in the random generation of boards method)
     Player - houses all of the individual methods to be called for battleship
     (including reset, attack, randomly generate boards, etc.) and houses
     private fields of the 2d array board of where the ships were initially placed and
     what moves have already been called.
     Battleship - houses the 2 players as private fields so that can keep track of the
     progress on each to win the game. mainly middleman to delegate which player's
     methods will be called each time
     RunBattleship - implements runnable and makes buttons with appropriate methods called
     Game - makes the JPanel whose most important part is the repaint function

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
     The many classes were hard to keep track of and I found myself getting lost in
     my own logic. Also fileReader was a killer and I don't think I implemented it correctly.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
     Overall, there is a good separation of functionality. I would refactor
     Battleship and Player because I think firstly some methods are overly verbose
     or unnecessarily complicated and then they call the same methods just one delegates
     to the other and I feel that may be inefficient.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
     N/A for better or worse this all came from my brain :/
