package fr.maxlego08.koth.hologram;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothHologram;
import fr.maxlego08.koth.api.utils.HologramConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DecentHologram implements KothHologram {

    private final Map<Koth, Hologram> holograms = new HashMap<Koth, Hologram>();

    @Override
    public void start(Koth koth) {

        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable()) return;

        DHAPI.removeHologram("ZKOTH-" + koth.getFileName());

        Hologram hologram = DHAPI.createHologram("ZKOTH-" + koth.getFileName(), config.getLocation());
        this.holograms.put(koth, hologram);
        updateLine(koth, hologram);
    }

    @Override
    public void end(Koth koth) {

        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable()) return;

        if (this.holograms.containsKey(koth)) {
            Hologram hologram = this.holograms.get(koth);
            hologram.destroy();
        }
        this.holograms.remove(koth);
    }

    @Override
    public void update(Koth koth) {

        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable() || !this.holograms.containsKey(koth)) return;

        Hologram hologram = this.holograms.get(koth);
        updateLine(koth, hologram);
    }

    private void updateLine(Koth koth, Hologram hologram) {
        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable()) return;

        List<String> lines = config.getLines();
        DHAPI.setHologramLines(hologram, lines.stream().map(koth::replaceMessage).collect(Collectors.toList()));
    }

    @Override
    public void onDisable() {
        this.holograms.values().forEach(Hologram::destroy);
        this.holograms.clear();
    }

}
