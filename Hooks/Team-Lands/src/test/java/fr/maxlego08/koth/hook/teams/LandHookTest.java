package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.api.KothPlugin;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.applicationframework.util.ULID;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.enums.LandType;
import me.angeschossen.lands.api.player.LandPlayer;
import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LandHookTest {

    private final UUID playerId = UUID.fromString("7e8eeb2c-ffce-44ae-8935-692898e84067");
    private KothPlugin plugin;
    private LandsIntegration lands;
    private OfflinePlayer player;
    private LandPlayer landPlayer;
    private LandHook hook;

    @BeforeEach
    void setUp() {
        plugin = mock(KothPlugin.class);
        lands = mock(LandsIntegration.class);
        player = mock(OfflinePlayer.class);
        landPlayer = mock(LandPlayer.class);
        when(player.getUniqueId()).thenReturn(playerId);
        when(lands.getLandPlayer(playerId)).thenReturn(landPlayer);
        hook = new LandHook(plugin, lands);
    }

    @Test
    void usesCurrentUlidForOwnedLand() {
        Land ownedLand = usableLand("01JZKOTHOWNEDLAND0000000000");
        when(landPlayer.getOwningLand()).thenReturn(ownedLand);

        assertEquals("01JZKOTHOWNEDLAND0000000000", hook.getTeamId(player));
    }

    @Test
    void choosesStableValidMembershipWhenPlayerOwnsNoLand() {
        Land later = usableLand("01JZKOTHZZZZZZZZZZZZZZZZZ");
        Land earlier = usableLand("01JZKOTHAAAAAAAAAAAAAAAAA");
        Land deleted = usableLand("01JZKOTHDELETED0000000000");
        when(deleted.exists()).thenReturn(false);
        doReturn(List.of(later, deleted, earlier)).when(landPlayer).getLands();

        assertEquals("01JZKOTHAAAAAAAAAAAAAAAAA", hook.getTeamId(player));
    }

    @Test
    void fallsBackToPlayerUuidWithoutUsableLand() {
        Land adminLand = usableLand("01JZKOTHADMIN000000000000");
        when(adminLand.getLandType()).thenReturn(LandType.ADMIN);
        doReturn(List.of(adminLand)).when(landPlayer).getLands();

        assertEquals(playerId.toString(), hook.getTeamId(player));
    }

    @Test
    void disbandUsesSameCurrentUlidIdentifier() {
        Land land = usableLand("01JZKOTHDISBAND0000000000");
        LandDeleteEvent event = mock(LandDeleteEvent.class);
        when(event.getLand()).thenReturn(land);

        hook.onDisband(event);

        verify(plugin).onTeamDisband("01JZKOTHDISBAND0000000000");
    }

    private Land usableLand(String id) {
        Land land = mock(Land.class);
        ULID ulid = mock(ULID.class);
        when(ulid.toString()).thenReturn(id);
        when(land.exists()).thenReturn(true);
        when(land.getLandType()).thenReturn(LandType.LAND);
        when(land.getULID()).thenReturn(ulid);
        return land;
    }
}
