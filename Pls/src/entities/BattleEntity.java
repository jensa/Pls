package entities;
import jenhenna.pls.GridArea;

import Utils.Coord;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public interface BattleEntity {
	/**
	 * 
	 * @return true if the entity can be selected, false otherwise
	 */
	public boolean selected ();
	
	/**
	 * Signal to this entity that its last performed action 
	 * affected the specified targets.
	 * @param entities the entities affected by the last action performed by this entity
	 * @return
	 */
	public boolean actionPerformedOn (Array<BattleEntity> entities);
	
	/**
	 * Get the area of effect of this entity's last performed action
	 * @return the area of effect, or null if not applicable
	 */
	public GridArea getEffectArea ();
	
	/**
	 * 
	 * @return this entity's sprite, with surface coordinates set
	 */
	public Sprite getSprite ();
	
	/**
	 * 
	 * @return the ID of this entity
	 */
	public int getID ();
	
	/**
	 * Set this entity's position, in surface coords
	 * @param c
	 */
	public void setPosition (Coord c);
}
