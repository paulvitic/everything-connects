package net.vitic.ec.cse.core.osgi;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ManagedService;

import javax.inject.Inject;
import java.util.Dictionary;
import java.util.Hashtable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * Created by paul on 09/08/15.
 */
@RunWith(PaxExam.class)
public class CoreActivatorTest extends TestCase {

    @Inject
    private ManagedService activator;

    @Inject
    private BundleContext bundleContext;

    @Configuration
    public Option[] config() {
        return options(
                mavenBundle("net.vitic.ec", "ec-core", "0.1.1"),
                //mavenBundle().groupId("net.vitic.ec").artifactId("ec-core").version("0.1.1").start(),
                //bundle("file:/Users/paul/Documents/IdeaProjects/everything-connects/everything-connects/ec-core/build/libs/ec-core-0.1.1.jar").start(),
                junitBundles()
        );
    }

    @Test
    public void testUpdated() throws Exception {
        for( Bundle b : bundleContext.getBundles() ) {
            System.out.println( "Bundle " + b.getBundleId() + " : " + b.getSymbolicName() + ":" + b.getRegisteredServices());
        }
        assertThat(bundleContext, is(notNullValue()));

        ServiceReference<?> serviceRef = bundleContext.getServiceReference("org.osgi.service.cm.ManagedService");
        Object service = bundleContext.getService(serviceRef);
        assertThat(service, is(notNullValue()));

        assertNotNull(bundleContext);
        Dictionary dictionary = new Hashtable();
        activator.updated(dictionary);
    }
}