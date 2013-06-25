package Hero;

import com.badlogic.gdx.utils.Array;

import entities.BattleUnit;

public class Hero {
	
	private Array<BattleUnit> battleUnits;
	
	/**
	 * Standard constructor. A hero must have at least one unit.
	 * @param units
	 */
	public Hero (Array<BattleUnit> units){
		battleUnits = units;
	}
	
	public Array<BattleUnit> getBattleUnits (){
		return battleUnits;
	}

}
