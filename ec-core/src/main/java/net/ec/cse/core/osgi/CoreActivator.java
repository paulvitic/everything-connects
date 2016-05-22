package net.ec.cse.core.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 */
public class CoreActivator implements ManagedService, BundleActivator {

    private BundleContext context;
    private String bundleSymbolicName;
    private LogService logService;
    private ServiceRegistration managedServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        this.context = bundleContext;
        this.bundleSymbolicName = context.getBundle().getSymbolicName();

        // create a tracker and track the log service
        ServiceTracker<LogService, LogService> logServiceTracker = new ServiceTracker<LogService, LogService>(context, LogService.class, null);
        logServiceTracker.open();
        // grab the service
        logService = logServiceTracker.getService();
        logServiceTracker.close();

        String pid = bundleSymbolicName + ".config";
        Dictionary<String, String> dictionary = new Hashtable<String, String>();
        dictionary.put(Constants.SERVICE_PID, pid);
        managedServiceRegistration = context.registerService(ManagedService.class.getName(), this, dictionary);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        managedServiceRegistration.unregister();
        this.context = null;
    }

    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
        if (properties!=null){
            Object somethingProp = properties.get(bundleSymbolicName + ".something");
            if(somethingProp !=null && logService != null) logService.log(LogService.LOG_INFO, somethingProp.toString());
        }
    }
}
