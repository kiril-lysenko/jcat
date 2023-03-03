package com.self.education.catinfo.repository;

import com.self.education.catinfo.domain.CatsStat;
import com.self.education.catinfo.integration_tests.config.H2Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static com.self.education.catinfo.helper.CatInfoHelper.catsStat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest(excludeAutoConfiguration = TestDatabaseAutoConfiguration.class)
@Import(H2Config.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
class CatsStatRepositoryTest {

    @Autowired
    private CatsStatRepository repository;

    @Test
    void shouldSaveCatsStatEntity() {
        final CatsStat stat = catsStat().build();
        final CatsStat actual = repository.save(stat);

        assertThat(repository.count(), is(1L));
        assertThat(actual, is(stat));
    }
}