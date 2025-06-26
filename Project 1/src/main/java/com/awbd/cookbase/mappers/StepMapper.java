/* ───────────────── StepMapper ───────────────── */
package com.awbd.cookbase.mappers;

import com.awbd.cookbase.domain.Step;
import com.awbd.cookbase.dtos.StepDTO;
import org.springframework.stereotype.Component;

@Component
public class StepMapper {

    public StepDTO toDto(Step step) {
        Long   id          = step.getId();
        int    number      = step.getStepNumber();
        String instruction = step.getInstruction();
        return new StepDTO(id, number, instruction);
    }

    public Step toStep(StepDTO dto) {
        Step step = new Step();
        step.setId(dto.getId());
        step.setStepNumber(dto.getStepNumber());
        step.setInstruction(dto.getInstruction());
        return step;
    }
}
