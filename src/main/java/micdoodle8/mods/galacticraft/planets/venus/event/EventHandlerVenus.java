package micdoodle8.mods.galacticraft.planets.venus.event;

import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.util.DamageSourceGC;
import micdoodle8.mods.galacticraft.planets.venus.VenusItems;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

public class EventHandlerVenus
{
    @SubscribeEvent
    public void onEntityTick(TickEvent.WorldTickEvent event)
    {
        if (event.side == Side.SERVER)
        {
            for (Entity e : new ArrayList<>(event.world.loadedEntityList))
            {
                if (e instanceof EntityLivingBase)
                {
                    EntityLivingBase entityLivingBase = (EntityLivingBase) e;
                    if (event.world.isMaterialInBB(entityLivingBase.getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), VenusModule.acidMaterial))
                    {
                        if (entityLivingBase.ticksExisted % 20 == 0)
                        {
                            entityLivingBase.attackEntityFrom(DamageSourceGC.acid, 3.0F);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onThermalArmorEvent(GCPlayerHandler.ThermalArmorEvent event)
    {
        if (event.armorStack == null)
        {
            event.setArmorAddResult(GCPlayerHandler.ThermalArmorEvent.ArmorAddResult.REMOVE);
            return;
        }

        if (event.armorStack.getItem() == VenusItems.thermalPaddingTier2 && event.armorStack.getItemDamage() == event.armorIndex)
        {
            event.setArmorAddResult(GCPlayerHandler.ThermalArmorEvent.ArmorAddResult.ADD);
            return;
        }

        event.setArmorAddResult(GCPlayerHandler.ThermalArmorEvent.ArmorAddResult.NOTHING);
    }
}
