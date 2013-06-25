package Utils;

import java.util.Map;

import com.badlogic.gdx.utils.Array;

import entities.BattleUnit;

public class Test {
	
	public static Array<BattleUnit> getTestUnits (Map<String, BattleUnit> unitMap){
		Array<BattleUnit> units = new Array<BattleUnit> ();
		for (String s : unitMap.keySet ())
			units.add (unitMap.get (s));
		return units;
	}

}
