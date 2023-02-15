package ATM.Constants;

import ATM.Utilities.ConfigurationManager;

public abstract class Constants {
    public abstract static class Stream {
        public final static String EOF = "over";
        public final static String EOS = "exit";
    }

    public abstract static class Socket {
        public static final int PORT = Integer.parseInt(ConfigurationManager.GetConfig("Port"));
        public static final String HOST = ConfigurationManager.GetConfig("Server");
    }

    public abstract static class SSL {
        public static final String PROTOCOL = "TLS";
        public static final String KEYSTORE = "Resources/SSL/ATM.jks";
        public static final String KEYSTOREPASS = "123456";
        public static final String TRUSTSTORE = "Resources/SSL/cacerts.jks";
        public static final String TRUSTSTOREPASS = "123456";
    }

}
