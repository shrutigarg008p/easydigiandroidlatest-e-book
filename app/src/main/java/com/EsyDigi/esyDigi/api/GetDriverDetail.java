
package com.EsyDigi.esyDigi.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "alternate_driver1",
        "alternate_driver2",
        "alternate_driver3",
        "learner_valid_date",
        "accompanying_valid_date",
        "special_condition",
        "course_review_date",
        "vehicle_handeling",
        "country_roads",
        "country_roads",
        "built_up_areas",
        "the_instructor_overall_assesment"
        ,"builtup_areas_and_country_roads"
})
public class GetDriverDetail {

    @JsonProperty("status")
    public String status;
    @JsonProperty("alternate_driver1")
    public List<String> alternateDriver1 = null;
    @JsonProperty("alternate_driver2")
    public List<String> alternateDriver2 = null;
    @JsonProperty("alternate_driver3")
    public List<String> alternateDriver3 = null;
    @JsonProperty("learner_valid_date")
    public String learnerValidDate;
    @JsonProperty("accompanying_valid_date")
    public String accompanyingValidDate;
    @JsonProperty("course_review_date")
    public String course_review_date;
    @JsonProperty("special_condition")
    public List<String> special_condition = null;
    @JsonProperty("vehicle_handeling")
    public List<String> vehicle_handeling = null;
    @JsonProperty("country_roads")
    public List<String> country_roads = null;
    @JsonProperty("built_up_areas")
    public List<String> built_up_areas  = null;
    @JsonProperty("the_instructor_overall_assesment")
    public List<String> the_instructor_overall_assesment   = null;
    @JsonProperty("builtup_areas_and_country_roads")
    public List<String> builtup_areas_and_country_roads   = null;

}