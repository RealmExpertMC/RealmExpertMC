/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.fml.relauncher;

import br.com.realmexpert.console.log4j.Log4jCrashFix;
import org.apache.logging.log4j.LogManager;
import br.com.realmexpert.RealmExpertMC;
import br.com.realmexpert.util.i18n.Message;

import java.lang.reflect.Method;
import java.util.Objects;

public class ServerLaunchWrapper {

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new ServerLaunchWrapper().run(args);
    }

    private ServerLaunchWrapper()
    {

    }

    private void run(String[] args)
    {
        if (System.getProperty("log4j.configurationFile") == null)
        {
            // Set this early so we don't need to reconfigure later
            System.setProperty("log4j.configurationFile", "log4j2_RealmExpertMC.xml");
        }
        Class<?> launchwrapper = null;
        try {
            launchwrapper = Class.forName("net.minecraft.launchwrapper.Launch", true, RealmExpertMC.class.getClassLoader());
            Class.forName("org.objectweb.asm.Type", true, RealmExpertMC.class.getClassLoader());
            System.out.println(Message.getString("RealmExpertMC.start"));
            System.out.println(Message.getString("load.libraries"));
            RealmExpertMC.LOGGER = LogManager.getLogger("RealmExpertMC");
        } catch (Exception e) {
            System.out.println(Message.getString("RealmExpertMC.start.error.nothavelibrary"));
            System.out.println("   ");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            Method main = launchwrapper != null ? launchwrapper.getMethod("main", String[].class) : null;
            String[] allArgs = new String[args.length + 2];
            allArgs[0] = "--tweakClass";
            allArgs[1] = "net.minecraftforge.fml.common.launcher.FMLServerTweaker";
            System.arraycopy(args, 0, allArgs, 2, args.length);
            Objects.requireNonNull(main).invoke(null, (Object) allArgs);
        } catch (Exception e) {
            System.out.println(Message.getString("RealmExpertMC.start.error"));
            e.printStackTrace(System.err);
            new Log4jCrashFix(System.out).run();
            System.exit(1);
        }
    }

}