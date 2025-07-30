package pet.project.hh.Dto;

import pet.project.hh.models.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicantDto {
    private Long id;
    private Long resumeId;
    private String resumeName;
    private String applicantName;
    private String categoryName;
    private Double salary;
    private Long applicantId;
    private Long vacancyId;
    private String vacancy;
    private List<Message> messages = new ArrayList<>();
    private boolean confirmation;
}
