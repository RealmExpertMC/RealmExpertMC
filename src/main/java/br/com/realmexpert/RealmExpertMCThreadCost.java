package br.com.realmexpert;

import br.com.realmexpert.util.i18n.Message;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RealmExpertMCThreadCost {
    static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    {
        threadMXBean.setThreadCpuTimeEnabled(true);
    }

    public static void dumpThreadCpuTime() {
        List<RealmExpertMCThreadCost.ThreadCpuTime> list = new ArrayList<>();
        long[] ids = threadMXBean.getAllThreadIds();
        for (long id : ids) {
            RealmExpertMCThreadCost.ThreadCpuTime item = new RealmExpertMCThreadCost.ThreadCpuTime();
            item.cpuTime = threadMXBean.getThreadCpuTime(id) / 1000000;
            item.userTime = threadMXBean.getThreadUserTime(id) / 1000000;
            item.name = threadMXBean.getThreadInfo(id).getThreadName();
            item.id = id;
            list.add(item);
        }
        list.sort(Comparator.comparingLong(i -> i.id));
        RealmExpertMC.LOGGER.info(Message.getString("RealmExpertMC.dump.1"));
        for (RealmExpertMCThreadCost.ThreadCpuTime threadCpuTime : list) {
            RealmExpertMC.LOGGER.info(String.format("%s %s %s %s", threadCpuTime.id, threadCpuTime.name, threadCpuTime.cpuTime, threadCpuTime.userTime));
        }
    }

    public static class ThreadCpuTime {
        private long id, cpuTime, userTime;
        private String name;
    }
}
