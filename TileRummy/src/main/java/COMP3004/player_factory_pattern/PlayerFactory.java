package COMP3004.player_factory_pattern;

import COMP3004.models.Player;

/*
* Strategy	1:
- This AI	player	p1 plays	its	initial	30	(or	more)	points	as	soon	as	it	can.
- Each	turn thereafter,	this AI	player	plays	all the	tiles it	can.
Strategy	2:
- This AI	player	p2 plays	its	initial	30	points only	if	another	player	has	already
played	tiles	on	the	table.
- If	it	can	play	all	its	tiles	(possibly	using	some	tiles	already	on	the	table),	it
does.
- Otherwise,	each	turn,	this AI	player	plays only	the	tiles	of	its	hand	that
require	using	tiles	on	the	table	to	make	melds.	That	is,	if	it	can't	win	this	turn,
this AI	player	keeps	in	its	hand	all	melds	that	do	NOT	require	the	use	of	tiles
already	on	the	table.
Strategy	3:
- This AI	player	p3 plays	its	initial	30	(or	more)	points	as	soon	as	it	can.
- If	it	can	play	all	its	tiles	(possibly	using	what's	on	the	table),	it	does.
- Else,	each	turn
o If	no	other	player	has	3	fewer	tiles	than	p3,	then p3 plays only	the	tiles
of	its	hand	that	require	using	tiles	on	the	table	to	make	melds	(as	in
Strategy	2).
o Else p3 plays	all	the	tiles	it	can
* */
public class PlayerFactory extends AbstractPlayerFactory {
    @Override
    public Player FactoryMethod(int type) {
        switch (type) {
            case 1: return new PlayerFirstAI();
            case 2: return new PlayerSecondAI();
            case 3: return new PlayerThirdAI();
            case 4: return new PlayerFourthAI();
            default: return null;
        }
    }
}
