package com.equitem.soft.mcq.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.equitem.soft.mcq.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttemptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attempt.class);
        Attempt attempt1 = new Attempt();
        attempt1.setId("id1");
        Attempt attempt2 = new Attempt();
        attempt2.setId(attempt1.getId());
        assertThat(attempt1).isEqualTo(attempt2);
        attempt2.setId("id2");
        assertThat(attempt1).isNotEqualTo(attempt2);
        attempt1.setId(null);
        assertThat(attempt1).isNotEqualTo(attempt2);
    }
}
