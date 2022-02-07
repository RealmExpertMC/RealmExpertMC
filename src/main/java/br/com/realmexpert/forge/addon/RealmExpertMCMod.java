package br.com.realmexpert.forge.addon;

import com.google.common.eventbus.EventBus;
import br.com.realmexpert.RealmExpertMC;
import br.com.realmexpert.forge.CustomMod;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;

public class RealmExpertMCMod extends DummyModContainer implements CustomMod {

    public RealmExpertMCMod(InputStream inputStream) {
        super(MetadataCollection.from(inputStream, "RealmExpertMC").getMetadataForId("RealmExpertMC", null));
        is.add(modinfo());
    }

    public static InputStream modinfo() {
        String info = "[\n" +
                "{\n" +
                "  \"modid\": \"RealmExpertMC\",\n" +
                "  \"name\": \"RealmExpertMC\",\n" +
                "  \"description\": \"RealmExpertMC built-in mark.\",\n" +
                "  \"version\": \"" + RealmExpertMC.getVersion() + "\",\n" +
                "  \"mcversion\": \"1.12.2\",\n" +
                "  \"logoFile\": \"/RealmExpertMC_logo.png\",\n" +
                "  \"url\": \"http://RealmExpertMC.com/\",\n" +
                "  \"updateUrl\": \"\",\n" +
                "  \"authors\": [\"Mgazul\", \"CraftDream\", \"azbh111\", \"lliioollcn\", \"terrainwax\", \"lvyitian\", \"ChenHauShen\", \"Technetium\", \"Shawiiz_z\", \"Others\"],\n" +
                "  \"credits\": \"Made by RealmExpertMC\",\n" +
                "  \"parent\": \"\",\n" +
                "  \"screenshots\": [],\n" +
                "  \"dependencies\": []\n" +
                "}\n" +
                "]";
        return new ByteArrayInputStream(info.getBytes());
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }

    @Override
    public Disableable canBeDisabled() {
        return Disableable.YES;
    }

    @Override
    public File jarFile() {
        return new File("RealmExpertMC.jar");
    }
}
