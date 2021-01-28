package be.heh.epm.adapter.web;

import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.in.AddEmployeeUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    private AddEmployeeUseCase addSalariedEmployee;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createReturn201() throws Exception {
        addSalariedEmployee = mock(AddEmployeeUseCase.class);

        EmployeeSalariedValidating postEmployeeValidating = new EmployeeSalariedValidating();
        postEmployeeValidating.setEmpId(1);
        postEmployeeValidating.setName("toto");
        postEmployeeValidating.setAddress("Rue de Mons");
        postEmployeeValidating.setMail("toto@heh.be");
        postEmployeeValidating.setMonthlySalary(1500);

        mockMvc.perform(post("/employees/salaried")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postEmployeeValidating)))

                .andExpect(status().isCreated())

                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/employees/salaried/1"));


        //verify(addSalariedEmployee).execute(postEmployeeValidating);

    }

    @Test
    public void errorInMailReturn400() throws Exception {

        EmployeeSalariedValidating postEmployeeValidating = new EmployeeSalariedValidating();
        postEmployeeValidating.setEmpId(1);
        postEmployeeValidating.setName("toto");
        postEmployeeValidating.setAddress("Rue de Mons");
        postEmployeeValidating.setMail("totoheh.be"); //mail error
        postEmployeeValidating.setMonthlySalary(1500);

        MvcResult res = mockMvc.perform(post("/employees/salaried")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postEmployeeValidating)))

                .andExpect(status().isBadRequest())
                .andReturn();


        JSONObject jsonObject = new JSONObject(res.getResponse().getContentAsString());

        JSONArray error = jsonObject.getJSONArray("errors");
        //assertEquals("mail : must be a well-formed email address", error.getString(0));

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
