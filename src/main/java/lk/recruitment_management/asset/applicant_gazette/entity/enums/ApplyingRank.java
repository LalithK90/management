package lk.recruitment_management.asset.applicant_gazette.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyingRank {
    ASP("Assistant Superintendent of Police"),
    WASP("Woman Assistant Superintendent of Police"),
    SI("Sub Inspector"),
    WSI("Woman Sub Inspector"),
    PC("Police Constable"),
    WPC("Woman Police Constable"),
    PCD("Police Constable Driver");

    private final String applyingRank;
}


