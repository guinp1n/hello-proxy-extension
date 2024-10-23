/*
 * Copyright 2018-present HiveMQ GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hivemq.extensions.helloproxy;

import com.hivemq.extension.sdk.api.ExtensionMain;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.parameter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author Dasha Samkova
 * @since 4.33.1
 */
public class HelloProxyMain implements ExtensionMain {

    private static final @NotNull Logger log = LoggerFactory.getLogger(HelloProxyMain.class);

    @Override
    public void extensionStart(
            final @NotNull ExtensionStartInput extensionStartInput,
            final @NotNull ExtensionStartOutput extensionStartOutput) {

        try {

            final String userName = System.getProperty("http.proxyUser");
            final char[] password = System.getProperty("http.proxyPassword").toCharArray();

            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });

            final ExtensionInformation extensionInformation = extensionStartInput.getExtensionInformation();
            log.info("Started " + extensionInformation.getName() + ":" + extensionInformation.getVersion());

        } catch (final Exception e) {
            log.error("Exception thrown at extension start: ", e);
        }
    }

    @Override
    public void extensionStop(
            final @NotNull ExtensionStopInput extensionStopInput,
            final @NotNull ExtensionStopOutput extensionStopOutput) {

        final ExtensionInformation extensionInformation = extensionStopInput.getExtensionInformation();
        log.info("Stopped " + extensionInformation.getName() + ":" + extensionInformation.getVersion());
    }


}