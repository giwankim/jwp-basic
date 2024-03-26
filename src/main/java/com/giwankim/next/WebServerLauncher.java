package com.giwankim.next;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebServerLauncher {
  private static final Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);

  public static void main(String[] args) throws LifecycleException {
    String webappDirLocation = "webapp/";
    Tomcat tomcat = new Tomcat();

    String webPort = System.getenv("PORT");
    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }
    tomcat.setPort(Integer.parseInt(webPort));

    Context ctx = tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
    File additionalWebInfClasses = new File("build/classes/java/main");
    StandardRoot resources = new StandardRoot(ctx);
    resources.addPreResources(
      new DirResourceSet(
        resources,
        "/WEB-INF/classes",
        additionalWebInfClasses.getAbsolutePath(), "/"
      )
    );
    ctx.setResources(resources);

    logger.info("configuring app with basedir: {}", new File(webappDirLocation).getAbsolutePath());

    tomcat.start();
    tomcat.getServer().await();
  }
}
