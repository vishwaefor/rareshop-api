package com.rareshop.api.rest.listing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rareshop.api.rest.listing.constant.APIMapping;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.model.BasicPricingRule;
import com.rareshop.api.rest.listing.service.PricingRuleService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rareshop.api.common.core.constant.Params;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.Acknowledgement;
import rareshop.api.common.core.model.RareResponse;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class PricingRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingRuleService pricingRuleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<BasicPricingRule> pricingRuleCaptor;

    @AfterEach
    private void afterEach() {
        reset(pricingRuleService);
    }

    @Nested
    @DisplayName("Updating PricingRule")
    class UpdatePricingRule {

        @Test
        @DisplayName("When [put] to /pricing-rules/{pricing-rule-id} with non existing id, " +
                "should get not found error:404")
        void testUpdatePricingRuleWithNonExistingId() throws Exception {

            long nonExistingId = 0L;
            String errorMessage = "item not found";
            Mockito.when(pricingRuleService.updatePricingRule(any(BasicPricingRule.class)))
                    .thenThrow(new NotFoundException(errorMessage));

            BasicPricingRule updatedBasicPricingRule = new BasicPricingRule();
            updatedBasicPricingRule.setId(nonExistingId);
            String url = APIMapping.PRICING_RULE_MAPPING + APIMapping.SINGLE_PRICING_RULE_MAPPING;

            ResultActions resultActions = mockMvc.perform(put(url, nonExistingId)
                    .content(objectMapper.writeValueAsString(updatedBasicPricingRule))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(pricingRuleService).updatePricingRule(pricingRuleCaptor.capture());
            Assertions.assertEquals(updatedBasicPricingRule, pricingRuleCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));


        }

        @Test
        @DisplayName("When [put] to /pricing-rules/{pricing-rule-id} with incorrect body, should get bad request error:400")
        void testUpdatePricingRuleWithIncorrectBody() throws Exception {

            String errorMessage = "invalid property";
            Mockito.when(pricingRuleService.updatePricingRule(any(BasicPricingRule.class)))
                    .thenThrow(new BadRequestException(errorMessage));

            BasicPricingRule updatedBasicPricingRule = new BasicPricingRule();
            String url = APIMapping.PRICING_RULE_MAPPING + APIMapping.SINGLE_PRICING_RULE_MAPPING;

            ResultActions resultActions = mockMvc.perform(put(url, 1L)
                    .content(objectMapper.writeValueAsString(updatedBasicPricingRule))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(pricingRuleService).updatePricingRule(pricingRuleCaptor.capture());
            Assertions.assertEquals(updatedBasicPricingRule, pricingRuleCaptor.getValue());

            resultActions.andExpect(status().isBadRequest())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));
        }

        @Test
        @DisplayName("When [put] to /pricing-rules/{pricing-rule-id} with correct body, Pricing Rule should be updated ")
        void testUpdatePricingRuleWithCorrectBody() throws Exception {
            long id = 1L;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRICING_RULE_UPDATED, payload);

            Mockito.when(pricingRuleService.updatePricingRule(any(BasicPricingRule.class)))
                    .thenReturn(acknowledgement);

            BasicPricingRule updatedBasicPricingRule = new BasicPricingRule();
            String url = APIMapping.PRICING_RULE_MAPPING + APIMapping.SINGLE_PRICING_RULE_MAPPING;

            ResultActions resultActions = mockMvc.perform(put(url, 1L)
                    .content(objectMapper.writeValueAsString(updatedBasicPricingRule))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(pricingRuleService).updatePricingRule(pricingRuleCaptor.capture());
            Assertions.assertEquals(updatedBasicPricingRule, pricingRuleCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));
        }
    }

    @Nested
    @DisplayName("Deleting PricingRule")
    class DeletePricingRule {

        @Test
        @DisplayName("When [delete] to /pricing-rules/{pricing-rule-id} with non existing id," +
                " should get not found error:404")
        void testDeletePricingRuleWithNonExistingId() throws Exception {

            long nonExistingId = 0L;
            String errorMessage = "item not found";
            Mockito.when(pricingRuleService.deletePricingRule(anyLong()))
                    .thenThrow(new NotFoundException(errorMessage));

            String url = APIMapping.PRICING_RULE_MAPPING + APIMapping.SINGLE_PRICING_RULE_MAPPING;
            ResultActions resultActions = mockMvc.perform(delete(url, nonExistingId));

            Mockito.verify(pricingRuleService).deletePricingRule(idCaptor.capture());
            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));


        }

        @Test
        @DisplayName("When [delete] to /pricing-rules/{pricing-rule-id} with existing id, " +
                "PricingRule should be deleted")
        void testDeleteProductWithExistingId() throws Exception {
            long existingId = 1L;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, existingId);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRODUCT_DELETED, payload);

            Mockito.when(pricingRuleService.deletePricingRule(anyLong()))
                    .thenReturn(acknowledgement);

            String url = APIMapping.PRICING_RULE_MAPPING + APIMapping.SINGLE_PRICING_RULE_MAPPING;
            ResultActions resultActions = mockMvc.perform(delete(url, existingId));

            Mockito.verify(pricingRuleService).deletePricingRule(idCaptor.capture());
            Assertions.assertEquals(existingId, idCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));

        }
    }
}