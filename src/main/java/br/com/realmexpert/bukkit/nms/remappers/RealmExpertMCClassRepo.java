package br.com.realmexpert.bukkit.nms.remappers;

import br.com.realmexpert.bukkit.nms.ClassLoaderContext;
import java.io.IOException;
import java.io.InputStream;
import net.md_5.specialsource.repo.CachingRepo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author pyz
 * @date 2019/7/4 1:21 AM
 */
public class RealmExpertMCClassRepo extends CachingRepo {
    private static final RealmExpertMCClassRepo INSTANCE = new RealmExpertMCClassRepo();

    public static RealmExpertMCClassRepo getInstance() {
        return INSTANCE;
    }

    @Override
    protected ClassNode findClass0(String internalName) {
        InputStream in = getClassLoder().getResourceAsStream(internalName + ".class");
        ClassNode classNode = new ClassNode();
        try {
            ClassReader reader = new ClassReader(in);
            reader.accept(classNode, 0);
        } catch (IOException e) {
            return null;
        }
        return classNode;
    }

    protected ClassLoader getClassLoder() {
        ClassLoader cl = ClassLoaderContext.peek();
        if (cl == null) {
            cl = Thread.currentThread().getContextClassLoader();
        }
        if (cl == null) {
            cl = this.getClass().getClassLoader();
        }
        return cl;
    }
}
