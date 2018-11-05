# COMP3004 - Scrummy

Description:

  A project for COMP3004 at Carleton university designed to showcase an understanding of TDD and Agile development in a group environment.

***

Status:
  
   This project has met the requirements for iteration 1 of the submission.

   Currently Supports:

   - Unit tests for all features using jUnit.
   - Dependency management using Maven.
   - Tiles with varying "Colours" and "Values".
   - Decks of Tiles that can be generated and shuffled.
   - Players which can have hands of tiles and draw from the deck.
   - AI players utilizing varying strategies:
      - Strategy 1: Play everything it can
      - Strategy 2: Play everyhing at once, or only modify the table
      - Strategy 3: Play as Stratgey 2, unless someone has 3 fewer cards, then play as Strategy 1
      - Strategy 4: Play as strategy 1, but hold onto melds with a high chance of getting related tiles
   - Textual user interface, allowing for user input
   - All rules (excluding jokers) of Tile Rummy implemented, including splitting

Roadmap:

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
  - [ ] Iteration 2...

Design Documentation:

   - [Draw.io UML Diagram](https://drive.google.com/file/d/1Bs36zHr1ql-CJrYGhb1J1I1Hmx0dcjDD/view?usp=sharing)
   - [Reports](https://github.com/l3rittny/Scrummy/tree/master/Documentation)
   - [Iteration1 Grid](https://docs.google.com/spreadsheets/d/1KXhiNsOeUnJxom1icK8RAqwpGoW4P9rRsnrB9mZolRU/edit#gid=616273153)

***

Requirements:

   - The use of this game presently requires the user to have installed jUnit and Maven in order to perform unit testing.
   - No installation is required in order to operate the application.
   - ensure when compiling with maven and trying execution that your JAVA_HOME environment variable is set
   - Currently developed for use with the JRE v.1.8.x

Usage:

   Unit tests can be run through maven by issuing the following command:

      mvn -B verify

   The application can be run by executing

      mvn exec:java

***

Collaborators:

   The list of collaborators for this group assignment are as follows:

   - Aidan Crowther,    100980915
   - David N. Zilio,    100997259
   - Ellis Glennie,     101036478
   - Brittny Lapierre,  100922938
