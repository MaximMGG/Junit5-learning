package com.maxim;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

public class TestLauncher {
    public static void main(String[] args) {
        LauncherDiscoveryRequest requst = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectPackage("com.maxim.service"))
        .build();

        try (LauncherSession session = LauncherFactory.openSession()) {
            session.getLauncher().execute(requst);
        }

    }
}
