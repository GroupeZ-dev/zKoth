package fr.maxlego08.koth.hologram;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothHologram;
import fr.maxlego08.koth.api.utils.HologramConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FancyHologram implements KothHologram {
    private final Map<Koth, Hologram> holograms = new HashMap<>();

    @Override
    public void start(Koth koth) {
        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable()) return;

        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        manager.getHologram("ZKOTH-" + koth.getFileName()).ifPresent(manager::removeHologram);

        var data = new TextHologramData("ZKOTH-" + koth.getFileName(), config.getLocation());
        updateLine(koth, data);
        var hologram = manager.create(data);
        manager.addHologram(hologram);
        this.holograms.put(koth, hologram);
    }

    @Override
    public void end(Koth koth) {
        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable()) return;

        var manager = FancyHologramsPlugin.get().getHologramManager();
        if (this.holograms.containsKey(koth)) {
            Hologram hologram = this.holograms.get(koth);
            manager.removeHologram(hologram);
        }
        this.holograms.remove(koth);
    }

    @Override
    public void update(Koth koth) {

        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable() || !this.holograms.containsKey(koth)) return;

        Hologram hologram = this.holograms.get(koth);
        updateLine(koth, hologram.getData());
        hologram.forceUpdate();
    }

    private void updateLine(Koth koth, HologramData hologram) {
        HologramConfig config = koth.getHologramConfig();
        if (!config.isEnable()) return;

        List<String> lines = config.getLines();
        if (hologram instanceof TextHologramData data) {
            data.setText(lines.stream().map(koth::replaceMessage).collect(Collectors.toList()));
        }
    }

    @Override
    public void onDisable() {
        var manager = FancyHologramsPlugin.get().getHologramManager();
        this.holograms.values().forEach(manager::removeHologram);
        this.holograms.clear();
    }
}
