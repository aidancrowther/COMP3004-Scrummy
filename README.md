# COMP3004 - Scrummy

## Introduction

  This is a project for COMP3004 at Carleton university designed to showcase an understanding of TDD and Agile development in a group environment.

### Collaborators

- Aidan Crowther,    100980915
- David N. Zilio,    100997259
- Ellis Glennie,     101036478
- Brittny Lapierre,  100922938

### Design Documentation

- [Draw.io UML Diagram](https://drive.google.com/file/d/1Bs36zHr1ql-CJrYGhb1J1I1Hmx0dcjDD/view?usp=sharing)
- [Reports](https://github.com/l3rittny/Scrummy/tree/master/Documentation)
- [Iteration1 Grid](https://docs.google.com/spreadsheets/d/1KXhiNsOeUnJxom1icK8RAqwpGoW4P9rRsnrB9mZolRU/edit#gid=616273153)

***

## Minimum Usage Requirements

- The use of this game presently requires the user to have installed jUnit and Maven in order to perform unit testing.
- No installation is required in order to operate the application.
- ensure when compiling with maven and trying execution that your JAVA_HOME environment variable is set
- Currently developed for use with the JRE v.1.8.x

Usage:

  Unit tests and build can be run through maven by issuing the following command:

    mvn verify

  Compilation may be run independently with

    mvn compile

  The application can be run by executing

    mvn exec:java

***

Roadmap of Project Requirements:

- [x] Iteration 1
  - [X] Base game elements (Tiles, deck, ...) implemented
  - [X] AI strategy 1
  - [X] AI strategy 2
  - [X] AI strategy 3
  - [X] AI strategy 4
  - [X] Text input
  - [X] File input
  - [X] Verify game state is legal
  - [X] Handle user input correctly
  - [X] Functioning game
- [ ] Iteration 2
  - [ ] LEVEL 1:
    - [x] must have a GUI interface (pattern-based, preferably MVC)
    - [x] must support ALL of iteration 1's functionality EXCEPT strategy 4
    - [x]  ability to have 2 to 4 players
    - [x] ability to have any player be a human or an AI (to which one of the 3 strategies of Iteration 1 can be assigned)
    - [x] ability to determine who starts as per rules
    - [x] highlight of most recently played tiles on the board
    - [ ] jokers  
    - [ ] minimal game rigging to set initial hands
  - [ ] LEVEL 2: for a grade between 50 and 65, must support:
    - [x] optional timer of 2 minutes for human playing
    - [x] rules for dealing with the case where a human plays and leaves the board in an invalid state after declaring the end of his/her turn.
    - [ ] This includes the use of the Memento pattern to return to the state of game (board and current player's hand) before the invalid turn.
  - [ ] LEVEL 3: for a grade between 66 and 84, must support:
    - [x] complex board reuse: Do include all the situations you got working in your video
      - **I will provide some test cases**
    - [ ]strategy 4: explain what it does and how it does it in your video
    - [x]real-time suggestions for human players wrt how to play their tiles and why (which goes hand in hand with strategy 4): show them in your video
  - [ ] LEVEL 4: for a grade of 85 and 89:
    - [ ] GUI support for game rigging:
      - [ ] ability to set load/set initial hands via GUI
      - [ ] ability to set the tile drawn by any player via the GUI
  - [ ] LEVEL 5: for a grade of 90 or more:
    - [ ] use of one or more Go4 patterns for the generation (as opposed to the mere loading) of rigged initial hands and in-game drawn tiles.
      - This is an open-ended requirement. The goal is to have an approach to generating specific game scenarios to exercise all rules. \
      Factory and Decorator may be the most relevant patterns.\
      The initial idea I have is that to address a game that only involves AI players (whose behavior is predictable) and set up their initial hands and the tiles they draw so that they can exercise specific functionality on the second turn (eg board tile reuse that involves 3 or more melds).\
      for a 20 mark bonus for each of the following 2 bullets: (IF and ONLY IF you have at least LEVEL 1 functionality correctly working AND TESTED)
    - [ ] synchronous (turn-based) network playing using the Reactor pattern
    - [ ] asynchronous play via the Proactor pattern: no turns, players share the board but play asynchronously!!
      - To get marks wrt networking, the onus is on you to demonstrate in your video proper testing (across SEVERAL machines) of what you implement.
