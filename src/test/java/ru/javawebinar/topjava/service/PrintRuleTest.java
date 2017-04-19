package ru.javawebinar.topjava.service;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class PrintRuleTest implements TestRule {

    private final TestInfo printer = new TestInfo();

    private String beforeContent = null;
    private String afterContent = null;
    private String info = null;
    private long timeStart;
    private long timeEnd;

    private class TestInfo extends ExternalResource {
        @Override
        protected void before() throws Throwable {
            timeStart = System.currentTimeMillis();
            info += beforeContent;
        };


        @Override
        protected void after() {
                timeEnd = System.currentTimeMillis();
                double seconds = (timeEnd-timeStart)/1000.0;
                info += afterContent+"Time elapsed: "+new DecimalFormat("0.000").format(seconds)+" sec\n";
        };

    }

    public final Statement apply(Statement statement, Description description) {
        beforeContent = "\nTEST "+description.getMethodName()+" START \n";
        afterContent =  "TEST ENDED ";
        return printer.apply(statement, description);
    }

    public String getInfo() {
        return info;
    }
}