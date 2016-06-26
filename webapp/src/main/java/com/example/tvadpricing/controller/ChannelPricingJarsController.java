package com.example.tvadpricing.controller;

import com.example.tvadpricing.security.SecurityConfig;
import com.example.tvadpricing.service.ServiceLocator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Implement the controller for uploading new service jars
 *
 */
@Controller
public class ChannelPricingJarsController {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelPricingJarsController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    /**
     * Upload a new service provider jar to the service, replacing any jars of the same name.
     */
    @Secured({ SecurityConfig.ADMIN_ROLE })
    @RequestMapping(method = RequestMethod.POST, path = "/channel-pricing-jars")
    public void uploadServiceJar(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws
            IOException {
        File serviceDirectory = serviceLocator.getServiceDirectory();
        File serviceJar = new File(serviceDirectory, file.getOriginalFilename());

        // Only one thread can upload a jar to prevent file corruption.
        synchronized(this) {
            serviceJar.delete();
            try (InputStream is = file.getInputStream();
                 OutputStream os = new FileOutputStream(serviceJar)) {
                int nofBytes = IOUtils.copy(is, os);
                LOG.info("written {} bytes to {}", nofBytes, serviceJar.getAbsolutePath());
                serviceLocator.initServiceLoader(serviceDirectory); // reload the serviceloader caches.
            } catch (IOException e) {
                LOG.error(e.getMessage());
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            }
        }
        response.sendRedirect("/admin.html");
    }
}
