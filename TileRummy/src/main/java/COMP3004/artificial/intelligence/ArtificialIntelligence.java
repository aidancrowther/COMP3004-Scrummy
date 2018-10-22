/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * This abstract class is a way to abstract out the Strategies of the AI such that it won't be janky in the controller
 */

package COMP3004.artificial.intelligence;

import COMP3004.controllers.GameInteractionInterface;
import COMP3004.oberver.pattern.TableObserver;

public abstract class ArtificialIntelligence extends TableObserver implements GameInteractionInterface
{
    
}