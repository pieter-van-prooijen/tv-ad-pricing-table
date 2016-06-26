package com.example.tvadpricing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Spring bean to retrieve, sort and resolve the ChannelPricing services
 */

@Component
public class ServiceLocator implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceLocator.class);

    public static final String SERVICE_DIRECTORY_PROP = "service.directory";

    @Autowired
    private Environment environment;

    private ServiceLoader serviceLoader;

    private String serviceDirectoryPath;

    /**
     * Answer a list of all service providers on the classpath
     * ServiceLoader takes care of caching, refreshing etc.
     *
     * @return a list of service provider instances
     */
    public List<ChannelPricingService> getServices() {

        List<ChannelPricingService> services = new LinkedList<>();
        Iterator<ChannelPricingService> iterator = serviceLoader.iterator();
        iterator.forEachRemaining(service -> services.add(service));
        return services;
    }

    // Validate the configuration before running the application.
    @Override
    public void afterPropertiesSet() throws Exception {
        // Set using --service.directory=<path>
        serviceDirectoryPath = environment.getProperty(SERVICE_DIRECTORY_PROP);
        if (StringUtils.isEmpty(serviceDirectoryPath)) {
            String msg = "Property 'service.directory' is not set";
            LOG.error(msg);
            throw new IllegalStateException(msg);
        }
        File serviceDirectory = getServiceDirectory();
        if (!serviceDirectory.canWrite()) {
            String msg = "Directory '" + serviceDirectory + "' does not exist or is not writable.";
            LOG.error(msg);
            throw new IllegalStateException(msg);
        }
        LOG.info("service.directory is {}", serviceDirectory);
        initServiceLoader(serviceDirectory);
    }

    // Invoke this method when changing / adding a jar to the class path.
    public void initServiceLoader(File serviceDirectory) {
        // Load all urls from the directory into the classloader
        File[] jars = serviceDirectory.listFiles((dirArg, name) -> name.endsWith(".jar"));

        List<URL> urls = new ArrayList<>(jars.length);
        for (File jar : jars) {
            try {
                urls.add(new URL("file://" + jar.getAbsolutePath()));
                LOG.info("adding jar {}", jar.getAbsolutePath());
            } catch (MalformedURLException e) {
                LOG.error(e.getMessage());
            }
        }
        // Make sure the service classloader delegates to the Tomcat webapp classloader, so all libs in the webapp
        // are visible.
        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), Thread.currentThread()
                .getContextClassLoader());
        serviceLoader = ServiceLoader.load(ChannelPricingService.class, classLoader);
    }

    public File getServiceDirectory() {
        if (StringUtils.isEmpty(serviceDirectoryPath)) {
            return null;
        }
        return new File(serviceDirectoryPath);
    }
}
