package com.equitem.soft.mcq.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.equitem.soft.mcq.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class McqPapperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(McqPapper.class);
        McqPapper mcqPapper1 = new McqPapper();
        mcqPapper1.setId("id1");
        McqPapper mcqPapper2 = new McqPapper();
        mcqPapper2.setId(mcqPapper1.getId());
        assertThat(mcqPapper1).isEqualTo(mcqPapper2);
        mcqPapper2.setId("id2");
        assertThat(mcqPapper1).isNotEqualTo(mcqPapper2);
        mcqPapper1.setId(null);
        assertThat(mcqPapper1).isNotEqualTo(mcqPapper2);
    }
}
