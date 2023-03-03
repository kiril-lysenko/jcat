package com.self.education.catinfo.repository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.self.education.catinfo.helper.CatInfoHelper.redCatEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.catinfo.api.OffsetBasedPageRequest;
import com.self.education.catinfo.domain.Cats;
import com.self.education.catinfo.integration_tests.config.H2Config;

@DataJpaTest(excludeAutoConfiguration = TestDatabaseAutoConfiguration.class)
@Import(H2Config.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
class CatsRepositoryTest {

    @Autowired
    CatsRepository repository;

    @Test
    void shouldCreateNewCat() {
        repository.createNewCat(redCatEntity().build());

        assertThat(repository.count(), is(1L));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/cats_data.sql" })
    void shouldFailureCreateNewCatWhenNameIsDuplicate() {
        final Exception actual = assertThrows(DataIntegrityViolationException.class,
                () -> repository.createNewCat(redCatEntity().name("Lucky").build()));

        assertThat(actual.getMessage(), containsString("could not execute statement"));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/cats_data.sql" })
    void shouldFindAll() {
        final Pageable pageable = new OffsetBasedPageRequest(0, 2);

        final Page<Cats> actual = repository.findAll(pageable);

        assertThat(actual.getContent().get(0), is(redCatEntity().build()));
        assertThat(actual.getContent().get(1), is(whiteCatEntity().build()));
    }
}