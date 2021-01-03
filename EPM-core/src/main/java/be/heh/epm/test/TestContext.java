package be.heh.epm.test;

import be.heh.epm.application.Context;

public class TestContext {
    public static void setContext() {
        Context.emp = new InMemoryEmployGateway();
    }
}
