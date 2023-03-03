package com.self.education.catinfo.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.catinfo.domain.Colors.BLACK;
import static com.self.education.catinfo.domain.Colors.BLACK_RED;
import static com.self.education.catinfo.domain.Colors.FAWN;
import static com.self.education.catinfo.domain.Colors.RED;
import static com.self.education.catinfo.domain.Colors.WHITE;
import static com.self.education.catinfo.helper.CatInfoHelper.FAWN_COLOR_COUNT;
import static com.self.education.catinfo.helper.CatInfoHelper.blackCatInfoEntity;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.catinfo.domain.CatColorsInfo;
import com.self.education.catinfo.integration_tests.config.H2Config;

@DataJpaTest(excludeAutoConfiguration = TestDatabaseAutoConfiguration.class)
@Import(H2Config.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
class CatColorsInfoRepositoryTest {

    @Autowired
    private CatColorsInfoRepository repository;

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/cats_data.sql" })
    void shouldCountCatsByColor() {
        final List<CatColorsInfo> actual = repository.countCatsByColor();

        assertThat(actual.get(0), is(CatColorsInfo.builder().catColor(WHITE).count(1).build()));
        assertThat(actual.get(1), is(CatColorsInfo.builder().catColor(RED).count(1).build()));
        assertThat(actual.get(2), is(CatColorsInfo.builder().catColor(BLACK_RED).count(3).build()));
    }

    @Test
    void shouldSave() {
        repository.save(FAWN.name(), FAWN_COLOR_COUNT);

        assertThat(repository.count(), is(1L));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/cats_data.sql" })
    void shouldFindByColor() {

        Optional<CatColorsInfo> actual = repository.findByColor(BLACK.name());

        assertThat(actual.get(), is(blackCatInfoEntity().build()));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/cats_data.sql" })
    void shouldUpdate() {
        final int count = 3;
        repository.update(BLACK.name(), count);

        assertThat(repository.findByColor(BLACK.name()).get(), is(blackCatInfoEntity().count(count).build()));
    }
}