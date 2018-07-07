package com.wipro.wipro;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;

public class PojoValidatorTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(CommonMethodForTest.TEST_TIMEOUT);

    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 2 * 2;

    // The package to test
    private static final String POJO_PACKAGE = "com.wipro.wipro.data";

    @Before
    public void validate() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE,
                new FilterPackageInfo());
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }

    @Test
    public void test_PojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(POJO_PACKAGE, new FilterPackageInfo());
    }
}
