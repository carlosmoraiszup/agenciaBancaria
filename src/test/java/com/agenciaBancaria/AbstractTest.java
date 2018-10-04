
package com.agenciaBancaria;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}

