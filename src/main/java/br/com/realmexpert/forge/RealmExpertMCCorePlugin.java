package br.com.realmexpert.forge;

import br.com.realmexpert.bukkit.pluginfix.MyPetTransformer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class RealmExpertMCCorePlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return !FMLLaunchHandler.isDeobfuscatedEnvironment() ? new String[]{
                SendPacketTransformer.class.getCanonicalName(),
                MyPetTransformer.class.getCanonicalName()
        } : null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
