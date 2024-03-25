package com.healspan.claim.constant;

public class DocumentQueries {

    public static final String UPLOAD_DOCUMENT_QUERY =
            "SELECT * from healspan.updatedocumentdetails({0},{1},{2})";

    public static final String DOCUMENT_FETCH_QUERY =
            "SELECT * from healspan.getdocumentdetails({0})";

    public static final String OPEN_TRANSACTION_QUERY =
            "select id from healspan.claim_stage_link where claim_info_id = {0}";

    public static final String DOCUMENT_AS_PER_STAGE_LINK_ID_QUERY =
            "select * from healspan.document where claim_stage_link_id in ({0}) and is_deleted ={1}";

    public static final String GET_CLAIM_STAGE_LINK_DETAILS_QUERY =
            "SELECT * From healspan.claim_stage_link CSL Where CSL.claim_info_id = {0} limit 1";

    public static final String DOCUMENT_DELETE =
            "select * from healspan.deletedocumentdetails({0})";

    public static final String GET_OTHER_DOCUMENT =
            "SELECT * from healspan.get_diagnosis_other({0})";

    public static final String GET_DOCUMENT_TYPE =
            "SELECT * from healspan.get_document_id({0})";

    private DocumentQueries() {
    }
}
