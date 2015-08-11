package net.vitic.ec.cse.core.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by paul on 28/06/15.
 */
public class CoreActivator implements ManagedService, BundleActivator {

    private BundleContext context;
    private ServiceRegistration managedServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        this.context = bundleContext;
        String pid = context.getBundle().getSymbolicName() + ".config";

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

    }
}
