/*
 * @(#) AbstractDaoTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/23
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
public abstract class AbstractDaoTest {

    @Autowired
    private TableClearer tableClearer;

    @After
    public void afterTest() {
        tableClearer.clearTables();
    }
}
