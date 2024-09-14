package care.cuddliness.hex.statuscode;

import care.cuddliness.hex.HexCore;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class StatuscodeHandler {
    HashMap<String, String> statusCodes = new HashMap<>();
    @Getter
    private YamlDocument statusCodeFile;

    public StatuscodeHandler() {
        try {
            this.statusCodeFile = YamlDocument.create(new File(HexCore.getHexCore().getDataFolder(), "statuscodes.yml"),
                    Objects.requireNonNull(HexCore.getHexCore().getResource("statuscodes.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT,
                    UpdaterSettings.builder().build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadAll();
    }

    private void loadAll() {
        this.statusCodeFile.getKeys().forEach(o -> {
            statusCodes.put(o.toString(), statusCodeFile.getString(o + ".description"));
        });
    }

    public StatusCode getStatusCode(String input) {
        if (statusCodes.get(input) == null) {
            return new StatusCode("999", "Code not found");
        } else {
            return new StatusCode(input, statusCodes.get(input));
        }
    }
}
