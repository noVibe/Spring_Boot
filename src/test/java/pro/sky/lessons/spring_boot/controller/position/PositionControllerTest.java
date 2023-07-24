package pro.sky.lessons.spring_boot.controller.position;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.lessons.spring_boot.model.Position;
import pro.sky.lessons.spring_boot.repository.position.PositionRepository;
import test_data.TestData;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = {"ADMIN"})
@AutoConfigureMockMvc
class PositionControllerTest {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    void addPositionsAndGetAllTest() throws Exception {
        List<Position> expected = positionRepository.findAll();
        List<Position> newPositions = TestData.generatePositionList(3);
        addAll(newPositions);
        expected.addAll(newPositions);
        List<Position> actual = new ArrayList<>();
        mockMvc.perform(get("/position/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result ->
                        actual.addAll((objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                                }))
                        ));
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    void addAll(List<Position> positions) throws Exception {
        List<String> beingAdded = positions.stream()
                .map(Position::getPositionName)
                .toList();
        mockMvc.perform(post("/position/addBatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beingAdded)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void deletePositionTest() throws Exception {
        mockMvc.perform(delete("/position")
                .param("id", "1"))
                .andExpect(status().isOk());
        assertThat(positionRepository.findById(1L).isEmpty())
                .isTrue();
    }
}