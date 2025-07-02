package fr.maxlego08.koth;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;

public class Selection {

    private Location rightLocation;
    private Location leftLocation;
    private LivingEntity rightEntity;
    private LivingEntity leftEntity;

    public Location getRightLocation() {
        return this.rightLocation;
    }

    /**
     * @param rightLocation the rightLocation to set
     */
    private void setRightLocation(Location rightLocation) {
        this.rightLocation = rightLocation;
    }

    public Location getLeftLocation() {
        return this.leftLocation;
    }

    /**
     * @param leftLocation the leftLocation to set
     */
    private void setLeftLocation(Location leftLocation) {
        this.leftLocation = leftLocation;
    }

    public void action(Action action, Location location, LivingEntity livingEntity) {
        switch (action) {
            case LEFT_CLICK_BLOCK:
                this.setLeftLocation(location);
                if (this.leftEntity != null && this.leftEntity.isValid()) {
                    this.leftEntity.remove();
                }
                this.leftEntity = livingEntity;
                break;
            case RIGHT_CLICK_BLOCK:
                this.setRightLocation(location);
                if (this.rightEntity != null && this.rightEntity.isValid()) {
                    this.rightEntity.remove();
                }
                this.rightEntity = livingEntity;
                break;
            default:
            case LEFT_CLICK_AIR:
            case PHYSICAL:
            case RIGHT_CLICK_AIR:
                break;
        }
    }


    public boolean isValid() {
        return this.leftLocation != null && this.rightLocation != null;
    }

    public boolean isCorrect() {
        return isValid() && Math.abs(this.leftLocation.getY() - this.rightLocation.getY()) > 1;
    }

    public LivingEntity getRightEntity() {
        return this.rightEntity;
    }

    public LivingEntity getLeftEntity() {
        return this.leftEntity;
    }

    public void clear() {
        if (this.rightEntity != null && this.rightEntity.isValid()) {
            this.rightEntity.remove();
        }
        if (this.leftEntity != null && this.leftEntity.isValid()) {
            this.leftEntity.remove();
        }
    }

}
