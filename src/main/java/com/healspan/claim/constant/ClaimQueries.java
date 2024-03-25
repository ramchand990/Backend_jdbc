package com.healspan.claim.constant;

public class ClaimQueries {

    public static final String HOSPITAL_DASHBOARD_QUERY =
            "select * from healspan.get_hospital_dashboard_details({0})";

    public static final String HEALSPAN_DASHBOARD_QUERY =
            "select * from healspan.get_healspan_dashboard_details({0})";

    public static final String CLAIM_FETCH_QUERY =
            "select * from healspan.get_claim_details({0})";

    public static final String CLAIM_PATIENT_INFO_CREATE_OR_UPDATE_QUERY =
            "SELECT * from healspan.patient_info_iu({0})";

    public static final String MEDICAL_INFO_CREATE_OR_UPDATE_QUERY =
            "SELECT * from healspan.medical_info_iu({0})";

    public static final String INSURANCE_INFO_CREATE_OR_UPDATE_QUERY =
            "SELECT * from healspan.insurance_info_iu({0})";

    public static final String SAVE_QUESTIONNAIRE_DOCUMENT_QUERY =
            "SELECT * from healspan.question_doc_iu({0})";

    public static final String SUBMIT_CLAIM_INFO =
            "SELECT * from healspan.submit_claim({0})";

    public static final String CHANGE_CLAIM_STAGE =
            "SELECT * from healspan.change_stage({0})";

    public static final String QUERY_ON_CLAIM =
            "SELECT * from healspan.query_claim({0})";

    public static final String APPROVE_CLAIM =
            "SELECT * from healspan.approve_claim({0})";

    public static final String TPA_UPDATE_QUERY =
            "SELECT * from healspan.submit_tpa_action({0})";

    private ClaimQueries() {

    }

}
